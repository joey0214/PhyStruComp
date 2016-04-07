/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.biojava.nbio.structure.StructureException;
import org.biojava.bio.symbol.IllegalSymbolException;
import com.joey.wuhan.phystrucomp.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class LoadPDB 
{
    private JFileChooser load_pdb_dialog;
    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws StructureException
     */
    public LoadPDB() throws FileNotFoundException, IOException, StructureException, IllegalSymbolException 
    {
        load_pdb_dialog = new JFileChooser();
        load_pdb_dialog.setDialogTitle("Load PDB files");
        load_pdb_dialog.setMultiSelectionEnabled(true);//can load multi files
        load_pdb_dialog.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = load_pdb_dialog.showOpenDialog(this.load_pdb_dialog);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            File pdbSelectedFiles[] = load_pdb_dialog.getSelectedFiles();
            PSCgui.inputHub.setPdbFile(pdbSelectedFiles);
            PSCgui.inputHub.update();  
            PSCgui.outpuTextArea.append(pdbSelectedFiles.length + " PDBs loaded");
        }        
    }
}