/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileParser;
import org.biojava.bio.structure.io.PDBFileReader;
import psc.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class Separater 
{
    private File seedPDBfile;
    private File[] targetFiles;
    private Structure seedStructure;
    private Structure[] targetStructures; 
    private int seedLen ;
    
    //generate a dialog for user to choose pdb files
    public void Separater() throws IOException
    {
        seedChooser();
        //targetChooser();
        paraseFiles();
    }
    
    //set seed to search similar substructure, must be the structure want to analysis.
    //not contains other elements
    public void setSeed(){}
    
    //structures contain substructure want to extract
    public void setTarget(){}
    
    //return structures for subsequent analysis
    public void getStructures(){}

    private void seedChooser() 
    {
        JFileChooser seedChooser = new JFileChooser();
        seedChooser.setDialogTitle("Choose Seed PDB File");
        seedChooser.setMultiSelectionEnabled(false);//can load multi files
        seedChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = seedChooser.showOpenDialog(seedChooser);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            this.seedPDBfile = seedChooser.getSelectedFile();      
        }        
    }

    private void targetChooser() 
    {
        JFileChooser targetChooser = new JFileChooser();
        targetChooser.setDialogTitle("Choose Target PDB Files");
        targetChooser.setMultiSelectionEnabled(true);//can load multi files
        targetChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = targetChooser.showOpenDialog(targetChooser);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            this.targetFiles = targetChooser.getSelectedFiles();  
        } 
    }

    private void paraseFiles() throws IOException 
    {
        PDBFileParser pdbFileParser = new PDBFileParser();
        PDBFileReader pdbfr = new PDBFileReader();
        seedStructure = pdbfr.getStructure(seedPDBfile);
        seedStructure.getChain(0).toString();
        //Structure tmp = pdbFileParser.parsePDBFile(null);
        
       // seedStructure.getChain(0).toString().
          System.out.println(seedStructure.getChain(0).toString()); 
        seedStructure.getChains();
        System.out.println(seedStructure.getChains().size());
        for (int i =0; i < (seedStructure.getChains().size()); i ++)
        {
            System.out.println(seedStructure.getChain(i).toString()); 
        }
    }
    
}
