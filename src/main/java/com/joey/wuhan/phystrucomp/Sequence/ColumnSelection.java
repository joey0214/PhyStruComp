/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.util.LinkedList;

/**
 *
 * @author zhongxf
 */
public class ColumnSelection 
{
    LinkedList isselected = new LinkedList();
    
    public void add(int col)
    {
        Integer column = new Integer(col);
        if (isselected.contains(column)) return ;
        isselected.add(col);
    }
    
    public void clear()
    {
        isselected.clear();
    }
    
    public int getSize()
    {
        return isselected.size();
    }
    
    public LinkedList getSelected()
    {
        return isselected;
    }
    
    public void remove(int col)
    {
        Integer column = new Integer(col);
        if (isselected.contains(column))
        {
            isselected.remove(column);
        }
    }
    
    public int columnAt(int i)
    {
        return (Integer)isselected.get(i);
    }
    
    //
    public int getMax()
    {
        int max =-1;
        
        for (int i =0; i< isselected.size();i++)
        {
            if (columnAt(i) > max)
            {
                max = columnAt(i);
            }
        }
        return max;
    }
    
    //
    public int getMin()
    {
        int min =1000000000;
        for (int i =0; i <isselected.size(); i++)
        {
            if (columnAt(i) < min)
            {
                min = columnAt(i);
            }
        }
        return min;
        
    }
    
    
}
