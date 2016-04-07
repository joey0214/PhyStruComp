/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Trees;

import javax.swing.JFrame;
import com.bigiov.wbtoolkit.phylo.NJTree;
import com.bigiov.wbtoolkit.phylo.Tree;
import com.bigiov.wbtoolkit.phylo.TreeGUI;
//import wbitoolkit.phylo.TreeGUI2;
import com.bigiov.wbtoolkit.phylo.TreeIO;

/**
 *DO NOT USE THIS, JUST A TEST
 * @author zhongxf
 */
public class PSCTree 
{
    private double[][] matrix;
    private String[] labels ;
    
    public void setMatrix(double[][] distanceMatrix)
    {
        this.matrix = distanceMatrix;
    }
    
    public void setLabels(String[] treeLabels)
    {
        this.labels = treeLabels;
    }
    
    public void getTree()
    {
        TreeIO treeIO = new TreeIO();
        NJTree njt = new NJTree(matrix, labels);
        njt.buildTree();
        Tree tree = njt.getTree();
        
        JFrame f = new JFrame();
        f.setSize(400, 400);
        TreeGUI tg = new TreeGUI(tree);
//        TreeGUI2 tg2 = new TreeGUI2(tree);
        f.add(tg);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
}
