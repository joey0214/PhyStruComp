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
    private Alignment ali;

    @Override
    public void read(File file) throws FileNotFoundException, IOException {
        loadAlignment(getSeqIO(file));
    }

    @Override
    public void write(File file, Sequence seq) throws IOException {
        
    }
    
    private SeqIO getSeqIO(File file) throws FileNotFoundException, IOException{
        FastaIO sio=new FastaIO();
        sio.read(file);
        return sio;
    }
    
    private void loadAlignment(SeqIO sio){
        ali=new Alignment();
        FastaIO fa=(FastaIO)sio;
        int length=fa.getMaxLength();
        int noOfSeq=fa.getNoOfSeq();
        int[] index=new int[noOfSeq];
        String[] names=new String[noOfSeq];
        HashMap nameMap=new HashMap<String,Sequence>();
        ArrayList seqs=new ArrayList<Sequence>(noOfSeq);
        for(int i=0;i<noOfSeq;i++){
            index[i]=i;
            Sequence seq=fa.getSeqs().get(i);
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
        ali.setNames(names);
        ali.setNameMap(nameMap);
        ali.setSeqs(seqs);
        ali.gapProfile();
    }

    /**
     * @return the ali
     */
    public Alignment getAlignment() {
        return ali;
    }

    /**
     * @param ali the ali to set
     */
    public void setAlignment(Alignment ali) {
        this.ali = ali;
    }
//    load aligned sequences
    public void loadAlignment(Sequence[] sequences)
    {
        int maxLen = findMaxLength(sequences);
        ali = new Alignment();
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
        ali.setLength(maxLen);
        ali.setNoOfSeq(noOfSeq);
        ali.setIndex(index);
        ali.setNames(names);
        ali.setNameMap(nameMap);
        ali.setSeqs(seqs);
        ali.gapProfile();
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
    
//    public Alignment getAlignment()
//    {
//        System.out.println("align.getNoOfSeq()  "+ali.getNoOfSeq());
//        System.out.println("align.getSeqCount()   "+ali.getSeqCount());
//
//        return ali;
//    }
    
//    public void setAlignment(Alignment ali)
//    {
//        this.align = ali;
//    }

    private int findMaxLength(Sequence[] sequences) 
    {
        int max = 0;
        for (int i =0 ; i < sequences.length; i ++)
        {
            int tmpLen = sequences[i].getSeqChar().length;
//            System.out.println(tmpLen);
            if (max < tmpLen)
            {
                max = tmpLen;
            }
        }
//        System.out.println(max);
        return max;
    }

//    @Override
//    public void write(File file, Sequence seq) throws IOException 
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
