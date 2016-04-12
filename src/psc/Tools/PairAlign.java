/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Tools;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.Annotation;
import org.biojava.bio.BioError;
import org.biojava.bio.BioException;
import org.biojava.bio.dist.Distribution;
import org.biojava.bio.dist.DistributionFactory;
import org.biojava.bio.dist.GapDistribution;
import org.biojava.bio.dist.PairDistribution;
import org.biojava.bio.dist.UniformDistribution;
import org.biojava.bio.dp.DP;
import org.biojava.bio.dp.DPFactory;
import org.biojava.bio.dp.EmissionState;
import org.biojava.bio.dp.MarkovModel;
import org.biojava.bio.dp.ScoreType;
import org.biojava.bio.dp.SimpleEmissionState;
import org.biojava.bio.dp.SimpleMarkovModel;
import org.biojava.bio.dp.StatePath;
import org.biojava.bio.dp.twohead.CellCalculatorFactoryMaker;
import org.biojava.bio.dp.twohead.DPInterpreter;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.ProteinTools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.db.SequenceDB;
import org.biojava.bio.seq.io.SymbolTokenization;
import org.biojava.bio.symbol.Alphabet;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.BasisSymbol;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.IllegalAlphabetException;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.bio.symbol.Symbol;
import org.biojava.bio.symbol.SymbolList;
import org.biojava3.ronn.ModelLoader;

/**
 *
 * @author zhongxf
 */
public class PairAlign 
{

    
    public PairAlign(SequenceDB seqForAlign ) throws IllegalSymbolException, IllegalAlphabetException, IllegalArgumentException, BioException 
    {
        FiniteAlphabet alpha = ProteinTools.getAlphabet();
        CellCalculatorFactoryMaker cfMaker = new DPInterpreter.Maker();
        DPFactory dPFactory = new DPFactory.DefaultFactory(cfMaker);

        MarkovModel markovModel = generateAligner(alpha, 0.7, 0.6);

        DP aligner = dPFactory.createDP(markovModel);
        
        
        
        SequenceIterator seqSourceIterator = seqForAlign.sequenceIterator();
        while (seqSourceIterator.hasNext())
        {
            Sequence sourceSeq = seqSourceIterator.nextSequence();
            SequenceIterator seqSTargetIterator = seqForAlign.sequenceIterator();
            while (seqSTargetIterator.hasNext())
            {
                Sequence targetSeq = seqSTargetIterator.nextSequence();
                
                Sequence[] seqs = new Sequence[]{sourceSeq,targetSeq};
                System.out.println("Aligning " + sourceSeq.getName() + " : " + targetSeq.getName());
                
                StatePath result = aligner.viterbi(seqs, ScoreType.PROBABILITY);
                System.out.println("Log odds Viterbi probability: \t" + result.getScore());
                System.out.println("\t" + result.getScore());
                
                //output the alignment
                SymbolList alignment = result.symbolListForLabel(StatePath.SEQUENCE);
                System.out.println(alignment.getAlphabet());
                SymbolTokenization tok = alignment.getAlphabet().getTokenization("default");
                System.out.println(tok.tokenizeSymbolList(alignment));
                
                //output the state path
                alignment = result.symbolListForLabel(StatePath.STATES);
                System.out.println(alignment.getAlphabet());
                tok = alignment.getAlphabet().getTokenization("default");
                System.out.println(tok.tokenizeSymbolList(alignment));
                tokenizePath(result);
            }
        }

    }
     private static MarkovModel generateAligner(FiniteAlphabet alpha, double pMatch, double pExtenGap) throws IllegalSymbolException, IllegalAlphabetException
    {
        FiniteAlphabet seq01 = alpha;
        FiniteAlphabet seq02 = (FiniteAlphabet)AlphabetManager.getCrossProductAlphabet(Collections.nCopies(2, seq01));
        
        MarkovModel markovModel = new SimpleMarkovModel(2, seq02, "pair-wise aligner");
        
        Distribution nullModel = new UniformDistribution(seq01);
        Distribution gap = new GapDistribution(seq01);
        Distribution matchDist = generateMatchDist((FiniteAlphabet)seq02);
        Distribution insert1Dist = new PairDistribution(nullModel, gap);
        Distribution insert2Dist = new PairDistribution(gap, nullModel);
        
        EmissionState match = new SimpleEmissionState(
                "match", Annotation.EMPTY_ANNOTATION, new int[]{1,1}, matchDist);
        EmissionState insert1 = new SimpleEmissionState(
                "insert1", Annotation.EMPTY_ANNOTATION, new int[]{1,0}, insert1Dist);
        EmissionState insert2 = new SimpleEmissionState(
                "insert2", Annotation.EMPTY_ANNOTATION, new  int[]{0,1}, insert2Dist);
        
        //add the states to the model
        markovModel.addState(match);
        markovModel.addState(insert1);
        markovModel.addState(insert2);

        //these transitions will begin the model
        markovModel.createTransition(markovModel.magicalState(), insert1);
        markovModel.createTransition(markovModel.magicalState(), insert2);
        markovModel.createTransition(markovModel.magicalState(), match);

        //these transitions will terminate the model
        markovModel.createTransition(insert1, markovModel.magicalState());
        markovModel.createTransition(insert2, markovModel.magicalState());
        markovModel.createTransition(match, markovModel.magicalState());

        //self transitions
        markovModel.createTransition(match, match); //extend the match
        markovModel.createTransition(insert1, insert1); //extend a gap
        markovModel.createTransition(insert2, insert2); //extend a gap

        markovModel.createTransition(match, insert1); //insert a gap
        markovModel.createTransition(match, insert2); //insert a gap
        markovModel.createTransition(insert1, match); //back to matching again
        markovModel.createTransition(insert2, match); //back to matching again
        
        markovModel.getWeights(markovModel.magicalState()).setWeight(match, 0.5);
        markovModel.getWeights(markovModel.magicalState()).setWeight(insert1, 0.25);
        markovModel.getWeights(markovModel.magicalState()).setWeight(insert2, 0.25);
        
        Distribution distr;
        
        double pEnd = 0.01;
        
        distr = markovModel.getWeights(match);
        distr.setWeight(match, pMatch);
        distr.setWeight(insert1, (1.0 - pMatch - pEnd) / 2.0);
        distr.setWeight(insert2, (1.0 - pMatch - pEnd) / 2.0);
        distr.setWeight(markovModel.magicalState(), pEnd);

        //----Transition probabilites from the 1st insert state
        distr = markovModel.getWeights(insert1);
        //probability of self transition (gap extension)
        distr.setWeight(insert1, pExtenGap);
        //probability of transition to match
        distr.setWeight(match, 1.0 - pEnd - pExtenGap);
        //probability of terminating after a gap
        distr.setWeight(markovModel.magicalState(), pEnd);

        //----Transition probabilites from the 2nd insert state
        distr = markovModel.getWeights(insert2);
        //probability of self transition (gap extension)
        distr.setWeight(insert2, pExtenGap);
        //probability of transition to match
        distr.setWeight(match, 1.0 - pEnd - pExtenGap);
        //probability of terminating after a gap
        distr.setWeight(markovModel.magicalState(), pEnd);

        return markovModel;
    }
     
     private static Distribution generateMatchDist(FiniteAlphabet seq02) throws IllegalAlphabetException, IllegalSymbolException 
     {
        Distribution dist  = DistributionFactory.DEFAULT.createDistribution(seq02);
        int size = seq02.size();
        int matches = (int)Math.sqrt(size);
        
        double pMatch = 0.7; 
        double matchWeight = pMatch/matches;
        double missWeight = (1.0 - pMatch)/ (size-matches);
        
        for (Iterator i = seq02.iterator(); i.hasNext();)
        {
            BasisSymbol cpSymbol = (BasisSymbol)i.next();
            List sl = cpSymbol.getSymbols();
            if (sl.get(0) == sl.get(1))
            {
                dist.setWeight(cpSymbol, matchWeight);
                
            }
            else 
            {
                dist.setWeight(cpSymbol, missWeight);
            }
        }
        return dist;
    }

    private static void tokenizePath(StatePath path) throws IllegalSymbolException, BioException 
    {
        SymbolList states = path.symbolListForLabel(StatePath.STATES);
        SymbolList symbols = path.symbolListForLabel(StatePath.SEQUENCE);
        StringBuilder queryString = new StringBuilder();
        StringBuilder targetString = new StringBuilder();
        StringBuilder pathString = new StringBuilder();
        
        if (states.length() != symbols.length())
        {
            throw new BioError("State path lengths should be identical");
        }
        
        char queryTokenn =' ';
        char targetToken = ' ';
        char pathToken = ' ';
        
        for (int i = 1; i < symbols.length(); i ++)
        {
            BasisSymbol doublet = (BasisSymbol)symbols.symbolAt(i);
            List sl = doublet.getSymbols();
//            queryTokenn = ProteinTools.
//            Alphabet aa = ProteinTools.getAlphabet();
//            SymbolTokenization aaToken = aa.getTokenization("token");
            
            queryTokenn = DNATools.dnaToken((Symbol)sl.get(0));
            targetToken = DNATools.dnaToken((Symbol)sl.get(1));
            
            Symbol s = states.symbolAt(i);
            
            if (s.getName() == "match" && queryTokenn == targetToken)
            {
                pathToken = '+';
                
            }
            else 
            {
                pathToken = ' ';
            }
            
            queryString.append(queryTokenn);
            pathString.append(pathToken);
            targetString.append(targetToken);
        }
        
        System.out.println(queryString);
        System.out.println(pathString);
        System.out.println(targetString);
        
        
        
    }
    
}
