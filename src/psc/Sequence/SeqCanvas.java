/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import psc.Sequence.AlignmentView;
import psc.Sequence.SeqRender;
import psc.Sequence.AlignmentPanel;

/**
 *
 * @author zhongxf
 */
public class SeqCanvas extends JComponent
{
    private final AlignmentView alignmView ;
    final SeqRender seqRender ;
    BufferedImage img;
    Graphics2D g2d ;
    int imgWidthI;
    int imgHeightI;
    
    boolean fastPainting = false;
    boolean fastPaint = false;

    SeqCanvas(AlignmentPanel alignpanel) 
    {
        this.alignmView = alignpanel.alignView;
        seqRender = new SeqRender(alignmView);
        setLayout(new BorderLayout());
        this.setSize(new Dimension(530, 200));
        setBackground(Color.white);
        
    }
    
    public void paintComponent(Graphics gg)
    {
        BufferedImage imgBuff = img;
        super.paintComponent(gg);
        
        imgWidthI = getWidth();
        imgHeightI = getHeight();
        
        imgHeightI -= (imgHeightI % alignmView.charHeight);
        imgWidthI -= (imgWidthI % alignmView.charWidth);
        
        if ((imgWidthI < 1) || (imgHeightI < 1))
        {
            return;
        }
        
        if (imgBuff == null || imgWidthI != imgBuff.getWidth()
            || imgHeightI != imgBuff.getHeight())
        {
            try
            {
                imgBuff = img = new BufferedImage(imgWidthI, imgHeightI,
                        BufferedImage.TYPE_INT_RGB);
                g2d = (Graphics2D)img.getGraphics();
                g2d.setFont(alignmView.getFont());
            }
            catch (OutOfMemoryError memoryError)
            {
                System.gc();
                System.err.println("SeqCanvas OutOfMemory Redraw Error.\n" + memoryError);
                
                return;
            }
            
        }
        
        if (alignmView.antiAlias)
        {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, imgWidthI,imgHeightI);
        
        int offset = 1;
        drawPanel(g2d, alignmView.firstRes,alignmView.lastRes, 
                alignmView.firstSeq, alignmView.lastSeq, offset);
        
        gg.drawImage(imgBuff, 0, 0, this);
        
    }
        
    void drawPanel(Graphics g1, int startRes, int endRes, int startSeq, int endSeq, int offset)
    {

        int screenY = 0;
        int blockStart = startRes;
        int blockEnd = endRes;

        

        if (screenY <= (endRes - startRes))
        {
            blockEnd = blockStart + (endRes - startRes) - screenY;
            g1.translate(screenY * alignmView.charWidth, 0);
            draw(g1, blockStart, blockEnd, startSeq, endSeq, offset);

            g1.translate(-screenY * alignmView.charWidth, 0);
        }
    

    }
    
    void draw(Graphics graphic, int startResI, int endResI, 
            int startSeq, int endSeq, int offset)
    {
        graphic.setFont(alignmView.getFont());
        seqRender.prepare(graphic, alignmView.renderGaps);
        
        Sequence nextseq ;
        
        for (int i = startSeq; i <= endSeq; i++)
        {
            nextseq = alignmView.getAlignment().getSeqByIndex(i);
            int j = offset + ((i-startSeq) * alignmView.charHeight);
            seqRender.drawSequence(nextseq, startResI, endResI, j);
        }
        
        if (alignmView.getSelectionGroup() != null)
        {
            drawGroupsBoundries(graphic, startResI,endResI,startSeq, endSeq, offset);
        }
    }

    void drawGroupsBoundries(Graphics graphic, int startResI, int endResI, 
            int startSeq, int endSeq, int offset) 
    {
        Graphics2D graphics2D = (Graphics2D)graphic;
        
        SequenceGrouping group = alignmView.getSelectionGroup();
        int sx = -1;
        int sy = -1;
        int ex = -1;
        int groupIndex = -1;
        int viswidth = (endResI -startResI +1) * alignmView.charWidth;
        
        if (group != null)
        {
            int oldy = -1;
            int i =0;
            boolean ingroup = false;
            int top= -1;
            int bottom = -1;
            
            for (i =startSeq; i <= endSeq; i++)
            {
                sx = (group.getStartRes() - startResI) * alignmView.charWidth;
                sy = offset + ((i - startSeq) * alignmView.charHeight);
                ex = (((group.getEndRes() + 1) -group.getStartRes()) * alignmView.charWidth);
                
                if (sx +ex < 0 || sx > viswidth )
                {
                    continue;
                }
                
                if ((sx <= (endResI - startResI) * alignmView.charWidth) 
                        && group.getSequences().contains(alignmView.getAlignment().getSeqByIndex(i)))
                {
                    if ((bottom == -1) 
                            && !group.getSequences().contains(alignmView.getAlignment().getSeqByIndex( + 1)))
                    {
                        bottom = sy + alignmView.charHeight;
                    }
                    
                    if (top != -1 && bottom != -1 
                            && group == alignmView.getSelectionGroup())
                    {
                        Paint op = graphics2D.getPaint();
                        graphics2D.setPaint(new Color(0, 0, 0, 50));
                        graphics2D.fillRect(0, top, viswidth, bottom-top);
                        graphics2D.setPaint(op);
                    }
                    
                    if (!ingroup)
                    {
                        if (((top == -1) && (i == 0))
                                &&  !group.getSequences().contains(alignmView.getAlignment().getSeqByIndex( i -1)))
                        {
                            top = sy;
                        }
                        
                        oldy = sy;
                        ingroup = true;
                        
                        if (group == alignmView.getSelectionGroup())
                        {
                            graphics2D.setColor(Color.yellow);
                            
                            if (top != -1 && bottom != -1)
                            {
                                Paint op = graphics2D.getPaint();
                                graphics2D.setPaint(new Color(0, 0, 0, 50));
                                graphics2D.fillRect(0, top, viswidth, bottom - top);
                                graphics2D.setPaint(op);
                            }
                        }
                        else 
                        {
                            graphics2D.setStroke(new BasicStroke());
                            graphics2D.setColor(group.getOutlineColour());
                        }
                    }
                }
                
                else 
                {
                    if (ingroup)
                    {
                        
                        if (sx >= 0 && sx < viswidth)
                        {
                            graphics2D.drawLine(sx, oldy, sx, sy);
                        }
                        
                        if (sx + ex < viswidth)
                        {
                            graphics2D.drawLine(sx +ex, oldy, sx + ex, sy);
                        }
                        
                        if (sx < 0)
                        {
                            ex += sx;
                            sx = 0;
                        }
                        
                        if (sx + ex > viswidth)
                        {
                            ex = viswidth;
                        }
                        
                        else if (sx + ex >= (endResI - startResI +1) * alignmView.charWidth)
                        {
                            ex = (endResI - startResI +1) * alignmView.charWidth;
                        }
                        
                        if (top != -1)
                        {
                            graphics2D.drawLine(sx, top, sx+ex, top);
                            top = -1;
                        }
                        
                        if (bottom != -1)
                        {
                            graphics2D.drawLine(sx, bottom, sx + ex, bottom);
                            bottom = -1;
                        }
                        
                        ingroup = false;
                    }
                }
            }
            
            if (ingroup)
            {
                sy = offset + ((i - startSeq) * alignmView.charHeight);
                if (sx >= 0 && sx < viswidth)
                {
                    graphics2D.drawLine(sx, oldy, sx, sy);
                }
                
                if (sx + ex < viswidth)
                {
                    graphics2D.drawLine(sx + ex, oldy, sx + ex, sy);
                }
                
                if (sx < 0)
                {
                    ex += sx;
                    sx =0;
                }
                
                if (sx + ex > viswidth)
                {
                    ex = viswidth;
                }
                
                else if (sx + ex >= (endResI - startResI +1) * alignmView.charWidth)
                {
                    ex = (endResI - startResI +1) * alignmView.charWidth;
                }
                
                if (top != -1)
                {
                    graphics2D.drawLine(sx, top, sx + ex, top);
                    top = -1;
                }
                
                if (bottom != -1)
                {
                    graphics2D.drawLine(sx, bottom - 1, sx + ex, bottom -1);
                    bottom = -1;
                }
                
                ingroup = false;
            }
            groupIndex ++;
            graphics2D.setStroke(new BasicStroke());
        }
    }
    
}
