/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import psc.gui.RMSDFrame;
import psc.Tree.TreeFrame;
import wbitoolkit.phylo.TreeGUI2;

/**
 *test on RMSDFrame
 * @author zhongxf
 */
public class Test01 
{
     public static  void main(String[] args) 
    {
    double[][] testmatrix= {{1,2,3},{2,3,4},{3,4,5}};
    String[] lables = {"A","B","C"};
    
//    RMSDFrame testFrame = new RMSDFrame(testmatrix,lables);
    
        TreeFrame mytree = new TreeFrame(testmatrix, lables);
    }
    
}
