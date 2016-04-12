/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.io.BufferedReader;
import java.io.Reader;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileParser;

/**
 *
 * @author zhongxf
 */
//this class is to check the input structures have the same chain to compare, if have different chains
// it can auto extract the same chain to do subsequent analysis.

public class ChainConformity 
{
    private Structure[] readInStruct,conforStructs;
    private  Structure seed ;
    public void readIn(Structure[] structures)
    {
        this.readInStruct = structures;
        checkConformity();
    }
    
    public void setSeed(Structure seedStructure)
    {
        this.seed = seedStructure;
    }
    
    public Structure[] getConformityStruct()
    {
        return conforStructs;
    }

    //sperate structure 
    private void checkConformity() 
    {
        for (int i =0; i < readInStruct.length; i++)
        {
            //readInStruct[i].
            
            for (Chain chain : readInStruct[i].getChains()) 
            {
                
            }
            
        }
    }
    
    
    
    
}
