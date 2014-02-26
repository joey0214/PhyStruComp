/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.symbol.IllegalSymbolException;
import psc.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class AppendPDB 
{
    private final JFileChooser append_pdb_dialog;
    
    public AppendPDB() throws FileNotFoundException, IOException, StructureException, IllegalSymbolException 
    {
        append_pdb_dialog = new JFileChooser();
        append_pdb_dialog.setDialogTitle("Append PDB files");
        append_pdb_dialog.setMultiSelectionEnabled(true);//can load multi files
        append_pdb_dialog.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = append_pdb_dialog.showOpenDialog(this.append_pdb_dialog);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        } 
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            File[] pdbSelectedFiles = append_pdb_dialog.getSelectedFiles();
            PSCgui.displayInGUI2.appendFiles(pdbSelectedFiles);
            PSCgui.displayInGUI2.update();
        }
    }
}
