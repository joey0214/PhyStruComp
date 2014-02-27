/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import psc.Sequence.Alignment;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class AlignmentIO extends SeqIO
{
    private  Alignment align ;

    @Override
    public void read(File file) throws FileNotFoundException, IOException 
    {
        loadAlignment(getSeqIO(file));
    }

    public void write(String fileName, Sequence seq) throws IOException 
    {
        
    }
    
    private SeqIO getSeqIO(File file) throws FileNotFoundException, IOException
    {
        FastaIO fastaio = new FastaIO();
        fastaio.read(file);
        return fastaio;
    }

    private void loadAlignment(SeqIO seqio) 
    {
        align = new Alignment();
        FastaIO fa = (FastaIO)seqio;
        int length = fa.getMaxLength();
        int noOfSeq = fa.getNoOfSeq();
        int[] index = new int[noOfSeq];
        String[] names =new String[noOfSeq];
        HashMap nameMap = new HashMap<String, Sequence>();
        ArrayList seqs = new ArrayList<Sequence>(noOfSeq);
        
        for (int i=0; i <noOfSeq; i++)
        {
            index[i] = i;
            Sequence seq = fa.getSeqs().get(i);
            names[i] = seq.getSeqName();
            nameMap.put(seq.getSeqName(), seq);
            char[] aseq = new char[length];
            char[] oseq = seq.getSeqChar();
            int olength = seq.getLength();
            int j =0;
            for (; j < olength; j++)
            {
                aseq[j] = oseq[j];
            }
            for (; j < length; j++)
            {
                aseq[j] = ' ';
            }
            seq.setSeqChar(aseq);
            seqs.add(seq);
        }
        align.setLength(length);
        align.setNoOfSeq(noOfSeq);
        align.setIndex(index);
        align.setSeqNmaes(names);
        align.setNameMapSeq(nameMap);
        align.setSeqs(seqs);
        align.gapProfile();
    }
    
//    load aligned sequences
    public void loadAlignment(Sequence[] sequences)
    {
        int maxLen = findMaxLength(sequences);
        align = new Alignment();
        int noOfSeq = sequences.length;
        int[] index = new int[noOfSeq];
        String[] names =new String[noOfSeq];
        HashMap nameMap = new HashMap<String, Sequence>();
        ArrayList seqs = new ArrayList<Sequence>(noOfSeq);
        
        for (int i=0; i <noOfSeq; i++)
        {
            index[i] = i;
            Sequence seq = sequences[i];
            names[i] = seq.getSeqName();
            nameMap.put(seq.getSeqName(), seq);
            char[] aseq = new char[maxLen];
            char[] oseq = seq.getSeqChar();
            int olength = seq.getLength();
            int j =0;
            for (; j < olength; j++)
            {
                aseq[j] = oseq[j];
            }
            for (; j < maxLen; j++)
            {
                aseq[j] = ' ';
            }
            seq.setSeqChar(aseq);
            seqs.add(seq);
        }
        
        align.setLength(maxLen);
        align.setNoOfSeq(noOfSeq);
        align.setIndex(index);
        align.setSeqNmaes(names);
        align.setNameMapSeq(nameMap);
        align.setSeqs(seqs);
        align.gapProfile();
    }
    
//    load unaligned sequences; using "-" for  making up sequences to same length  
    public void loadRawAlignment(Sequence[] sequences)
    {
        int maxLen = findMaxLength(sequences);
//       make up to same length
        int seqListLen = sequences.length;
        for (int i =0; i <seqListLen; i ++)
        {
            char[] oldChar = sequences[i].getSeqChar();
            int oldLen = oldChar.length;
            
            if (maxLen > oldLen)
            {
                char[] newChar = new char[maxLen];
                for (int j = 0; j < maxLen; j ++)
                {
                    if (j < oldLen)
                    {
                        newChar[j] = oldChar[j];
                    }
                    else 
                    {
                        newChar[j] = '-';
                    }
                }
                
                sequences[i].setSeqChar(newChar);
            }
        }
        
        loadAlignment(sequences);
        
        
    }
    
    public Alignment getAlignment()
    {
        return align;
    }
    
    public void setAlignment(Alignment ali)
    {
        this.align = ali;
    }

    private int findMaxLength(Sequence[] sequences) 
    {
        int max = 0;
        for (int i =0 ; i < sequences.length; i ++)
        {
            int tmpLen = sequences[i].getSeqChar().length;
            if (max < tmpLen)
            {
                max = tmpLen;
            }
        }
        return max;
    }

    @Override
    public void write(File file, Sequence seq) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
