/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.test;

import javax.swing.JFrame;
import com.joey.wuhan.phystrucomp.gui.SliderSelectionPanel;

/**
 *
 * @author zhongxf
 */
public class TestOnSlider 
{
    
    public static void main(String argsString[])
    {
         SliderSelectionPanel test = new SliderSelectionPanel();
         JFrame mainFrame = new JFrame("Test on slider");
         mainFrame.add(test);
         mainFrame.setSize(400, 400);
         mainFrame.setVisible(true);
    }
   
    
    
}
