/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author zhongxf
 */
public class AboutDialog extends JDialog
{
    public AboutDialog(JFrame  parent)
    {
        super(parent, "About Dialog", true);
        
        Box box = Box.createVerticalBox();
        box.add(new JLabel("asdfghjkl;"));
        box.add(Box.createGlue());
        getContentPane().add(box, "Center");
        
        JPanel p1 = new JPanel();
        JButton ok = new JButton("Ok");
        p1.add(ok);
        getContentPane().add(p1, "South");
        
        ok.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        setSize(300, 200);    
    }
}
