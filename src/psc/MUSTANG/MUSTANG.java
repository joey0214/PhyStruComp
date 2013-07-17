/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.MUSTANG;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author zhongxf
 */
public class MUSTANG 
{
    private Structure[] rawStructure ,alignedStructure;
    private String pwpath = System.getProperty("user.dir");
    private String inpath = null;
    private Structure superimposedStructure = null;
    
    
    public void setRawStructPath(String inputPath)
    {
        File infile = new File(inputPath);
        if (infile.exists() && infile.isDirectory())
        {
            this.inpath = inputPath;
        }
        else 
        {
            System.out.println("Error!! Check your input directory!");
        }
    }
    
    public void setRawStructure(Structure[] stru)
    {
        if (stru != null && stru.length!=0)
        {
            this.rawStructure = stru;
        }
        else 
        {
            System.out.println("Error! Check your structure files");
        }
    }
    
    public void getAlignedStructure()
    {
        //TODO
    }
    
    public void runMUSTANG()
    {
        //TODO
        //run MUSTANG in java 
    }
    
    private void mkdir()
    {
        String tmpString = "/alignedResult";
        File file = new File(pwpath+"/alignedResult");
        if (!file.exists())
        {
            file.mkdir();
        }
        else 
        {
            int tmpInt = 1;
            File newfile = new File(pwpath+tmpString+tmpInt);
            while (!newfile.exists())
            {
                
            }
        }
        
    }
    
}
