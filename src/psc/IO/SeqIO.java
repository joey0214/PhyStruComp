/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public abstract  class SeqIO 
{
    private int noOfSeq;
    private int maxLength;
    private List<Sequence> seqs;
    
    public abstract void read(File file) throws FileNotFoundException, IOException;
    
    public abstract void write(File file, Sequence seq) throws IOException;

    public int getNoOfSeq()
    {
        return noOfSeq;
    }

    public int getMaxLength() 
    {
        return maxLength;
    }

    public List<Sequence> getSeqs() 
    {
        return seqs;
    }

    public void setNoOfSeq(int noOfSeq) 
    {
        this.noOfSeq = noOfSeq;
    }

    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    public void setSeqs(List<Sequence> seqs)
    {
        this.seqs = seqs;
    }
}
