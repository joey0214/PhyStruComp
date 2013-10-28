/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.ToolTipManager;

/**
 *
 * @author zhongxf
 */
public class AlignmentView 
{
    int firstRes ;
    int lastRes ;
    int firstSeq;
    int lastSeq ;
    int charHeight ;
    int charWidth;
    Color textColor = Color.black;
    boolean antiAlias=false;
    boolean renderGaps = true;
    private Alignment alignment ;
    Font font;
    boolean validCharWidth;
    private ColorSchemeI colorScheme;
    private ColumnSelection colSel = new ColumnSelection();
    
    
    
    public AlignmentView(Alignment ali)
    {
        this.alignment = ali;
        init();
    }

    private void init() 
    {
        this.firstRes =0;
        this.lastRes = getAlignment().getWidth();
        this.firstSeq =0;
        this.lastSeq = getAlignment().getHeight();
        
        antiAlias = false;
        
        String fontName = "Courier New";
        String fontStyle = Font.PLAIN + "";
        String fontSize = "12";
        int style = 0;
        if (fontStyle.equals("bold"))
        {
            style = 1;
        }
        else if (fontStyle.equals("italic"))
        {
            style = 2;
        }
        
        setFont(new Font(fontName,style, Integer.parseInt(fontSize)));
        setColorScheme(new PscColorScheme());
        ToolTipManager.sharedInstance().setInitialDelay(10);
    }
    
    public Alignment getAlignment()
    {
        return alignment;
    }
    
    public void setAlignment(Alignment alignment)
    {
        this.alignment = alignment;
    }
    
    public Font getFont()
    {
        return font;
    }
    
    public void setFont(Font font)
    {
        this.font = font;
        
        Container container = new Container();
        java.awt.FontMetrics fm = container.getFontMetrics(font);
        setCharHeight(fm.getHeight());
        setCharWidth(fm.charWidth('M'));
        validCharWidth =true;
    }
    
    public  int getCharHeight()
    {
        return charHeight;
    }
    
    public void setCharHeight(int newheight)
    {
        this.charHeight = newheight;
    }
    
    public int getWidth()
    {
        return charWidth;
    }
    
    public void setCharWidth(int newwidth)
    {
        this.charWidth = newwidth;
    }

    public void setColorScheme(ColorSchemeI colorScheme) 
    {
        this.colorScheme = colorScheme;
       
    }
    
    public ColorSchemeI getColorScheme()
    {
        return colorScheme;
    }
    
    public ColumnSelection getColumnSelection()
    {
        return colSel;
    }

    public void setColumnSelection(ColumnSelection colSelection)
    {
        this.colSel = colSelection;
    }
    
    public int getFirstRes()
    {
        return firstRes;
    }
    
    public void setFirstRes(int firstres)
    {
        this.firstRes = firstres;
    }
    
    public int getLastRes()
    {
        return lastRes;
    }
    
    public void setLastRes(int lastres)
    {
        if (lastres > (getAlignment().getWidth() -1))
        {
            lastres = getAlignment().getWidth() -1;
        }
        
        if (lastres < 0)
        {
            lastres = 0;
        }
        this.lastRes = lastres;
    }
    
    public int getFirstSeq()
    {
        return firstSeq;
    }
    
    public void setFirstSeq(int firstseq)
    {
        this.firstSeq = firstseq;
    }
    
    public int getLastSeq()
    {
        return lastSeq;
    }
    
    public void setLastSeq(int lastseq)
    {
        if (lastseq > (getAlignment().getHeight() - 1))
        {
            lastseq = getAlignment().getHeight() - 1;
        }
        if (lastseq < 0)
        {
            lastseq = 0;
        }
        this.lastSeq = lastseq;
    }
}
