/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import psc.Sequence.SeqCanvas;
import psc.Sequence.AlignmentPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import psc.Sequence.AlignmentView;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class SeqPanel extends JPanel implements  MouseListener, 
        MouseWheelListener, MouseMotionListener
{
    public SeqCanvas seqCanvas;
    public AlignmentPanel alignPanel ;
    protected int lastresI;
    protected int startseqI;
    protected AlignmentView alignView;

    SeqPanel(AlignmentView alignview, AlignmentPanel alignpanel) 
    {
        this.alignView = alignview;
        setBackground(Color.white);
        seqCanvas = new SeqCanvas(alignpanel);
        setLayout(new BorderLayout());
        add(seqCanvas,BorderLayout.CENTER);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        
        this.alignPanel = alignpanel;
    }
    
    public int findRes(MouseEvent mevent)
    {
        int res = 0;
        int x = mevent.getX(); 
        if (x > seqCanvas.getWidth() + seqCanvas.getWidth())
        {
            x = seqCanvas.getX() + alignView.getStartSeq();
        }
        res = (x / alignView.getCharWidth()) + alignView.getStartRes();
        return res ;
    }
    
    public int findSeq(MouseEvent mevent)
    {
        int seqI = 0;
        int y =mevent.getY();
        
        seqI = Math.min(y / alignView.getCharHeight() + alignView.getStartSeq(), 
                alignView.getAlignment().getHeight() - 1);
        //有矛盾，需要检查！！！
        //TODO
        seqI = y / alignView.getCharHeight() + alignView.getStartSeq();
        if (seqI >= alignView.getAlignment().getHeight())
        {
            return -1;
        }
        return seqI;
    }
    

    @Override
    public void mouseClicked(MouseEvent me) 
    {
        System.out.println("Mouse Clicked");
        int seqIndex = findSeq(me);
        if (seqIndex == -1) return;
        Sequence sequence = alignView.getAlignment().getSeqByIndex(findSeq(me));
        int resIndex = findRes(me);
        System.out.println(sequence.getSeqName()+","
                +alignView.getAlignment().getResAt(seqIndex, resIndex));
        
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent me) 
    {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) 
    {
        mwe.consume();
        
        if (mwe.getWheelRotation() > 0)
        {
            alignPanel.scrollUp(false);
        }
        else
        {
            alignPanel.scrollUp(true);
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
       
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {
       int seqIndex = findSeq(me);
        if (seqIndex == -1)
        {
            this.setToolTipText(null);
            return;
        }
        Sequence sequence = alignView.getAlignment().getSeqByIndex(findSeq(me));
        int resIndex = findRes(me);
        int positionI = alignView.getAlignment().getSitePosAt(seqIndex, resIndex);
        
        if (positionI == -1)
        {
            this.setToolTipText(null);
            return;
        }
        this.setToolTipText("<html><div bgccolor= \"#eeff99\">" + 
                (seqIndex + 1) + ": " + sequence.getSeqName()
                + " "+ (resIndex +1)+"("+positionI+")</div></html");
         
    }
    
}
