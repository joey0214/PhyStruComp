/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

/**
 *
 * @author zhongxf
 */
public class Sequence 
{
    private int seqId;
    private String seqName;
    private String seqDescription;
    private char[] seqChar;
    private boolean selected ;
    private int length ;
    
    public Sequence(String name, String seq)
    {
        this.seqName = name;
        this.seqChar = seq.toCharArray();
    }
    
    public Sequence(int id , String name , String seq)
    {
        this.seqId = id;
        this.seqName = name;
        this.seqChar = seq.toCharArray();
    }
    
    public char charAt(int index)
    {
        return seqChar[index];
    }
    
    public String getSeqName()
    {
        return seqName;
    }
    
    public void setSeqName(String newname)
    {
        this.seqName = newname;
    }
    
    public char[] getSeqChar()
    {
        return seqChar;
    }
    
    public void setSeqChar(char[] newSeqChar )
    {
        this.seqChar = newSeqChar;
    }
    
    public int getId()
    {
        return seqId;
    }
    
    public int getLength()
    {
        return seqChar.length;
    }
    
    public void setLength(int newLength)
    {
        this.length = newLength;
    }
    
    public String getSeqDescriotion()
    {
        return seqDescription;
    }
    
    public void setSeqDescription(String newDescription)
    {
        this.seqDescription = newDescription;
    }
    
    public void setSelected(boolean isSelected)
    {
        this.selected = isSelected;
    }
}
