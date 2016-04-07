/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.SeqMappingStructure;

import org.biojava.bio.seq.Sequence;
import org.biojava.nbio.structure.Structure;

/**
 *
 * @author zhongxf
 */
public class StructureMapping 
{
    Structure structure;
    String pdbid;
    Sequence aaSequence;
    Sequence secSequence;
    public StructureMapping(Structure struct, String pdbidString, Sequence aaseq, Sequence secseq )
    {
        this.structure=struct;
        this.aaSequence=aaseq;
        this.pdbid=pdbidString;
        this.secSequence=secseq;
    }
    
    public Structure getStructure()
    {
        return structure;
    }
    
    public Sequence getaaSequence()
    {
        return aaSequence;
    }
    
    public Sequence getsecSequence()
    {
        return secSequence;
    }
    
    public String getPdbId()
    {
        return pdbid;
    }
    
}
