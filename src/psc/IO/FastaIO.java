/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class FastaIO extends SeqIO
{

    @Override
    public void read(File file) throws FileNotFoundException, IOException 
    {
        ArrayList seqs = new ArrayList<Sequence>();
        int maxLength = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        String title = "";
        StringBuilder seq = new StringBuilder();
        title = br.readLine();
        if (title.substring(0, 1).equals(">") == false)
        {
            throw new java.io.IOException("Bad FASTA format at first line");
        }
        title = title.substring(1, title.length());
        
        int i =0;
        while((line = br.readLine()) != null)
        {
            if (line.startsWith(">") == true)
            {
                addSequence(seqs , i, title, seq.toString());
                
                i++;
                
                if (maxLength < seq.length())
                {
                    maxLength = seq.length();
                }
                title = line.substring(1, line.length());
                seq = new StringBuilder();
            }
            else
            {
                seq.append(line.toUpperCase());
            }
        }
        addSequence(seqs, i, title, seq.toString());
        if (maxLength < seq.length())
        {
            maxLength = seq.length();
        }
        br.close();
        seqs.trimToSize();
        this.setMaxLength(maxLength);
        this.setNoOfSeq(seqs.size());
        this.setSeqs(seqs);
    }

    @Override
    public void write(File file, Sequence seq) throws IOException 
    {
        //
    }

    private void addSequence(ArrayList seqs, int id, String title, String seq) 
    {
        int s = title.indexOf(" ");
        String name = s>0 ? title.substring(0, s): title ;
        String desc = s>0 ? title.substring(s) : "";
        Sequence aseq=new Sequence(id,name,seq);
        aseq.setSeqDescription(desc);
        seqs.add(aseq);
    }
    
}
