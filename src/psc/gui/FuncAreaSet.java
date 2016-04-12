/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import org.biojava.bio.structure.StructureException;



/**
 *
 * @author zhongxf
 */
class FuncAreaSet extends JFrame
{

    public FuncAreaSet() 
    {
        super();
        setTitle("set function domains");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        show();    
    }

    private void initComponents() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exitForm();
            }
        });

        setSize(300, 300);
    }
    private void exitForm() {
        //System.exit(0);
        setVisible(false);
    }
    
}

