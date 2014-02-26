/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

/**
 *
 * @author zhongxf
 */
public class AlignmentPanel extends  JSplitPane implements AdjustmentListener
{
    private Border border1;
    private JPanel idPanelHolder=new JPanel();
    private JPanel scalePanelHolder=new JPanel();
    private JPanel seqPanelHolder=new JPanel();
    private JPanel idTopSpace=new JPanel();
    private JTextField searchBar;
    private JScrollBar hscroll=new JScrollBar();
    private JScrollBar vscroll=new JScrollBar();
    
    
    protected AlignmentView alignView;
    SeqPanel seqPanel;
    private IdPanel idPanel;
    private ScalePanel scalePanel;
    
    int hextent = 0;
    int vextent = 0;
    
    public AlignmentPanel()
    {
        init();
    }
    
    public AlignmentPanel(AlignmentView alignview)
    {
        init();
        this.alignView = alignview;
        
        seqPanel = new SeqPanel(alignview,this);
        idPanel = new IdPanel(alignview, this);
        scalePanel = new ScalePanel(alignview, this);
        
        idPanelHolder.add(idPanel, BorderLayout.CENTER);
        scalePanelHolder.add(scalePanel, BorderLayout.CENTER);
        seqPanelHolder.add(seqPanel, BorderLayout.CENTER);
        
        scalePanelHolder.setPreferredSize(new Dimension(600, alignview.charHeight*2));
        idTopSpace.setPreferredSize(new Dimension(200, alignview.charHeight*2));
        
        setScrollValues(0,0);
        
        hscroll.addAdjustmentListener(this);
        vscroll.addAdjustmentListener(this);
    }

    private void init() 
    {
       layoutPanel();
    }

    private void layoutPanel() 
    {
        border1 = BorderFactory.createLineBorder(Color.gray,1);
        this.setPreferredSize(new Dimension(805, 330));
        
        idPanelHolder.setBorder(null);
        idPanelHolder.setPreferredSize(new Dimension(200, 300));
        idPanelHolder.setLayout(new BorderLayout());      
        idTopSpace.setPreferredSize(new Dimension(200,30));
        idTopSpace.setLayout(new BorderLayout());
        searchBar=new JTextField();
        idTopSpace.add(searchBar);
        idPanelHolder.add(idTopSpace, BorderLayout.NORTH);
        JScrollPane idsp=new JScrollPane();
        idsp.setViewportView(idPanelHolder);
        idsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        idsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        scalePanelHolder.setBackground(Color.white);
        scalePanelHolder.setPreferredSize(new Dimension(600, 30));
        scalePanelHolder.setLayout(new BorderLayout());
        
        seqPanelHolder.setLayout(new BorderLayout());
        seqPanelHolder.add(scalePanelHolder, BorderLayout.NORTH);
        seqPanelHolder.add(vscroll, BorderLayout.EAST);
        seqPanelHolder.add(hscroll,BorderLayout.SOUTH);
        hscroll.setOrientation(JScrollBar.HORIZONTAL);
        seqPanelHolder.setPreferredSize(new Dimension(600,200));

        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(idsp);
        this.setRightComponent(seqPanelHolder);
        this.setDividerSize(5);
        this.setDividerLocation(170);
        this.setContinuousLayout(true);
        
        
    }
    
    public boolean scrollUp(boolean  up)
    {
        if (up)
        {
            if (vscroll.getValue() < 1)
            {
                return false;
            }
            
            vscroll.setValue(vscroll.getValue() - 1);
            
        }
        else 
        {
            if (vextent + vscroll.getValue() >= alignView.getAlignment().getHeight())
            {
                return false;
            }
            vscroll.setValue(vscroll.getValue() +1);
        }
        return true;
    }
    
    public boolean scrollRight(boolean  right)
    {
        if (!right)
        {
            if (hscroll.getValue() <1)
            {
                return false;
            }
            hscroll.setValue(hscroll.getValue() -1);
        }
        else 
        {
            if ((hextent + hscroll.getValue()) >= alignView.getAlignment().getWidth())
            {
                return false;
            }
            hscroll.setValue(hscroll.getValue() +1);
        }
        return true;
    }
     @Override
    public void adjustmentValueChanged(AdjustmentEvent ae) 
    {
       int oldX = alignView.getFirstRes();
        int oldY = alignView.getFirstSeq();

        if (ae.getSource() == hscroll)
        {
            int x = hscroll.getValue();
            alignView.setFirstRes(x);
            alignView.setLastRes((x + (seqPanel.seqCanvas.getWidth() / alignView.getCharWidth())) - 1);
        }

        if (ae.getSource() == vscroll){
            int offy = vscroll.getValue();
            alignView.setFirstSeq(offy);
            alignView.setLastSeq(offy + (seqPanel.seqCanvas.getHeight() / alignView.getCharHeight()));
      
        }


        int scrollX = alignView.firstRes - oldX;
        int scrollY = alignView.firstSeq - oldY;

    
        // Make sure we're not trying to draw a panel
        // larger than the visible window
        if (scrollX > alignView.lastRes - alignView.firstRes){
            scrollX = alignView.lastRes - alignView.firstRes;
        }
        else if (scrollX < alignView.firstRes - alignView.lastRes){
            scrollX = alignView.firstRes - alignView.lastRes;
        }

        if (scrollX != 0 || scrollY != 0){
//            idPanel.idCanvas.fastPaint(scrollY);
//            seqPanel.seqCanvas.fastPaint(scrollX, scrollY);
//            scalePanel.repaint();
            repaint();

        }
    }

    private void setScrollValues(int x, int y) 
    {
       if (alignView == null || alignView.getAlignment() == null)
       {
           return;
       }
       
       int width = alignView.getAlignment().getWidth();
       int height = alignView.getAlignment().getHeight();
       
       alignView.setLastRes((x + (seqPanel.seqCanvas.getWidth() / 
               alignView.charWidth)) -1);
       alignView.setLastSeq((x + (seqPanel.seqCanvas.getHeight() / 
               alignView.getCharHeight())) );
       
       hextent = seqPanel.seqCanvas.getWidth() / alignView.charWidth;
       vextent = seqPanel.seqCanvas.getHeight() / alignView.charHeight;
       
       if (hextent > width)
       {
           hextent = height;
       }
       
       if (vextent > height)
       {
           vextent = height;
       }
       
       if ((hextent +x) >width)
       {
           x = width -hextent;
       }
       
       if ((vextent +y) >height)
       {
           y = height - vextent;
       }
       
       if (y<0)
       {
           y = 0;
       }
       
       if (x <0)
       {
           x = 0;
       }
       
       hscroll.setValues(x, hextent, 0, width);
       vscroll.setValues(y, vextent, 0, height);
    }

    @Override
    public void paintComponent(Graphics g){ 
        setScrollValues(alignView.getFirstRes(), alignView.getFirstSeq());
    
    }
    
}
