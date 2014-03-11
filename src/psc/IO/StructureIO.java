/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.File;
import java.io.IOException;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.ProteinSequence;
import psc.Sequence.Sequence;
import psc.gui.StateOutput;

/**
 *
 * @author zhongxf
 */
public class StructureIO 
{
    private File[] pdbFiles;
    private Sequence[] aaSequenceses, secondSeqs;
    private ProteinSequence[] proteinSequences;
    private Structure[] structures;
    private StateOutput errorMessage;
    private String[] pdbNames;
    
    public void readPDBFiles(File[] pdbfiles) throws IOException
    {
        getStructure(pdbfiles);
        
    }
    
    public void getStructure(File[] pdbfiles) throws IOException
    {
        this.pdbFiles = pdbfiles;
        Structure[] inputedStructures = extractStructure(pdbfiles);
        this.structures = inputedStructures;
        this.pdbNames = getPbdNames();
        readStructure(structures);
    }
    
    public void readStructure(Structure[] structs)
    {
        ProteinSequence[] proseqs = new ProteinSequence[structs.length];
        Sequence[] aminoseqs = new Sequence[structs.length];
        //String[] structnames = getStructureName();
        for (int i = 0; i < structs.length; i++) 
        {
            Structure tmpStruct = structs[i];
            String aminoChainSeq = "";
            for (Chain chain : tmpStruct.getChains()) 
            {
                aminoChainSeq += chain.getAtomSequence();
            }
            String aminoSeq = aminoChainSeq;
            proseqs[i] = new ProteinSequence(aminoSeq);
            AccessionID tmpID = new AccessionID(pdbNames[i]);
            proseqs[i].setAccession(tmpID);  
            
            aminoseqs[i] = new Sequence(pdbNames[i], aminoSeq);
        }
       
        this.proteinSequences = proseqs;
        this.aaSequenceses = aminoseqs;
    }
    
    public Structure[] getStructures()
    {
        return structures;
    }
    
    public ProteinSequence[] getProteinSequences()
    {
        return proteinSequences;
    }
    
    public Sequence[] getAASequences()
    {
        return aaSequenceses;
    }
    
    public Sequence[] getSecondSeuqnces()
    {
        return secondSeqs;
    }
    
    public void setPDBs(File[] pdbfiles) throws IOException
    {
        this.pdbFiles = pdbfiles;
        readPDBFiles(pdbfiles);
    }
    
    public void setProteinSequences(Sequence[] proseqs)
    {
        this.aaSequenceses = proseqs;
    }
    
    public void setProteinSequences(ProteinSequence[] proseqs)
    {
        this.proteinSequences = proseqs;
    }
    
    public void setSecondSequences(Sequence[] secseqs)
    {
        this.secondSeqs = secseqs;
    }

    private Structure[] extractStructure(File[] pdbfiles) throws IOException 
    {
        if (pdbfiles == null || pdbfiles.length == 0)
        {
            errorMessage.addMessgae("Read PDB files error: can not find PDB files");
            
            return null;
        }
        
        else 
        {
            Structure[] structures = new Structure[pdbfiles.length];
            PDBFileReader pdbReader = new PDBFileReader();

            for (int i = 0; i < pdbfiles.length; i++) 
            {
                structures[i] = pdbReader.getStructure(pdbfiles[i]);
            }
        
            return structures;
        }
       
    }

    private String[] getPbdNames() 
    {
        if (pdbFiles == null || pdbFiles.length == 0)
        {
            return null;
        }
        else 
        {
            pdbNames = new String[pdbFiles.length];
            for (int i = 0; i < pdbFiles.length; i++) 
            {
                String[] tmpstrings = (pdbFiles[i].getName().toString()).split("\\.");
                pdbNames[i] = tmpstrings[0];
            }
            
            return pdbNames;
        }
    }
    
    public String[] getStructureNames()
    {
        return pdbNames;
    }
}
