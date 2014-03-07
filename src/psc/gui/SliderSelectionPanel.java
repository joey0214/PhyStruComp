/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author zhongxf
 */
public class SliderSelectionPanel extends JPanel implements ActionListener
{
    private JRadioButton fixedButton, flexibleButton;
    private  JButton retreeButton;
    private JTextField valueMessageArea;
    private JSlider slider;
    ChangeListener sliderListener;
    int windowSize, startI,endI;
    private GridBagConstraints gbc;
    
    public SliderSelectionPanel()
    {
        super();
        gbc = new GridBagConstraints();
        
        setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), " "));
        windowSize = 50;
        startI =0;
        endI =0;
        
        valueMessageArea = new JTextField();
        sliderListener = new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) 
            {
                JSlider source = (JSlider)ce.getSource();
                valueMessageArea.setText("The select value is ：" + source.getValue());
                
            }
        };
        fixedButton = new JRadioButton("Fixed Sized", false);
        fixedButton.addActionListener(this);
        
        flexibleButton = new JRadioButton("Filexible Size", false);
        flexibleButton.addActionListener(this);
        
        retreeButton = new JButton("Re-Tree");
        retreeButton.addActionListener(this);        
        
        slider = new JSlider(0,100);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setLocation(new Point(0, 0));
//        slider.setPaintLabels(true);
        slider.addChangeListener(sliderListener);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        
//        add(fixedButton);
//        add(slider);
//        add(valueMessageArea);
//        add(flexibleButton);
//        
//        add(retreeButton);
//        
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
//        add(toolPanel, singleSlider, gbc, 0, 0, 1, 1);
        add(this, fixedButton, gbc, 0, 0, 1, 1);
        add(this, slider, gbc, 0, 1, 1, 1);
        add(this, valueMessageArea, gbc, 0, 2, 1, 1);
        add(this, flexibleButton, gbc, 0, 3, 1, 1);
        add(this, retreeButton, gbc, 0, 4, 1, 1);
    }
    /**
     * get the value of slider selection
     *
     * @param startI
     * @param endI
     * @return position
     */
    public int[] getValus()
    {
        int[] position = new int[2];
        position[0] = startI;
        position[1] = endI;
        return position;
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
       if (ae.getSource() == fixedButton)
       {
           
       }
       
       if (ae.getSource() == flexibleButton)
       {
           //TODO
       }
       
       if (ae.getSource() == retreeButton)
       {
           //TODO
           //re-drew the tree based on the structure region selected.
       }
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
