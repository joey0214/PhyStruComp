/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Tools;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author zhongxf
 */
public class TextPaneListRenderer extends JTextPane implements ListCellRenderer
{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        this.setDocument((DefaultStyledDocument)value);
        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else 
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        
        this.setOpaque(true);
        return this;
        
    }
    
}
