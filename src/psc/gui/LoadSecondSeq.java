/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.biojava.bio.symbol.IllegalSymbolException;

/**
 *
 * @author zhongxf
 */
public class LoadSecondSeq implements ActionListener
{
    private JRadioButton check1,check2;
    private int method=-1;
    private JButton okbutton,cancelbutton;
    private  JFrame jframe,errorFrame;
    
    private  void chooseDialog() 
    {
        jframe = new JFrame("How to Get secondary sequence");
        jframe.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jframe.setSize(330, 200);
        jframe.setLayout(new GridLayout(4,1));
        jframe.show();
        
        ButtonGroup checkgroup = new ButtonGroup();
        check1 = new JRadioButton("Load from existed file",false);
        check2 = new JRadioButton("Predict from PSIPRED",true);
        checkgroup.add(check1);
        checkgroup.add(check2);
        
        okbutton = new JButton("OK");
        okbutton.addActionListener(this);
        cancelbutton = new JButton("Cancel");
        cancelbutton.addActionListener(this);
        jframe.add(check1);
        jframe.add(check2);
        jframe.add(okbutton);
        jframe.add(cancelbutton);
    }

    public LoadSecondSeq()
    {
        chooseDialog();
        PSCgui.jmolPanel.executeCmd("Superposing");

    }
    
    private void detectChoice() 
    {
        if (check1.isSelected())        {   method = 0;}
        else if (check2.isSelected())   {   method = 1;}
        else                            {   method =-1;}
    }

    @Override
    public void actionPerformed(ActionEvent event) 
    {
        if (event.getSource() == okbutton)
        {
            detectChoice();
            switch (method) {
                case -1:
                    JOptionPane.showMessageDialog(errorFrame,
                            "Eggs are not supposed to be green.");
                case 0:
                    try 
                    {
                        loadSecseq();
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(LoadSecondSeq.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IllegalSymbolException ex) 
                    {
                        Logger.getLogger(LoadSecondSeq.class.getName()).log(Level.SEVERE, null, ex);
                    }
                case 1:
                    getpsipred();
            }
        }
        if (event.getSource() == cancelbutton)
        {
             jframe.setVisible(false);
        }
    }

    private void loadSecseq() throws IOException, IllegalSymbolException 
    {
         JFileChooser loadDialog = new JFileChooser();
        loadDialog.setDialogTitle("Load PDB files");
        loadDialog.setMultiSelectionEnabled(true);//can load multi files
        loadDialog.setDialogType(JFileChooser.OPEN_DIALOG);

        int c = loadDialog.showOpenDialog(loadDialog);
        if (c != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        else if (c == JFileChooser.APPROVE_OPTION) 
        {
            File[] secseqFiles = loadDialog.getSelectedFiles();
            PSCgui.displayInGUI2.setSecseqFile(secseqFiles);
            PSCgui.displayInGUI2.update();           
        }  
    }

    private void getpsipred() 
    {
        //TODO
        //get predict secondary sequences from psipred
    }

}
