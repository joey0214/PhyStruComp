/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

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
public class SliderSlectionPanel extends JPanel implements ActionListener
{
    private JRadioButton fixedButton, flexibleButton;
    private  JButton retreeButton;
    private JTextField valueMessageArea;
    private JSlider slider;
    ChangeListener sliderListener;
    int windowSize, startI,endI;
    public SliderSlectionPanel()
    {
        setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "OutPut"));
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
        
        add(fixedButton);
        add(slider);
        add(valueMessageArea);
        add(flexibleButton);
        
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
    
    
}
