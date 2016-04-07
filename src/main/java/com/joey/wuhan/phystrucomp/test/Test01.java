/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.test;

import com.joey.wuhan.phystrucomp.gui.RMSDFrame;
import com.joey.wuhan.phystrucomp.Trees.TreeFrame;
import com.bigiov.wbtoolkit.phylo.TreeGUI2;

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
