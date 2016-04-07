/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author zhongxf
 */
public class SeqRender 
{
    AlignmentView av;

    FontMetrics fm;

    boolean renderGaps = true;

  

    Color resBoxColour;

    Graphics graphics;

    boolean monospacedFont;

    boolean forOverview = false;

    /**
    * Creates a new SequenceRenderer object.
    * 
    * @param av
    *          DOCUMENT ME!
    */
    public SeqRender(AlignmentView av){
        this.av = av;
    }

    /**
    * DOCUMENT ME!
    * 
    * @param b
    *          DOCUMENT ME!
    */
    public void prepare(Graphics g, boolean renderGaps){
        graphics = g;
        fm = g.getFontMetrics();

        // If EPS graphics, stringWidth will be a double, not an int
        double dwidth = fm.getStringBounds("M", g).getWidth();

        monospacedFont = (dwidth == fm.getStringBounds("|", g).getWidth() && (float) av.charWidth == dwidth);

        this.renderGaps = renderGaps;
    }


    /**
    * DOCUMENT ME!
    * 
    * @param cs
    *          DOCUMENT ME!
    * @param seq
    *          DOCUMENT ME!
    * @param i
    *          DOCUMENT ME!
    */
    void getBoxColour(ColorSchemeI cs, Sequence seq, int i){
        if(cs!=null){
            resBoxColour=cs.colorOf(seq.getSeqChar()[i], seq, i);
        }
        else{
            resBoxColour = Color.white;
        }
        
    
    }

   
    public void drawSequence(Sequence seq, int start, int end, int y1){

        drawBoxes(seq, start, end, y1);

        if (av.validCharWidth){
            drawText(seq, start, end, y1);
        }
    }

    public synchronized void drawBoxes(Sequence seq, int start, int end, int y1){
        if (seq == null) return; // fix for racecondition
        
        int i = start;
        int length = seq.getLength();

        int curStart = -1;
        int curWidth = av.charWidth;

        Color tempColour = null;

        while (i <= end){
            resBoxColour = Color.white;

      
            getBoxColour(av.getColorScheme(), seq, i);

            if (resBoxColour != tempColour){
                if (tempColour != null){
                    graphics.fillRect(av.charWidth * (curStart - start), y1, curWidth, av.charHeight);
                }

                graphics.setColor(resBoxColour);

                curStart = i;
                curWidth = av.charWidth;
                tempColour = resBoxColour;
            }
            else{
                curWidth += av.charWidth;
            }

            i++;
        }

        graphics.fillRect(av.charWidth * (curStart - start), y1, curWidth, av.charHeight);

    }


    public void drawText(Sequence seq, int start, int end, int y1){
        y1 += av.charHeight - av.charHeight / 5; // height/5 replaces pady
        int charOffset = 0;
        char s;

        if (end + 1 >= seq.getLength()){
            end = seq.getLength() - 1;
        }
        graphics.setColor(av.textColour);

   
        if (av.renderGaps){
            char[] ss=seq.getSeqChar();
            StringBuilder sb=new StringBuilder();
            for(int i=start;i<end+1;i++){
                sb.append(ss[i]);
            }
            graphics.drawString(sb.toString(), 0, y1);
        }
     
    
    }
//    AlignmentView alignView;
//    FontMetrics fontMetrics;
//    boolean renderGap = true;
//    Color resBoxColor;
//    Graphics graphics;
//    boolean monospaceFont;
//    boolean forOverview = false;
//    
//    public SeqRender(AlignmentView alignview)
//    {
//        this.alignView = alignview;
//    }
//    
//    public void prepare(Graphics g, boolean rendergaps)
//    {
//        graphics = g;
//        fontMetrics = g.getFontMetrics();
//        double dwidth  = fontMetrics.getStringBounds("M", g).getWidth();
//        monospaceFont = (dwidth == fontMetrics.getStringBounds("|", g).getWidth() 
//                && (float)alignView.charWidth == dwidth);
//        this.renderGap = rendergaps;
//    }
//    
//    void getBoxColor(ColorSchemeI csi, Sequence seq, int i)
//    {
//        if (csi != null)
//        {
//            resBoxColor = csi.colorOf(seq.getSeqChar()[i],seq,i);
//        }
//        else 
//        {
//            resBoxColor = Color.white;
//        }
//    }
//    
//    public void drawSequence(Sequence sequence, int start, int end, int y1)
//    {
//        drawBoxes(sequence, start, end, y1);
//        
//        if (alignView.validCharWidth)
//        {
//            drawText(sequence, start, end, y1);
//        }
//    }
//    
//    
//    
//    //多进程加锁？？ synchronized能够保证在同一时刻最多只有一个线程执行该段代码
//    public synchronized void drawBoxes(Sequence seq, int startI, int endI, int y1)
//    {
//        if (seq == null) return;
//        
//        int i = startI;
//        int length = seq.getLength();
//        int curStart = -1;
//        int curwidth = alignView.charWidth;
//        
//        Color tempColor = null;
//        
//        while (i <= endI)
//        {
//            resBoxColor = Color.white;
//            getBoxColor(alignView.getColorScheme(), seq, i);
//            
//            if (resBoxColor != tempColor)
//            {
//                if (tempColor != null)
//                {
//                    graphics.fillRect(alignView.charWidth * (curStart - startI), 
//                            y1, curwidth, alignView.charHeight);
//                }
//                
//                graphics.setColor(resBoxColor);
//                curStart = i;
//                curwidth = alignView.charWidth;
//                tempColor = resBoxColor;
//            }
//            else
//            {
//                curwidth += alignView.charWidth;
//            }
//            i ++;
//        }
//        graphics.fillRect(alignView.charWidth * (curStart -startI),
//                y1, curwidth, alignView.charHeight);
//        
//        
//    }
//    
//    public void drawText(Sequence seq , int startI, int endI, int y1)
//    {
//        y1 += alignView.charHeight -alignView.charHeight / 5;
//        int charOffset = 0;
//        char schar;
//        System.out.println("test02  "+endI);
//         System.out.println("test01  "+seq.getLength());
//        if (endI +1 >= seq.getLength())
//        {
//            endI = seq.getLength() -1;
//        }
//        graphics.setColor(alignView.textColor);
//        
//        if (alignView.renderGaps)
//        {
//            char[] seqchar = seq.getSeqChar();
//            StringBuilder stringbuilder = new StringBuilder();
//            for (int i=startI; i <endI +1; i++)
//            {
//                stringbuilder.append(seqchar[i]);
//            }
//            graphics.drawString(stringbuilder.toString(), 0, y1);
//        } 
//    } 
}
