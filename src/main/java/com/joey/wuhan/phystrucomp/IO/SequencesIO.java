/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.joey.wuhan.phystrucomp.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class SequencesIO 
{
    private ArrayList seqsList=new ArrayList<Sequence>();
    private Sequence[] gapedSeqsList;
    private int maxLength = 0;
    private boolean differentLength = false;
    private ArrayList<String> seqnames = new ArrayList<String>();
    
    public void readSequencesFiles(File[] seqfiles) throws IOException
    {
        for (int i=0; i < seqfiles.length; i++)
        {
            BufferedReader br = new BufferedReader(new FileReader(seqfiles[i]));
            String line = "";
            String title = "";
            StringBuilder seq = new StringBuilder();
            //title = br.readLine();
            
            if(br.readLine().substring(0, 1).equals(">") == false)
            {
                throw new java.io.IOException("Bad FASTA format on first line\n");
            }
            
            else {
                title = title.substring(1, title.length());
                int index = 0;
                while ((line = br.readLine()) != null) 
                {
                    if (line.startsWith(">") == true) 
                    {
                        addSequence(seqsList, index, title, seq.toString());

                        index++;
                        
                        //check if the sequences are the same length
                        if (maxLength < seq.length()) 
                        {
                            maxLength = seq.length();
                            differentLength = true;
                        }
                        //the title of current sequence
                        title = line.substring(1, line.length());
                        seq = new StringBuilder();
                    } 
                    
                    else 
                    {
                        //current sequence
                        seq.append(line.toUpperCase());
                    }
                }

                //add the last sequence
                addSequence(seqsList, i, title, seq.toString());
                
                if (maxLength < seq.length()) 
                {
                    maxLength = seq.length();
                }

                br.close();
                seqsList.trimToSize();
            }
        }
        
        checkLength(differentLength);

    }
    
//    public void readSecondStruSeq(){}
   
    public Sequence[] getOriginSequences()
    {
        Sequence[] sequences = new Sequence[seqsList.size()];
        for (int i=0; i < seqsList.size(); i ++)
        {
            sequences[i] = (Sequence)seqsList.get(i);
        }
        return sequences;
    }
    
    public Sequence[] getGapedSequences()
    {
        return gapedSeqsList;
    }
    
    public void getSecondStruSeq(){}
    
    public String[] getSequencesNames()
    {
        String[] seqLabels = new String[seqnames.size()];
        for (int i =0; i < seqnames.size(); i++)
        {
            seqLabels[i] = (String)seqnames.get(i);
        }
        
        return seqLabels;
    }

    private void addSequence(ArrayList seqsList, int id, String title, String seqString) 
    {
        int s = title.indexOf(" ");
        String name = s > 0 ? title.substring(0, s) : title;
        seqnames.add(name);
        String desc = s > 0 ? title.substring(s) : "";
        Sequence aseq = new Sequence(id, name, seqString);
        aseq.setSeqDescription(desc);
        seqsList.add(aseq);
    }

    private void checkLength(boolean differentLength) 
    {
        gapedSeqsList = new Sequence[seqsList.size()];
        
        if (differentLength)
        {
            for (int i=0; i < seqsList.size(); i++)
            {
                Sequence tmp = (Sequence)seqsList.get(i);
                String tmpchar= tmp.getSeqChar().toString();
                while (tmpchar.length() < maxLength)
                {
                    tmpchar += "-";
                }
                tmp.setSeqChar(tmpchar.toCharArray());
                gapedSeqsList[i] = tmp;
            }
        }
    }
    
    
    
}
