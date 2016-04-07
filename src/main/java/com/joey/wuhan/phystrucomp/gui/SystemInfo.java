/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.joey.wuhan.phystrucomp.test.SystemUtilTest;

/**
 *
 * @author zhongxf
 */
public class SystemInfo extends JDialog
{
    public SystemInfo() 
    {
//        super(parent, "System Information", true);
        JFrame testFrame = new JFrame("ystem Information");

        int cpuCount = Runtime.getRuntime().availableProcessors();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        Box box = Box.createVerticalBox();
        String message = "\n" + "Currently the number of available processors is " + cpuCount
                + "\n" + "The total memory of jvm is " + totalMemory + "\n"
                + "The free memory of jvm is " + freeMemory + "\n"
                + "The max memory of jvm is " + maxMemory + "\n";
        String askmessage = "Do you want yo use multi processors in later analysis ?  ";
        JTextArea textArea = new JTextArea(message);
        textArea.setLineWrap(true);
        box.add(textArea);
        box.add(Box.createGlue());
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
               
        JPanel p1 = new JPanel();
        JButton singleProcessor = new JButton("NO! Single.");
//        singleProcessor.setSize(20, 50);
        p1.add(singleProcessor);
        
        JButton multiProcessor = new JButton("YES! Multi.");
//        multiProcessor.setSize(20, 50);
        p1.add(multiProcessor);
        
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(getContentPane(), box, gbc, 0, 0, 2, 1);
        
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        add(getContentPane(), singleProcessor, gbc, 0, 1, 1, 1);
        add(getContentPane(), multiProcessor, gbc, 1, 1, 1, 1);
        
        singleProcessor.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //TODO
            }
        });
        
        multiProcessor.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //TODO
            }
        });
        
        setSize(300, 200);  
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width/2,screen.height/2);
    }
    
    private void add(Container cn, Component c, GridBagConstraints gbc, 
            int x, int y, int w, int h) 
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        cn.add(c, gbc);
    }  
 
}
