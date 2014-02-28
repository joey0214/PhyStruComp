/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.IO;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.symbol.IllegalSymbolException;
import psc.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class LoadSeqs 
{
     private JFileChooser loadSeqDialog;
    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws StructureException
     */
    public LoadSeqs() throws FileNotFoundException, IOException, StructureException, IllegalSymbolException 
    {
        loadSeqDialog = new JFileChooser();
        loadSeqDialog.setDialogTitle("Load PDB files");
        loadSeqDialog.setMultiSelectionEnabled(true);//can load multi files
        loadSeqDialog.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = loadSeqDialog.showOpenDialog(this.loadSeqDialog);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            File seqFiles[] = loadSeqDialog.getSelectedFiles();
            PSCgui.inputHub.setFile(seqFiles);
            PSCgui.inputHub.update();  
            PSCgui.outpuTextArea.append(seqFiles.length + " seqeuences loaded");
        }        
    }

}
