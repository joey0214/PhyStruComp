/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

/**
 *
 * @author zhongxf
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author wb
 */
class SequenceGrouping {
    
    private String groupName;

    private String description;
    
    private LinkedList sequences=new LinkedList();
    
    int width = -1;

    private DefaultColorScheme cs;

    private int startRes = 0;

    private int endRes = 0;

    private Color outlineColour = Color.black;

    private Color idColour = null;
    
    public SequenceGrouping()
    {
        groupName = "JGroup:" + this.hashCode();
    }
    
    public void addOrRemove(Sequence seq) 
    {
        if (sequences.contains(seq))
        {
            deleteSequence(seq);
        }
        else
        {
            addSequence(seq);
        }
    }

    public void addSequence(Sequence seq) 
    {
        if (seq != null && !sequences.contains(seq))
        {
            sequences.add(seq);
        }
    }
    
    public void deleteSequence(Sequence seq)
    {
        sequences.remove(seq);
    }

    public void deleteAll()
    {
        sequences.clear();
    }
  
    public LinkedList getSequences() 
    {
        return sequences;
    }

    public void setSequences(LinkedList sequences) 
    {
        this.sequences = sequences;
    }


    public String getGroupName() 
    {
        return groupName;
    }

    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

  
    public String getDescription() 
    {
        return description;
    }

 
    public void setDescription(String description) 
    {
        this.description = description;
    }

  
    public DefaultColorScheme getCs() 
    {
        return cs;
    }

 
    public void setCs(DefaultColorScheme cs) 
    {
        this.cs = cs;
    }

    public int getStartRes() 
    {
        return startRes;
    }


    public int getEndRes() 
    {
        return endRes;
    }

  
    public Color getOutlineColour() 
    {
        return outlineColour;
    }

    public void setOutlineColour(Color outlineColour) 
    {
        this.outlineColour = outlineColour;
    }

    public Color getIdColour() 
    {
        return idColour;
    }

 
    public void setIdColour(Color idColour) 
    {
        this.idColour = idColour;
    }

  
    public void setStartRes(int startRes) 
    {
        this.startRes = startRes;
    }

 
    public void setEndRes(int endRes) 
    {
        this.endRes = endRes;
    }
    
}

