/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.JFrame;
import psc.gui.SliderSlectionPanel;

/**
 *
 * @author zhongxf
 */
public class TestOnSlider 
{
    
    public static void main(String argsString[])
    {
         SliderSlectionPanel test = new SliderSlectionPanel();
         JFrame mainFrame = new JFrame("Test on slider");
         mainFrame.add(test);
         mainFrame.setSize(400, 400);
         mainFrame.setVisible(true);
    }
   
    
    
}
