/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import sun.awt.X11.Screen;

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
        String message = "This prigram is comparsing structres and displaying them; and using differences to build phylogenetics tree."
                + "\n" + "Any problem, contact with joey0576@163.com";
        JTextArea textArea = new JTextArea(message);
        textArea.setLineWrap(true);
        box.add(textArea);
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
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width/2,screen.height/2);
    }
}
