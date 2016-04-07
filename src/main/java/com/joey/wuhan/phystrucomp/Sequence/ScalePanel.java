/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author zhongxf
 */
class ScalePanel extends JPanel implements  MouseMotionListener,MouseListener
{
    private final AlignmentView alignView ;
    private final AlignmentPanel alignPanel ;

    ScalePanel(AlignmentView av, AlignmentPanel ap) 
    {
        this.alignView = av;
        this.alignPanel = ap;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void paintComponent(Graphics gg)
    {
        drawScale(gg, alignView.getStartRes(), alignView.getEndRes(),
                getWidth(), getHeight());
    }
    
    

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
         int x = (me.getX() / alignView.getCharWidth()
                 + alignView.getStartRes());
         final  int resI;
         
         if (x >= alignView.getAlignment().getWidth())
         {
             return;
         }
         
         resI = x;
         System.out.println(x);
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
        
    }

    @Override
    public void mouseExited(MouseEvent me) 
    {
                       
    }

    private void drawScale(Graphics gg, int startX, int endX, int width, int height) 
    {
        Graphics2D gg2d = (Graphics2D)gg;
        gg2d.setFont(alignView.getFont());
        
        if (alignView.antiAlias)
        {
            gg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        // Fill in the background
        gg2d.setColor(new Color(238,238,238));
        gg2d.fillRect(0, 0, width, height);
        gg2d.setColor(Color.black);
        
        // Draw the scale numbers
        gg2d.setColor(Color.black);
        
        FontMetrics fm = gg2d.getFontMetrics(alignView.getFont());
        int y = alignView.charHeight - fm.getDescent();
        
        String label;
        for (int i = startX; i <= endX; i++)
        {
            int v = i +1;
            
            if ((v % 10) == 0)
            {
                label = String.valueOf(v);
                
                gg2d.drawString(label, (i - startX + 0.5f-label.length()*1f/2)
                        *alignView.charWidth, y);
                gg2d.drawLine((int)(i - startX +0.5)*alignView.charWidth,
                        y, (int)(i -startX+0.5)*alignView.charWidth,
                        y +fm.getDescent()*2);
                
            }
            else 
            {
                gg2d.drawLine((int)(i - startX +0.5)*alignView.charWidth,
                        y +fm.getDescent(), (int)(i -startX+0.5)*alignView.charWidth,
                        y +fm.getDescent()*2);
            }
        }
        gg2d.drawLine(0,y +fm.getDescent()*2, (endX -startX +1)*alignView.charWidth,
                        y +fm.getDescent()*2);
    }
    
}
