/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Tree;

import javax.swing.JFrame;
import wbitoolkit.phylo.NJTree;
import wbitoolkit.phylo.Tree;
import wbitoolkit.phylo.TreeGUI;
import wbitoolkit.phylo.TreeGUI2;
import wbitoolkit.phylo.TreeIO;

/**
 *
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
