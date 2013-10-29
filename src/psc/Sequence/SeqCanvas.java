/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        
    }
    
}
