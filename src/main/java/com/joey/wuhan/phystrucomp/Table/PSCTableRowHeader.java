/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zhongxf
 */
public class PSCTableRowHeader extends JTable{
    private JTable ownerTable;
    private Object[] header;
 
    public PSCTableRowHeader(JTable ownerTable, Object[] header, int width){  
        super();
        this.ownerTable=ownerTable;
        this.header=header;
        this.setModel(new RowHeaderTableModel(header));          
//        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setRowSelectionAllowed(false);
        this.setPreferredScrollableViewportSize (new Dimension(width,0));
        this.getColumnModel().getColumn(0).setPreferredWidth(width);  
        this.setDefaultRenderer(Object.class,new RowHeaderRenderer(ownerTable,this)); 
          
    }
    
    public void setTableRowHeader(Object[] header){
        ((RowHeaderTableModel)getModel()).setRowHeader(header);
    }
   
    class RowHeaderRenderer extends JLabel implements TableCellRenderer,ListSelectionListener
    {  
        JTable ownerTable;
        JTable table; 
        public RowHeaderRenderer(JTable ownerTable,JTable table)
        {  
            this.ownerTable = ownerTable;  
            this.table=table;  
            ListSelectionModel listModel=ownerTable.getSelectionModel();  
            listModel.addListSelectionListener(this);   
        }  
        @Override
        public Component getTableCellRendererComponent(JTable table,Object obj,  
                boolean isSelected,boolean hasFocus,int row, int col)
        {  
            JTableHeader header = ownerTable.getTableHeader();  
            this.setOpaque(true);             
            setBorder(header.getBorder());  
            setHorizontalAlignment(CENTER);
            setBackground(header.getBackground());  
            if ( isSelect(row) )
            {  
                setForeground(Color.white);  
                setBackground(Color.lightGray);  
            }  
            else
            {  
                setForeground(header.getForeground());     
            }  
            setFont(header.getFont());  
            setText(((RowHeaderTableModel)table.getModel()).getValueAt(row, col).toString());  
            return this;  
        }  
        @Override
        public void valueChanged(ListSelectionEvent e)
        {  
            this.table.repaint();  
        }  
        private boolean isSelect(int row)
        {  
            int[] sel = ownerTable.getSelectedRows();  
            for ( int i=0; i<sel.length; i++ )  
                if (sel[i] == row )   
                    return true;  
            return false;  
        }  
    }  
 
    class RowHeaderTableModel extends AbstractTableModel
    {  
        private Object[] labels; 
        public RowHeaderTableModel(Object[] labels)
        {
            this.labels=labels;
        }  
 
        public void setRowHeader(Object[] labels)
        {
            this.labels=labels;
        }
        @Override
        public int getRowCount()
        {  
            return labels.length;  
        }  
        @Override
        public int getColumnCount()
        {  
            return 1;  
        }  
        @Override
        public Object getValueAt(int row, int column)
        {  
            return labels[row];  
        }  
    } 
}
