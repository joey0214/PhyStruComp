/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import psc.IO.AlignmentIO;
import psc.IO.FastaIO;
import psc.IO.SeqIO;

import psc.Sequence.Alignment;
import psc.Sequence.AlignmentPanel;
import psc.Sequence.AlignmentView;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class AlignmentVIewTest 
{
    
    public static void main(String args[]) throws FileNotFoundException, IOException 
    {
        
        Sequence testSeq1 = new Sequence("test2", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq2 = new Sequence("test1", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq3 = new Sequence("test1", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq4 = new Sequence("test1", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq5 = new Sequence("test2", "AAACTGCGACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCAAAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
//        Sequence[] seqarray = {testSeq1,testSeq2};
        Sequence[] seqarray =new Sequence[5];
        seqarray[0] = testSeq1;
        seqarray[1] = testSeq2;
        seqarray[2] = testSeq3;
        seqarray[3] = testSeq4;
        seqarray[4] = testSeq5;
        
////      
//        SeqIO seqIO = new SeqIO() {
//
//            @Override
//            public void read(File file) throws FileNotFoundException, IOException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void write(File file, Sequence seq) throws IOException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        }  ;
//        seqIO.setSeqs(seqarray);
//        
//        FastaIO fastaio = new FastaIO();
//        fastaio.read(new File("./msa.fasta"));
        
        
        AlignmentIO testI = new AlignmentIO();
        testI.loadAlignment(seqarray);
//        Alignment testAlignment = testI.getAlignment();
        
//        Alignment testAlignment = toAlignment(seqarray);
        
//        if (testAlignment != null)
//        {
//            System.out.println("true");
//        }
        
        testI.read(new File("./msf.fasta"));
        
        System.out.println((testI.getAlignment()));
//        for (int i =0; i < )
        
        AlignmentView testView = new AlignmentView(testI.getAlignment());
        
        if (testView == null)
        {
            System.out.println("test alignment null");
        }
        
        AlignmentPanel alignmentPanel = new  AlignmentPanel(testView);
       
        if (alignmentPanel == null)
        {
            System.out.println("test alignment null");
        }
        
        JFrame mainFrame = new JFrame("Test on alignment");
         mainFrame.add(alignmentPanel);
         mainFrame.setSize(800, 800);
         mainFrame.setVisible(true);
         
     }
    
    

    private static Alignment toAlignment(Sequence[] seqarray) 
    {
        Alignment ali=new Alignment();
//        Fasta fa=(Fasta)sio;
        
        int length=0 ;
        for (int i=0; i< seqarray.length; i++)
        {
            if (seqarray[i].getSeqChar().length > length)
            {
                length = seqarray[i].getSeqChar().length ;
            }
        }
        
        int noOfSeq=seqarray.length;
        int[] index=new int[noOfSeq];
        String[] names=new String[noOfSeq];
        HashMap nameMap=new HashMap<String,Sequence>();
        ArrayList seqs=new ArrayList<Sequence>(noOfSeq);
        for(int i=0;i<noOfSeq;i++){
            index[i]=i;
            Sequence seq=seqarray[i];
            names[i]=seq.getSeqName();
            nameMap.put(seq.getSeqName(), seq);
            char[] aseq=new char[length];
            char[] oseq=seq.getSeqChar();
            int olength=seq.getLength();
            int j=0;
            for(;j<olength;j++){
                aseq[j]=oseq[j];
            }
            for(;j<length;j++){
                aseq[j]=' ';
            }
            seq.setSeqChar(aseq);
            seqs.add(seq);
        }
        ali.setLength(length);
        ali.setNoOfSeq(noOfSeq);
        ali.setIndex(index);
//        ali.setSeqNmaes(names);
//        ali.setNameMapSeq(nameMap);
        ali.setSeqs(seqs);
        ali.gapProfile();
        
       
        
        return ali;
    }
    
    public void AlignmentViewTest()
    {
        Sequence testSeq1 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq2 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence[] seqarray = {testSeq1,testSeq2};
        Alignment testAlignment = toAlignment(seqarray);
        
        if (testAlignment != null)
        {
            System.out.println("true");
        }
        
        
    }
    
    
    
}
