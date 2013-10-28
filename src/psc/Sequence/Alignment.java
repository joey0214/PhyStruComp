/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
//import org.biojava.bio.seq.Sequence;
import org.biojava3.core.sequence.ProteinSequence;

/**
 *
 * @author zhongxf
 */
public class Alignment 
{
    private int seqsCount;
    private int gapCount;
    private char gap='-';
    private int length ;
    private ArrayList[] gapMap ;
    private ArrayList<Sequence> seqs;
    private int[] gapFreeIndex;
    private int[] seqIndex;
    private int[] columnIndex;
    private HashMap<String,Sequence> nameMapSeq ;
    private String[] seqNames ;

   
    

    public Alignment(int seqsCount, int gapCount, int length) {
        this.seqsCount = seqsCount;
        this.gapCount = gapCount;
        this.length = length;
    }
    

    public Alignment(int seqsCount, int gapCount) {
        this.seqsCount = seqsCount;
        this.gapCount = gapCount;
    }

    public Alignment() {
    }
    
    public Alignment(Sequence[] seqs,int type)
    {
        createAlignment(seqs, type);
    }
    
    public Alignment(ProteinSequence[] proseqs)
    {
        createAlignment3(proseqs);
    }

    private void createAlignment(Sequence[] seqs, int type) 
    {
        if (type == 0) //nucleid acid symbol
        {
            createAlignment1(seqs);
        }
        
        if (type == 1)//amino acid symbol
        {
            createAlignment2(seqs);
        }
    }

    

    private void createAlignment1(Sequence[] seqs) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createAlignment2(Sequence[] seqs) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void createAlignment3(ProteinSequence[] proseqs) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void gapProfile()
    {
        gapMap =new ArrayList[seqsCount];
        boolean[] notGaps = new boolean[length];
        for (int i =0; i <length; i++)
        {
            notGaps[i] =true;
            
        }
        int gapsCount =0;
        for (int i =0; i <seqsCount; i ++)
        {
            ArrayList gapsArrayList = new ArrayList();
            char[] seqChar = seqs.get(i).getSeqChar();
            for (int j =0; j <length; j ++)
            {
                if (seqChar[j]<'A' || seqChar[j]>'Z')
                {
                    gapsArrayList.add(seqChar[j]);
                    if (notGaps[j])
                    {
                        notGaps[j] = false;
                        gapsCount +=1;
                    }
                }
            }
        }
        
        gapFreeIndex = new int[length-gapsCount];
        int j=0;
        for (int i=0; i <length; i++)
        {
            if (notGaps[i])
            {
                gapFreeIndex[j++] = i;
                
            }
        }
    }
    
    public Sequence getSeqByID(int idI)
    {
        if (idI<0 || idI >= seqs.size())
        {
            return null;
        }
        
        return seqs.get(idI);
    }
    
    public Sequence getSeqByName(String name)
    {
        return nameMapSeq.get(name);
    }
    
    public Sequence getSeqByIndex(int ind)
    {
        if (ind<0 || ind >= seqs.size())
        {
            return null;
        }
        return seqs.get(seqIndex[ind]);
    }
    
    public int getLength()
    {
        return length;
    }
    
    public void setLength(int newLength)
    {
        this.length = newLength;
    }
    
    public int getWidth()
    {
        return length;
    }
    
    public void setwidth(int newWidth)
    {
        this.length = newWidth;
    }
    
    public int getSeqCount()
    {
        return seqsCount;
    }
    
    public void setSeqCount(int newSeqCount)
    {
        this.seqsCount = newSeqCount;
    }
    
    public String[] getSeqNames()
    {
        return seqNames;
    }
    
    public void setSeqNmaes(String[] newnames)
    {
        this.seqNames = newnames;
    }
    
    public int getHeight()
    {
        return seqsCount;
    }
    
    public void setHeight(int seqsnumber)
    {
        this.seqsCount = seqsnumber;
    }
    
    public int[] getIndex()
    {
        return seqIndex;
    }
    
    public void setIndex(int[] newindex)
    {
        this.seqIndex = newindex;
    }
    
    public HashMap<String, Sequence> getNameMapSeq()
    {
        return nameMapSeq;
    }
    
    public void setNameMapSeq(HashMap<String, Sequence>nameMap)
    {
        this.nameMapSeq = nameMap;
    }
    
    public int[] getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(int[] coluindex)
    {
        this.columnIndex = coluindex;
    }
    
    public ArrayList<Sequence> getSeqs()
    {
        return seqs;
    }
    
    public void setSeqs(ArrayList<Sequence> newseqs)
    {
        this.seqs = newseqs;
    }
    
    public int[] getGapFreeIndex()
    {
        return gapFreeIndex;
    }
    
    public String getGapFreeSeq(Sequence seq)
    {
        char[] ssCs = seq.getSeqChar();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i <gapFreeIndex.length; i++)
        {
            stringBuilder.append(ssCs[gapFreeIndex[i]]);
        }
        return stringBuilder.toString();
    }
    
    public void getGapFreeSeq(ArrayList<Sequence> seqlist)
    {
        for (int i=0; i<seqlist.size();i++)
        {
            //TODO
        }
    }
    
    public char getResAt(int seqindex,int resindex)
    {
        return getSeqByIndex(seqindex).getSeqChar()[resindex];
    }
    
    public int getSitePositionAt(int seqindex, int coluIndex)
    {
        int id = getSeqByIndex(seqindex).getId();
        ArrayList gap = gapMap[id];
        if(gap.contains(coluIndex))
        {
            return -1;
        }
        int gapNum =0;
        for (Object i:gap)
        {
            int g =(Integer)i;
            if (g<= coluIndex)
            {
                gapNum +=1;
            }
        }
        return (coluIndex-gapNum+1);
    }
}
