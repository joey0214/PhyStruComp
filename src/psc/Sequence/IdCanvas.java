/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import psc.Sequence.AlignmentView;

/**
 *
 * @author zhongxf
 */
class IdCanvas extends JPanel
{
    private final AlignmentView alignView ;
    private int imgHeightI;
    BufferedImage image;
    Graphics2D gg2d;
    private Font idFont;
    private FontMetrics fontMetrics;
    
    public IdCanvas(AlignmentView alignview)
    {
        setLayout(new BorderLayout());
        this.alignView = alignview;
    }
    
    @Override
    public void paintComponent(Graphics gg)
    {
        gg.setColor(Color.white);
        gg.fillRect(0, 0, getWidth(), getHeight());
        int oldHeight = imgHeightI;
        
        imgHeightI = getHeight();
        imgHeightI -= (imgHeightI % alignView.charHeight);
        
        if (imgHeightI <1)
        {
            return;
        }
        
        if (oldHeight != imgHeightI || image.getWidth(this) != getWidth())
        {
            image = new BufferedImage(getWidth(), imgHeightI, BufferedImage.TYPE_INT_RGB);
            
        }
        
        gg2d = (Graphics2D) image.getGraphics();
        gg2d.setColor(Color.white);
        gg2d.fillRect(0, 0, getWidth(), imgHeightI);
        
        drawIds(alignView.firstSeq, alignView.lastSeq);
        
        gg.drawImage(image, 5, 1, this);
    }

    private void drawIds(int firstSeqI, int lastSeqI) 
    {
        idFont = alignView.getFont();
        
        gg2d.setFont(idFont);
        fontMetrics = gg2d.getFontMetrics();
        
        if (alignView.antiAlias)
        {
            gg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        Color currentColor = Color.white;
        Color currentTextColor = Color.black;
        
        int xPosition = 0;
        
        Sequence sequence;
        
        for (int i = firstSeqI; i <=lastSeqI; i++)
        {
            sequence = alignView.getAlignment().getSeqByIndex(i);
            
            if (sequence == null)
            {
                continue;
            }
            
            if ((alignView.getSelectionGroup() != null)
                && alignView.getSelectionGroup().getSequences().contains(sequence))
            {
                currentColor = Color.lightGray;
                currentTextColor = Color.black;
            }
            else
            {
                currentColor=alignView.getColorScheme().colorOf(sequence);
                currentTextColor=Color.black;
            }
            
            gg2d.setColor(currentColor);
            gg2d.fillRect(0, (i - firstSeqI)*alignView.charHeight, 
                    getWidth(), alignView.charHeight);
            gg2d.setColor(currentTextColor);
            
            String string = sequence.getSeqName();
            
            gg2d.drawString(string, xPosition, 
                    (((i - firstSeqI)* alignView.charHeight) + alignView.charHeight)
                    - (alignView.charHeight / 5));
        }
    }
    
}
