/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.File;
import java.io.IOException;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
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
    
    public void readPDBFiles(File[] pdbfiles) throws IOException
    {
        this.pdbFiles = pdbfiles;
        this.structures = extractStructure(pdbfiles);
        
    }
    
    public void getStructure(File[] pdbfiles) throws IOException
    {
        this.pdbFiles = pdbfiles;
        Structure[] inputedStructures = extractStructure(pdbfiles);
        this.structures = inputedStructures;
    }
    
    public void readStructure(){}
    
    public Structure[] getStructures()
    {
        return structures;
    }
    
    public void getProteinSequences(){}
    
    public void getSecondSeuqnces(){}
    
    public void setPDBs(File[] pdbfiles) throws IOException
    {
        this.pdbFiles = pdbfiles;
        readPDBFiles(pdbfiles);
    }
    
    public void setProteinSequences(){}
    
    public void setSecondSequences(){}

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
    
}
