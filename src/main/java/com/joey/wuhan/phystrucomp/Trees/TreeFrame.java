/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Trees;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.joey.wuhan.phystrucomp.gui.StateOutput;
import com.bigiov.wbtoolkit.phylo.NJTree;
import com.bigiov.wbtoolkit.phylo.Tree;
import com.bigiov.wbtoolkit.phylo.TreeIO;


/**
 *
 * @author zhongxf
 */
public class TreeFrame extends JFrame implements ActionListener
{
    private JMenuItem saveTreeItem;
    private JMenuItem printItem;
    private JMenuItem exitItem;
    private JMenu optionMenu;
    private StateOutput message;
    private double [][] variationMatrix;
    private String[] labels ;
    private Tree tree;
    private TreeIO treeIO;

    public TreeFrame(double[][] rmsdMartix, String[] headers) 
    {
        super();
        setTitle("Tree ");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.variationMatrix = rmsdMartix;
        this.labels = headers;
        initComponents();
        buildTree();
        show();
    }
    
    private void saveTree() throws IOException
    {
        String treeInString = treeIO.writeTree(tree);
        System.out.println(treeInString);
        String fileName = "variation_tree_in_newick.nwk";
        String savePath = System.getProperty("user.dir");
        
        FileWriter writer = new FileWriter(fileName);
        writer.write(treeInString);
        writer.close();
//        message.addMessgae(savePath + "/"+fileName);
        System.out.println(savePath + "/"+fileName);
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == saveTreeItem)
        {
            try {
                saveTree();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(TreeFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ae.getSource() == printItem)
        {
            //TODO
        }
        
        if (ae.getSource() == exitItem)
        {
            setVisible(false);
            //System.exit(0);
        }
    }

    private void initComponents() 
    {
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                setVisible(false);
            }
        });

        setSize(500, 500);

        saveTreeItem = new JMenuItem("Save");
        printItem = new JMenuItem("print");
        exitItem = new JMenuItem("exit");
       
        optionMenu = new JMenu("Option");
        optionMenu.addSeparator();
        optionMenu.add(saveTreeItem);
        optionMenu.addSeparator();
        optionMenu.add(printItem);
        optionMenu.addSeparator();
        optionMenu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(optionMenu);
        setJMenuBar(menuBar);

        saveTreeItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);
    }

    private void buildTree() 
    {
        treeIO = new TreeIO();
        NJTree mynj = new NJTree(variationMatrix, labels);
        mynj.buildTree();
        Tree njtree = mynj.getTree();
        this.tree = njtree;
        
        TreePanel tg = new TreePanel(njtree);
//        TreeGUI tg2 = new TreeGUI(tree);

        this.add(tg);
        this.setVisible(true);
    }
    
}
