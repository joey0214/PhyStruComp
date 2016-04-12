/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
/**
 *
 * @author zhongxf
 */
public  class PSCseqPane  extends JSplitPane
{
    private JList titleList;
    private JList seqPane;
    private SeqLine[] ssl;
    
    public SeqLine[] PSCseqPane (ArrayList seqs)
    {
        
//        int n = seqs.size();
        int numberSeqs =seqs.size();
//        String[] stl = new String[numberSeqs];
        ssl = new SeqLine[numberSeqs];
        
        for (int i = 0; i<numberSeqs; i++)
        {
//            stl[i] = ((Seq)seqs.get(i)).getName();
            ssl[i] = new SeqLine(seqs.get(i).toString());
        }
        
        return ssl;       
    }
    class SeqSelectionListener implements ListSelectionListener 
    {
        @Override
        public void valueChanged(ListSelectionEvent lse) 
        {
            if (lse.getValueIsAdjusting() == false)
            {
                if (lse.getSource() == titleList)
                {
                    seqPane.setSelectedIndex(titleList.getSelectedIndex());
                }
                else if (lse.getSource() == seqPane)
                {
                    titleList.setSelectedIndex(seqPane.getSelectedIndex());
                }
            }            
        }
    }

    class SeqLine extends DefaultStyledDocument
    {
        private SimpleAttributeSet attrA,attrV,attrL,attrI,attrM; //darkGray
        private SimpleAttributeSet attrS,attrT,attrN,attrQ;  //GREEN
        private SimpleAttributeSet attrD,attrE;           //RED
        private SimpleAttributeSet attrH,attrR,attrK;   //BLUE
        private SimpleAttributeSet attrF,attrY,attrW; //magenta
        private SimpleAttributeSet attrP,attrG; //cyan
        private SimpleAttributeSet attrC;  //yellow
        private SimpleAttributeSet attr;   //black
         
        public SeqLine(String seq) 
        {
            setAttr();
            String[] seqs = seq.toUpperCase().split("");
            for (String b : seqs)
            { 
                addBase(b);
            }
        }
        
        public void setAttr()
        {
            attrA = new SimpleAttributeSet();
            StyleConstants.setForeground(attrA, Color.darkGray);
            attrV = new SimpleAttributeSet();
            StyleConstants.setForeground(attrV, Color.darkGray);
            attrL = new SimpleAttributeSet();
            StyleConstants.setForeground(attrL, Color.darkGray);
            attrI = new SimpleAttributeSet();
            StyleConstants.setForeground(attrI, Color.darkGray);
            attrM = new SimpleAttributeSet();
            StyleConstants.setForeground(attrM, Color.darkGray);
            
            attrS = new SimpleAttributeSet();
            StyleConstants.setForeground(attrS, Color.green);
            attrT = new SimpleAttributeSet();
            StyleConstants.setForeground(attrT, Color.green);
            attrN = new SimpleAttributeSet();
            StyleConstants.setForeground(attrN, Color.green);
            attrQ = new SimpleAttributeSet();
            StyleConstants.setForeground(attrQ, Color.green);
            
            attrD = new SimpleAttributeSet();
            StyleConstants.setForeground(attrD, Color.red);
            attrE = new SimpleAttributeSet();
            StyleConstants.setForeground(attrE, Color.red);
                       
            attrH = new SimpleAttributeSet();
            StyleConstants.setForeground(attrH, Color.blue);
            attrR = new SimpleAttributeSet();
            StyleConstants.setForeground(attrR, Color.blue);
            attrK = new SimpleAttributeSet();
            StyleConstants.setForeground(attrK, Color.blue);
                        
            attrF = new SimpleAttributeSet();
            StyleConstants.setForeground(attrF, Color.magenta);
            attrY = new SimpleAttributeSet();
            StyleConstants.setForeground(attrY, Color.magenta);
            attrW = new SimpleAttributeSet();
            StyleConstants.setForeground(attrW, Color.magenta);
            
            attrP = new SimpleAttributeSet();
            StyleConstants.setForeground(attrP, Color.cyan);
            attrG = new SimpleAttributeSet();
            StyleConstants.setForeground(attrG, Color.cyan);
            
            attrC = new SimpleAttributeSet();
            StyleConstants.setForeground(attrC, Color.yellow);
            
            attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, Color.black);
            SimpleAttributeSet attrCattr;
            
            for (SimpleAttributeSet a : new SimpleAttributeSet[]
            {attrA,attrV,attrL,attrI,attrM,attrS,attrT,attrN,attrQ, attrD,attrE,attrH,
            attrR,attrK,attrF,attrY,attrW,attrP,attrG, attr} )  
            {
                StyleConstants.setFontSize(a, 14);
                StyleConstants.setFontFamily(a, "Courier New");
            }           
        }

        private void addBase(String b) 
        {
                 if (b.equals("A")) { insert(b,attrA); }
            else if (b.equals("V")) { insert(b,attrV); }
            else if (b.equals("L")) { insert(b,attrL); }
            else if (b.equals("I")) { insert(b,attrI); }
            else if (b.equals("M")) { insert(b,attrM); }
            else if (b.equals("S")) { insert(b,attrS); }
            else if (b.equals("T")) { insert(b,attrT); }
            else if (b.equals("N")) { insert(b,attrN); }
            else if (b.equals("Q")) { insert(b,attrQ); }
            else if (b.equals("D")) { insert(b,attrD); }
            else if (b.equals("E")) { insert(b,attrE); }
            else if (b.equals("H")) { insert(b,attrH); }
            else if (b.equals("R")) { insert(b,attrR); }
            else if (b.equals("K")) { insert(b,attrK); }
            else if (b.equals("F")) { insert(b,attrF); }
            else if (b.equals("Y")) { insert(b,attrY); }
            else if (b.equals("W")) { insert(b,attrW); }
            else if (b.equals("P")) { insert(b,attrP); }
            else if (b.equals("G")) { insert(b,attrG); }
            else { insert(b,attr); }                      
        }

        private void insert(String str, AttributeSet attreset) 
        {
            try{
                this.insertString(this.getLength(), str, attreset);
            }
            catch(BadLocationException ble){
                System.out.println("BadLocationException:"+ble);
            }
        }
    }
}
