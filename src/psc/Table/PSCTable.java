/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author zhongxf
 */
public class PSCTable extends JTable 
{
    private PSCTableRowHeader rowHeader;

    public PSCTable(int rowCount, int columnCount, String[] headers) 
    {
        super();
        setModel(new PSCTableModel(rowCount, columnCount, headers));
        
    }

    public void setColumnHeader(String[] header) 
    {
        ((PSCTableModel) getModel()).setHeaders(header);
    }

    public void setRowHeader(String[] header) 
    {
        rowHeader = new PSCTableRowHeader(this, header, calRowWidth(header));
    }

    public PSCTableRowHeader getRowHeader() 
    {
        return this.rowHeader;
    }

    public void setData(Object[][] data) 
    {
        ((PSCTableModel) getModel()).setData(data);
    }

    @Override
    public void setValueAt(Object value, int row, int column) 
    {
        ((PSCTableModel) getModel()).setValueAt(value, row, column);
    }

    private int calRowWidth(String[] header) 
    {
        int length = 0;
//        int m = -1;
        int m = 0;
        for (int i = 0; i < header.length; i++) 
        {
            if (length < header[i].length()) 
            {
                length = header[i].length();
                m = i;
            }
        }
        return this.getFontMetrics(this.getFont()).stringWidth(header[m]) + 20;
    }

    class PSCTableModel extends AbstractTableModel 
    {
        private Object[][] data;
        private String[] headers;
        private int rowCount;
        private int columnCount;

        public PSCTableModel(int rowCount, int columnCount, String[] headers) 
        {
            this.rowCount = rowCount;
            this.columnCount = columnCount;
            this.headers = headers;
            this.data = new Object[rowCount][columnCount];
        }

        @Override
        public int getRowCount() 
        {
            return rowCount;
        }

        @Override
        public int getColumnCount() 
        {
            return columnCount;
        }

        @Override
        public Object getValueAt(int row, int column) 
        {
            return getData()[row][column];
        }

        @Override
        public String getColumnName(int column) 
        {
            return headers[column];
        }

        @Override
        public void setValueAt(Object value, int row, int column) 
        {
            data[row][column] = value;
        }

        /**
         * @return the data
         */
        public Object[][] getData() 
        {
            return data;
        }

        /**
         * @param data the data to set
         */
        public void setData(Object[][] data) 
        {
            this.data = data;
        }

        /**
         * @param rowCount the rowCount to set
         */
        public void setRowCount(int rowCount) 
        {
            this.rowCount = rowCount;
        }

        /**
         * @param columnCount the columnCount to set
         */
        public void setColumnCount(int columnCount) 
        {
            this.columnCount = columnCount;
        }

        /**
         * @param headers the headers to set
         */
        public void setHeaders(String[] headers)
        {
            this.headers = headers;
        }
    }
}
