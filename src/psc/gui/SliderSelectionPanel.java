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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

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
    ChangeListener sliderListener, multiSliderListener;
    int windowSize, startI,endI;
    private GridBagConstraints gbc;
    
    public SliderSelectionPanel()
    {
//       JComponent pane;
//        pane = (JComponent) new JPanel();
////        pane.setLayout(new GridBagLayout());
//        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        
        JPanel ipanel = new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
          
        gbc = new GridBagConstraints();
        
        setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), " "));
        windowSize = 50;
        startI =0;
        endI =0;
        
        valueMessageArea = new JTextField();
        sliderListener = new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent ce) 
            {
                JSlider source = (JSlider)ce.getSource();
                valueMessageArea.setText("The select value is ：" + source.getValue());
                
            }
        };
        
//        multiSliderListener = new ChangeListener() 
//        {
//            @Override
//            public void stateChanged(ChangeEvent ce) 
//            {
//                MultiSlider source = (MultiSlider)ce.getSource();
//                valueMessageArea.setText("The select value is ：" + source.getValue());
//                System.out.println(source.getValue());
//            }
//        };
                
        fixedButton = new JRadioButton("Fixed Sized", false);
        fixedButton.setSize(50, 20);
        fixedButton.addActionListener(this);
        
        flexibleButton = new JRadioButton("Filexible Size", false);
        flexibleButton.setSize(50, 20);
        flexibleButton.addActionListener(this);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(fixedButton);
        buttonGroup.add(flexibleButton);
        
        retreeButton = new JButton("Re-Tree");
        retreeButton.setSize(40, 20);
        retreeButton.addActionListener(this);        
        
        slider = new JSlider(0,100);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setLocation(new Point(0, 0));
//        slider.setPaintLabels(true);
        slider.addChangeListener(sliderListener);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setValue(0);
        
        
//        MultiSlider multiSlider = new MultiSlider();
//        multiSlider.addChangeListener(multiSliderListener);
        
        add(fixedButton);
        add(slider);
        add(valueMessageArea);
        add(flexibleButton);   
//        add(multiSlider);
        add(retreeButton);
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
