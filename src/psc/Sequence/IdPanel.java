/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import psc.Sequence.AlignmentView;

/**
 *
 * @author zhongxf
 */
class IdPanel extends  JPanel implements  MouseListener, 
        MouseMotionListener, MouseWheelListener
{
    private final AlignmentPanel alignPanel ;
    private final AlignmentView alignView;
    private final IdCanvas idCanvas;
    private boolean  mouseDragging;
    private int lastid = -1;

    IdPanel(AlignmentView av, AlignmentPanel ap) 
    {
        this.setBackground(Color.white);
        this.alignView = av;
        alignPanel = ap;
        
        idCanvas = new IdCanvas(alignView);
        setLayout(new BorderLayout());
        add(idCanvas, BorderLayout.CENTER);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }
    
    

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
       if (me.getClickCount() == 2) return;
       
       int seqI = alignPanel.seqPanel.findSeq(me);
       
       if ((alignView.getSelectionGroup() == null) ||
               (!me.isControlDown() && !me.isShiftDown()
               && alignView.getSelectionGroup() != null))
       {
           alignView.setSelectionGroup(new SequenceGrouping());
           alignView.getSelectionGroup().setStartRes(0);
           alignView.getSelectionGroup().setEndRes(
                   alignView.getAlignment().getWidth() - 1);
           
       }
       
       if (me.isShiftDown() && (lastid != -1))
       {
           selectSeqs(lastid, seqI);
       }
       else 
       {
           selectSeq(lastid);
       }
       
       alignPanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        mouseDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        mouseDragging = true;
        
        int seqI = alignPanel.seqPanel.findSeq(me);
        if (seqI <0) return;
        alignView.getSelectionGroup().deleteAll();
        selectSeqs(lastid, seqI);
        alignPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        SeqPanel seqp = alignPanel.seqPanel;
        int seqI = seqp.findSeq(me);
        
        if (seqI == -1)
        {
            this.setToolTipText(null);
            return;
        }
        
        Sequence sequence = alignView.getAlignment().getSeqByIndex(seqI);
        this.setToolTipText((seqI - 1)+": " +sequence.getSeqName()
                + " " + sequence.getSeqDescriotion());
        
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

    public void selectSeqs(int startI, int endI) 
    {
        if (alignView.getSelectionGroup() == null)
        {
            return;
        }
        
        if (endI >= alignView.getAlignment().getHeight())
        {
            endI = alignView.getAlignment().getHeight() -1;
        }
        
        if (endI < startI)
        {
             int tmpI = startI;
             startI = endI;
             endI = tmpI;
        }
        
        for (int i = startI; i <= endI; i++)
        {
            alignView.getSelectionGroup().addSequence(alignView.getAlignment().getSeqByIndex(i));
        }
    }

    public void selectSeq(int seqI) 
    {
        this.lastid = seqI;
        Sequence pickedSequence = alignView.getAlignment().getSeqByIndex(seqI);
        alignView.getSelectionGroup().addOrRemove(pickedSequence);
    }
    
}
