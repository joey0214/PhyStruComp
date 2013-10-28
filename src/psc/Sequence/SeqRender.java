/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.eclipse.persistence.internal.oxm.NillableNodeValue;

/**
 *
 * @author zhongxf
 */
public class SeqRender 
{
    AlignmentView alignView;
    FontMetrics fontMetrics;
    boolean renderGap = true;
    Color resBoxColor;
    Graphics graphics;
    boolean monospaceFont;
    boolean forOverview = false;
    
    public SeqRender(AlignmentView alignview)
    {
        this.alignView = alignview;
    }
    
    public void prepare(Graphics g, boolean rendergaps)
    {
        graphics = g;
        fontMetrics = g.getFontMetrics();
        double dwidth  = fontMetrics.getStringBounds("M", g).getWidth();
        monospaceFont = (dwidth == fontMetrics.getStringBounds("|", g).getWidth() 
                && (float)alignView.charWidth == dwidth);
        this.renderGap = rendergaps;
    }
    
    void getBoxColor(ColorSchemeI csi, Sequence seq, int i)
    {
        if (csi != null)
        {
            resBoxColor = csi.colorOf(seq.getSeqChar()[i],seq,i);
        }
        else 
        {
            resBoxColor = Color.white;
        }
    }
    
    //多进程加锁？？ synchronized能够保证在同一时刻最多只有一个线程执行该段代码
    public void drawBoxes(Sequence seq, int startI, int endI, int y1)
    {
        if (seq == null) return;
        
        int i = startI;
        int length = seq.getLength();
        int curStart = -1;
        int curwidth = alignView.charWidth;
        
        Color tempColor = null;
        
        while (i <= endI)
        {
            resBoxColor = Color.white;
            getBoxColor(alignView.getColorScheme(), seq, i);
            
            if (resBoxColor != tempColor)
            {
                if (tempColor != null)
                {
                    graphics.fillRect(alignView.charWidth * (curStart - startI), 
                            y1, curwidth, alignView.charHeight);
                }
                
                graphics.setColor(resBoxColor);
                curStart = i;
                curwidth = alignView.charWidth;
                tempColor = resBoxColor;
            }
            else
            {
                curwidth += alignView.charWidth;
            }
            i ++;
        }
        graphics.fillRect(alignView.charWidth * (curStart -startI),
                y1, curwidth, alignView.charHeight);
        
        
    }
    
    public void drawText(Sequence seq , int startI, int endI, int y1)
    {
        y1 += alignView.charHeight -alignView.charHeight / 5;
        int charOffset = 0;
        char schar;
        
        if (endI +1 >= seq.getLength())
        {
            endI = seq.getLength() -1;
        }
        graphics.setColor(alignView.textColor);
        
        if (alignView.renderGaps)
        {
            char[] seqchar = seq.getSeqChar();
            StringBuilder stringbuilder = new StringBuilder();
            for (int i=startI; i <endI +1; i++)
            {
                stringbuilder.append(seqchar[i]);
            }
            graphics.drawString(stringbuilder.toString(), 0, y1);
        } 
    } 
}
