/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.util.ArrayList;
import java.util.List;
import org.biojava.nbio.alignment.Alignments;
import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.util.ConcurrencyTools;
import com.joey.wuhan.phystrucomp.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class SeqAligner 
{
    private ProteinSequence[] proseq , alignedProseq;
    private String[] proseqnames;
    
    public void setSequernce(ProteinSequence[] proteinseq)
    {
        this.proseq = proteinseq;
        getpPoseqnames();
    }
    
    public ProteinSequence[] getAlignedSeq()
    {
        return alignedProseq;
    }
    
    public void alignRun()
    {
        List<ProteinSequence> proseqList = convertToList(proseq);
        alignedProseq = new ProteinSequence[proseq.length];
       
        Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(proseqList);
        System.out.println("Clustalw: \n" + profile);
        System.out.println( "profile.getSize()" + profile.getSize());
        List<AlignedSequence<ProteinSequence, AminoAcidCompound>> aaAlignedList = profile.getAlignedSequences();
        
        alignedProseq = convertToProSeq(aaAlignedList);
      
         ConcurrencyTools.shutdown(); 
    }
    
    public String[] getAlignedSeqStrings()
    {
        String[] seqstrings = new String[alignedProseq.length];
        for (int i = 0; i < alignedProseq.length; i++)
        {
            seqstrings[i] = alignedProseq[i].getSequenceAsString();
        }
        return seqstrings;
    }
    
    public String[] getAlignedSeqNames()
    {
        String[] nameStrings = new String[alignedProseq.length];
        for (int i =0; i < alignedProseq.length; i++)
        {
            nameStrings[i] = alignedProseq[i].getAccession().toString();
        }
        return nameStrings;
    }

    private List<ProteinSequence> convertToList(ProteinSequence[] proseq) 
    {
        List<ProteinSequence> proseqlist = new ArrayList<ProteinSequence>();
        for (int i =0 ; i < proseq.length ;i++)
        {
            proseqlist.add(proseq[i]);
        }
        return  proseqlist;
    }

    private ProteinSequence[] convertToProSeq(List<AlignedSequence<ProteinSequence, AminoAcidCompound>> aaAlignedList) 
    {
        ProteinSequence[] tmpProses = new ProteinSequence[aaAlignedList.size()];
        for (int i = 0; i < aaAlignedList.size(); i++)
        {
            tmpProses[i] = new ProteinSequence(aaAlignedList.get(i).toString());
            AccessionID tmpID = new AccessionID(proseqnames[i]);
            tmpProses[i].setAccession(tmpID); 
        }
        return tmpProses;
    }

    private void getpPoseqnames() 
    {
        proseqnames = new String[proseq.length];
        for (int i =0 ; i < proseq.length; i ++)
        {
            proseqnames[i] = proseq[i].getAccession().toString();
        }
    }
}
