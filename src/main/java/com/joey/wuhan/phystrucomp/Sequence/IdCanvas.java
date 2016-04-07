/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import com.joey.wuhan.phystrucomp.Sequence.AlignmentView;

/**
 *
 * @author zhongxf
 */
class IdCanvas extends JPanel
{
    
    private final AlignmentView av;
    private int imgHeight;
    BufferedImage image;
    Graphics2D gg;
    private Font idfont;
    private FontMetrics fm;


    public IdCanvas(AlignmentView av) {
        setLayout(new BorderLayout());
        this.av = av;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        int oldHeight = imgHeight;

        imgHeight = getHeight();
        imgHeight -= (imgHeight % av.charHeight);

        if (imgHeight < 1){
            return;
        }   

        if (oldHeight != imgHeight || image.getWidth(this) != getWidth()){
            image = new BufferedImage(getWidth(), imgHeight, BufferedImage.TYPE_INT_RGB);
        }

        gg = (Graphics2D) image.getGraphics();

        // Fill in the background
        gg.setColor(Color.white);
        gg.fillRect(0, 0, getWidth(), imgHeight);

        drawIds(av.startSeq, av.endSeq);

        g.drawImage(image, 5, 1, this);
    }

    private void drawIds(int starty, int endy) {
        
        idfont = av.getFont();

        gg.setFont(idfont);
        fm = gg.getFontMetrics();

        if (av.antiAlias){
            gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        Color currentColor = Color.white;
        Color currentTextColor = Color.black;

        // Now draw the id strings
        int xPos = 0;

        Sequence sequence;
        // Now draw the id strings
        for (int i = starty; i <= endy; i++){
            sequence = av.getAlignment().getSeqByIndex(i);

            if (sequence == null){
                continue;
            }
            
            if ((av.getSelectionGroup() != null)
                && av.getSelectionGroup().getSequences().contains(sequence)){
                currentColor = Color.lightGray;
                currentTextColor = Color.black;
            }
            else{
//                currentColor=Color.white;
                currentColor=av.getColorScheme().colorOf(sequence);
                currentTextColor=Color.black;
            }
//            else{
//                currentColor = av.getSequenceColour(sequence);
//                currentTextColor = Color.black;
//            }

            gg.setColor(currentColor);

            gg.fillRect(0, (i - starty) * av.charHeight, getWidth(),
                    av.charHeight);

            gg.setColor(currentTextColor);

            String string = sequence.getSeqName();

            gg.drawString(string, xPos,
                    (((i - starty) * av.charHeight) + av.charHeight)
                        - (av.charHeight / 5));


        }
    
    }
//    private final AlignmentView alignView ;
//    private int imgHeightI;
//    BufferedImage image;
//    Graphics2D gg2d;
//    private Font idFont;
//    private FontMetrics fontMetrics;
//    
//    public IdCanvas(AlignmentView alignview)
//    {
//        setLayout(new BorderLayout());
//        this.alignView = alignview;
//    }
//    
//    @Override
//    public void paintComponent(Graphics gg)
//    {
//        gg.setColor(Color.white);
//        gg.fillRect(0, 0, getWidth(), getHeight());
//        int oldHeight = imgHeightI;
//        
//        imgHeightI = getHeight();
//        imgHeightI -= (imgHeightI % alignView.charHeight);
//        
//        if (imgHeightI <1)
//        {
//            return;
//        }
//        
//        if (oldHeight != imgHeightI || image.getWidth(this) != getWidth())
//        {
//            image = new BufferedImage(getWidth(), imgHeightI, BufferedImage.TYPE_INT_RGB);
//            
//        }
//        
//        gg2d = (Graphics2D) image.getGraphics();
//        gg2d.setColor(Color.white);
//        gg2d.fillRect(0, 0, getWidth(), imgHeightI);
//        
//        drawIds(alignView.getFirstSeq(), (alignView.getLastSeq()));
//        System.out.println((alignView.getCharHeight()-1));
//        
//        gg.drawImage(image, 5, 1, this);
//    }
//
//    private void drawIds(int firstSeqI, int lastSeqI) 
//    {
////        System.out.println("first seq: " + firstSeqI + "   lastseq: " + lastSeqI);
//        idFont = alignView.getFont();
//        
//        gg2d.setFont(idFont);
//        fontMetrics = gg2d.getFontMetrics();
//        
//        if (alignView.antiAlias)
//        {
//            gg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//                    RenderingHints.VALUE_ANTIALIAS_ON);
//        }
//        
//        Color currentColor = Color.white;
//        Color currentTextColor = Color.black;
//        
//        int xPosition = 0;
//        
//        Sequence sequence;
//        
//        for (int i = firstSeqI; i <=lastSeqI; i++)
//        {
//            sequence = alignView.getAlignment().getSeqByIndex(i);
//            
//            if (sequence == null)
//            {
//                continue;
//            }
//            
//            if ((alignView.getSelectionGroup() != null)
//                && alignView.getSelectionGroup().getSequences().contains(sequence))
//            {
//                currentColor = Color.lightGray;
//                currentTextColor = Color.black;
//            }
//            else
//            {
//                currentColor=alignView.getColorScheme().colorOf(sequence);
//                currentTextColor=Color.black;
//            }
//            
//            gg2d.setColor(currentColor);
//            gg2d.fillRect(0, (i - firstSeqI)*alignView.charHeight, 
//                    getWidth(), alignView.charHeight);
//            gg2d.setColor(currentTextColor);
//            
//            String string = sequence.getSeqName();
////            System.out.println("seqeunce name:  " + string);
////            System.out.println("seqeunce name:  " + firstSeqI + " and " + lastSeqI);
//            
//            gg2d.drawString(string, xPosition, 
//                    (((i - firstSeqI)* alignView.charHeight) + alignView.charHeight)
//                    - (alignView.charHeight / 5));
//        }
//    }
    
}
