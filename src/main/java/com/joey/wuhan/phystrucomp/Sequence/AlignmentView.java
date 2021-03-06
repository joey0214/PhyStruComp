/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.ToolTipManager;

/**
 *
 * @author zhongxf
 */
public class AlignmentView 
{
    int startRes;
    int endRes;
    int startSeq;
    int endSeq;
    int charHeight;
    int charWidth;
    private Alignment alignment;
    Font font;
    Color textColour = Color.black;
    private ColorSchemeI cs;
    private ColumnSelection colSel = new ColumnSelection();
    SequenceGrouping selectionGroup;
    boolean validCharWidth;
    boolean antiAlias=false;
    boolean renderGaps = true;
    
    public AlignmentView(Alignment al){
        alignment=al;
        init();
    }
    
    private void init(){
        this.startRes = 0;
        this.endRes = getAlignment().getWidth() - 1;
        this.startSeq = 0;
        this.endSeq = getAlignment().getHeight() - 1;

        antiAlias = false; 
        
        String fontName = "Courier New";
        String fontStyle = Font.PLAIN + "";
        String fontSize = "12";
        int style = 0;

        if (fontStyle.equals("bold")){
            style = 1;
        }
        else if (fontStyle.equals("italic")){
            style = 2;
        }
        setFont(new Font(fontName, style, Integer.parseInt(fontSize)));
        setColorScheme(new PscColorScheme());
        ToolTipManager.sharedInstance().setInitialDelay(10);
    }
    

    public int getStartRes(){
        return startRes;
    }


    public int getEndRes(){
        return endRes;
    }


    public int getStartSeq(){
        return startSeq;
    }
    
    public void setStartRes(int res){
        this.startRes = res;
    }


    public void setStartSeq(int seq){
        this.startSeq = seq;
    }


    public void setEndRes(int res){
        if (res > (getAlignment().getWidth() - 1)){
      // log.System.out.println(" Corrected res from " + res + " to maximum " +
      // (alignment.getWidth()-1));
            res = getAlignment().getWidth() - 1;
        }

        if (res < 0){
            res = 0;
        }

        this.endRes = res;
    }


    public void setEndSeq(int seq){
        if (seq > getAlignment().getHeight()-1){
            seq = getAlignment().getHeight()-1;
        }

        if (seq < 0){
            seq = 0;
        }

        this.endSeq = seq;
    }


    public int getEndSeq(){
        return endSeq;
    }
    
    public void setCharWidth(int w){
        this.charWidth = w;
    }

    public int getCharWidth(){
        return charWidth;
    }

    public void setCharHeight(int h){
        this.charHeight = h;
    }

    public int getCharHeight(){
        return charHeight;
    }

    public Font getFont() {
        return font;
    }
    
    public void setFont(Font f){
        font = f;

        Container c = new Container();

        java.awt.FontMetrics fm = c.getFontMetrics(font);
        setCharHeight(fm.getHeight());
        setCharWidth(fm.charWidth('M'));
        validCharWidth = true;
    }

    /**
     * @return the colSel
     */
    public ColumnSelection getColumnSelection() {
        return colSel;
    }

    /**
     * @param colSel the colSel to set
     */
    public void setColumnSelection(ColumnSelection colSel) {
        this.colSel = colSel;
    }

    /**
    * 
    * 
    * @return null or the currently selected sequence region
    */
    public SequenceGrouping getSelectionGroup(){
        return selectionGroup;
    }

    /**
    * Set the selection group for this window.
    * 
    * @param sg - group holding references to sequences in this alignment view
    *          
    */
    public void setSelectionGroup(SequenceGrouping sg){
        selectionGroup = sg;
    }

    /**
     * @return the cs
     */
    public ColorSchemeI getColorScheme() {
        return cs;
    }

    /**
     * @param cs the cs to set
     */
    public void setColorScheme(ColorSchemeI cs) {
        this.cs = cs;
    }

    /**
     * @return the alignment
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }
//    int firstRes ;
//    int lastRes ;
//    int firstSeq;
//    int lastSeq ;
//    int charHeight ;
//    int charWidth;
//    Color textColor = Color.black;
//    boolean antiAlias=false;
//    boolean renderGaps = true;
//    private Alignment alignment ;
//    Font font;
//    SequenceGrouping selectionGroup;
//    boolean validCharWidth;
//    private ColorSchemeI colorScheme;
//    private ColumnSelection colSel = new ColumnSelection();
//    
//    
//    
//    public AlignmentView(Alignment ali)
//    {
//        this.alignment = ali;
//        init();
//    }
//
//    private void init() 
//    {
//        this.firstRes =0;
//        this.lastRes = getAlignment().getWidth()-1;
//        this.firstSeq =0;
//        this.lastSeq = getAlignment().getSeqCount() -1;
//        System.out.println("getSeqCount======="+lastRes);
//        antiAlias = false;
//        
//        String fontName = "Courier New";
//        String fontStyle = Font.PLAIN + "";
//        String fontSize = "12";
//        int style = 0;
//        if (fontStyle.equals("bold"))
//        {
//            style = 1;
//        }
//        else if (fontStyle.equals("italic"))
//        {
//            style = 2;
//        }
//        
//        setFont(new Font(fontName,style, Integer.parseInt(fontSize)));
//        setColorScheme(new PscColorScheme());
//        ToolTipManager.sharedInstance().setInitialDelay(10);
//    }
//    
//    public Alignment getAlignment()
//    {
//        return alignment;
//    }
//    
//    public void setAlignment(Alignment alignment)
//    {
//        this.alignment = alignment;
//    }
//    
//    public Font getFont()
//    {
//        return font;
//    }
//    
//    public void setFont(Font font)
//    {
//        this.font = font;
//        
//        Container container = new Container();
//        java.awt.FontMetrics fm = container.getFontMetrics(font);
//        setCharHeight(fm.getHeight());
//        setCharWidth(fm.charWidth('M'));
//        validCharWidth =true;
//    }
//    
//    public  int getCharHeight()
//    {
//        return charHeight;
//    }
//    
//    public void setCharHeight(int newheight)
//    {
//        this.charHeight = newheight;
//    }
//    
//    public int getCharWidth()
//    {
//        return charWidth;
//    }
//    
//    public void setCharWidth(int newwidth)
//    {
//        this.charWidth = newwidth;
//    }
//
//    public void setColorScheme(ColorSchemeI colorScheme) 
//    {
//        this.colorScheme = colorScheme;
//       
//    }
//    
//    public ColorSchemeI getColorScheme()
//    {
//        return colorScheme;
//    }
//    
//    public ColumnSelection getColumnSelection()
//    {
//        return colSel;
//    }
//
//    public void setColumnSelection(ColumnSelection colSelection)
//    {
//        this.colSel = colSelection;
//    }
//    
//    public int getFirstRes()
//    {
//        return firstRes;
//    }
//    
//    public void setFirstRes(int firstres)
//    {
//        this.firstRes = firstres;
//    }
//    
//    public int getLastRes()
//    {
//        return lastRes;
//    }
//    
//    public void setLastRes(int lastres)
//    {
//        if (lastres > (getAlignment().getWidth() -1))
//        {
//            lastres = getAlignment().getWidth() -1;
//        }
//        
//        if (lastres < 0)
//        {
//            lastres = 0;
//        }
//        this.lastRes = lastres;
//    }
//    
//    public int getFirstSeq()
//    {
//        return firstSeq;
//    }
//    
//    public void setFirstSeq(int firstseq)
//    {
//        this.firstSeq = firstseq;
//    }
//    
//    public int getLastSeq()
//    {
//        return lastSeq;
//    }
//    
//    public void setLastSeq(int lastseq)
//    {
//        if (lastseq > (getAlignment().getHeight() - 1))
//        {
//            lastseq = getAlignment().getHeight() - 1;
//        }
//        if (lastseq < 0)
//        {
//            lastseq = 0;
//        }
//        this.lastSeq = lastseq;
//    }
//    
//    public SequenceGrouping getSelectionGroup()
//    {
//        return selectionGroup;
//    }
//
// 
//    public void setSelectionGroup(SequenceGrouping sg)
//    {
//        selectionGroup = sg;
//    }
}
