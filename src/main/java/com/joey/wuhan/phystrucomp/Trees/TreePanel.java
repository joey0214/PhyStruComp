/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Trees;

/**
 *
 * @author zhongxf
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.bigiov.wbtoolkit.phylo.RectangularTree;
import com.bigiov.wbtoolkit.phylo.Tree;
import com.bigiov.wbtoolkit.phylo.TreeAnnotation;
import com.bigiov.wbtoolkit.phylo.TreeNode2D;
import com.bigiov.wbtoolkit.phylo.TreeStyle;

/**
 *
 * @author wb
 * @edited by joey
 */
public class TreePanel extends JPanel
{
    public static final int RECTANGULAR=0;
    private TreeStyle ts;
    private int style;
    private Tree tree;
    private List<TreeAnnotation> annotations;
    private boolean showLabel=true;
    private boolean showBranchLength=false;
    private boolean showProb=true;
    private double zoom=1;
    private int x1,x2,y1,y2,dragStatus;
    private int rectStatus =0;
    private int[] selectedArea= new int[4];
    private TreeNode2D[] node2ds;
    private ArrayList definedCladesList = new ArrayList();
    public TreePanel(Tree tree)
    {
        this.tree=tree;  
        annotations=new LinkedList();
        this.setPreferredSize(new Dimension(400,400));
        this.addMouseListener(treePanelEvent());
        this.addMouseMotionListener(mouseDraged()); //@ add by joey
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        String[] labels = tree.getLabels();
//        List list = tree.getLeafNodes();
        
//        for (int i =0; i < labels.length; i ++)
//        {
//            System.out.println("labels[i]"+ labels[i]);
//        }
//        for (int k =0; k < list.size(); k ++)
//        {
//            System.out.println("list.get(k)" + list.get(k));
//        }
        
        
    }

    private MouseAdapter treePanelEvent()
    {
        return new MouseAdapter()
        {
            private Object g;
           @Override
            public void mouseClicked(MouseEvent me) 
           {
                if (me.getClickCount() == 2) 
                {
                   zoom += 0.1;
                   setSize((int) (getWidth() * zoom), (int) (getHeight() * zoom));
                   setPreferredSize(new Dimension((int) (getWidth()), (int) (getHeight())));
               }
            } 
           
           //@ add by joey
           public void mousePressed(MouseEvent arg0)
           {
               repaint();
               x1 = arg0.getX();
               y1 = arg0.getY();  
               System.out.println("x1 + y1  " + x1+"---"+ y1);
           }
           //@add by joey
           public void mouseReleased(MouseEvent arg0) 
            {
//                 repaint();
                if (dragStatus == 1)
                {
                    x2 = arg0.getX();
                    y2 = arg0.getY();
                    try 
                    {
                        paintRect(getGraphics());
                        // rectStatus:0 -> not paint rect; 1 -> have been paint rect.
                        rectStatus =1;
                        selectedArea = new int[]{x1,y1,x2,y2};
                    }
                    catch (Exception e) 
                    {
                       e.printStackTrace();
                   }
                }
            }
           
           public void paintRect(Graphics graphi)
             {
                int w = x1 - x2;
                int h = y1 - y2;
                if (w<0)
                {
                    w = w *-1;
                }
                 if (h<0)
                {
                    h = h*-1;
                }
                graphi.drawRect(x1, y1, w, h);
                selectionEvent();
            }

            
        };
    }
    //@add by joey
    private void selectionEvent() 
    { 
        int xMax,xMin,yMax,yMin;
        if (x1 > x2) 
        {
            xMax = x1;
            xMin = x2;
        }
        else 
        {
            xMax = x2;
            xMin = x1;
        }
        
        if (y1 > y2) 
        {
            yMax = y1;
            yMin = y2;
        }
        else 
        {
            yMax = y2;
            yMin = y1;
        }
        
//        tree.getLeafNodes();
////        node2ds=new TreeNode2D[tree.getNodeNum()];
////        int p=tree.getRootNode().getParentNode();
//        System.out.println(node2ds);
//        for (int i =0; i < tree.getLeafNodes().size(); i++)
//        {
//            System.out.println(tree.getLeafNodes().get(i).getName() +"---"+node2ds[i].getPos().x +"---"+ node2ds[i].getPos().y);
//        }
        String[] labels = tree.getLabels();
        ArrayList selsecedLabels = new ArrayList();
        tree.getLabelsPositions();
        
        for (int k =0; k < labels.length; k ++)
        {
            int[] posit = (int[])(tree.getLabelsPositions()).get(labels[k]); 
            if (posit[0]>= xMin && posit[0]<= xMax)
            {
                if (posit[1] >= yMin && posit[1] <= yMax)
                {
//                    System.out.println(labels[k]);
                    selsecedLabels.add(labels[k]);
                    
                }
            }
        }
        
        userConfirm(selsecedLabels);
       
    }


    //@add by joey
    private MouseAdapter mouseDraged() 
    {
        return new MouseAdapter() 
        {
            public void mouseDragged(MouseEvent arg0) 
            {
                repaint();
                dragStatus = 1;
                x2 = arg0.getX();
                y2 = arg0.getY();
                 try 
                    {
//                        repaint();
                       paintRect(getGraphics());
                    }
                    catch (Exception e) 
                    {
                       e.printStackTrace();
                   }
//                 System.out.println("mouseDragged at " + x2 +"---"+ y2);
            }

             public void paintRect(Graphics graphi)
             {
                int w = x1 - x2;
                int h = y1 - y2;
                if (w<0)
                {
                    w = w *-1;
                }
                 if (h<0)
                {
                    h = h*-1;
                }

//                System.out.println("graphi.drawRect(x1, y1, w, h) ---"+ x1+" - "+ y1+" - "+ w+" - "+ h);
                graphi.drawRect(x1, y1, w, h);
            }
        };
    }

    //@ add by joey
    public void zoomIN()
    {
        zoom += 0.1;
        setSize((int) (getWidth()* zoom), (int) (getHeight() * zoom));
        setPreferredSize(new Dimension((int) (getWidth()), (int) (getHeight())));
        System.out.println("hello zoomin");
    }
    //@ add by joey
     public void zoomOUT()
    {
        if (zoom > 1)
        {
            zoom =((zoom - 0.1)/zoom);
        setSize((int) (getWidth()* 0.9), (int) (getHeight() * 0.9));
        setPreferredSize(new Dimension((int) (getWidth()), (int) (getHeight())));
        }
        
    }
      
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(Color.white);
        int width = getWidth();
        int height = getHeight();
        
        draw(g2d, width, height, style);
    }
    
    public void draw(Graphics2D g2, int width, int height, int style){       
        switch(style){
            case 0: {
                ts=new RectangularTree(tree, width, height, g2);
                break;
            }
            default:
                return;
        }
        ts.setShowBranchLength(showBranchLength);
        ts.setShowLabel(showLabel);
        ts.setShowProb(showProb);
        ts.draw();
        
        if(getAnnotations().size()>0){
            for(TreeAnnotation a:annotations){
                a.setTree(ts);
                a.drawAnnotation();
            }
        }
    }
    
    
    public void addAnnotation(TreeAnnotation na){
        getAnnotations().add(na);
    }
    
    public void removeAnnotation(TreeAnnotation na){
        getAnnotations().remove(na);
    }

    /**
     * @return the ts
     */
    public TreeStyle getTs() {
        return ts;
    }

    /**
     * @param ts the ts to set
     */
    public void setTs(TreeStyle ts) {
        this.ts = ts;
    }

    /**
     * @return the style
     */
    public int getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * @return the tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(Tree tree) {
        this.tree = tree;
    }

    /**
     * @return the showLabel
     */
    public boolean isShowLabel() {
        return showLabel;
    }

    /**
     * @param showLabel the showLabel to set
     */
    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    /**
     * @return the showBranchLength
     */
    public boolean isShowBranchLength() {
        return showBranchLength;
    }

    /**
     * @param showBranchLength the showBranchLength to set
     */
    public void setShowBranchLength(boolean showBranchLength) {
        this.showBranchLength = showBranchLength;
    }

    /**
     * @return the showProb
     */
    public boolean isShowProb() {
        return showProb;
    }

    /**
     * @param showProb the showProb to set
     */
    public void setShowProb(boolean showProb) {
        this.showProb = showProb;
    }

    /**
     * @return the annotations
     */
    public List<TreeAnnotation> getAnnotations() {
        return annotations;
    }

    /**
     * @param annotations the annotations to set
     */
    public void setAnnotations(List<TreeAnnotation> annotations) {
        this.annotations = annotations;
    }

    // @add by joey
    public int getRectStatus()
    {
        return rectStatus;
    }
    // @add by joey
    public int[] getSelectArea()
    {
        return selectedArea;
    }
    //@add by joey
    public void eraseRect()
    {
        repaint();
    }
    
    public String[] getLabels()
    {
        return tree.getLabels();
    }

    private void userConfirm(ArrayList selsecedLabels) 
    {
         String selectlabels = "";
         for (int i =0; i < selsecedLabels.size(); i ++)
         {
             selectlabels += selsecedLabels.get(i) + ",";
         }
       
        System.out.println("selectlabels" + selectlabels);
        if (selectlabels != "") 
        {
            JFrame userConfirmFrame = new JFrame("User Confirm");
            userConfirmFrame.setSize(300, 300);
            
            JPanel jPanel = new JPanel(new BorderLayout());
            JLabel namelabel = new JLabel("clade name");
            JLabel messageLabel = new JLabel("You are doing to define those sequences");
            JTextArea textArea = new JTextArea(selectlabels);
            textArea.setRows(10);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            
            Object[] options = {"Confirmed!", "Cancel?"};
            int n = JOptionPane.showOptionDialog(userConfirmFrame,
                    "You are doing to define those sequences ( " + selectlabels
                    + " )as clade ",
                    "User Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == JOptionPane.YES_OPTION) 
            {
                definedCladesList.add(selsecedLabels);
            } 
            else if (n == JOptionPane.NO_OPTION) 
            {
                System.out.println("hello,canceled");
            }
        }
    
    }
    
    public ArrayList getDefinedClades()
    {
        return definedCladesList;
    }
}

