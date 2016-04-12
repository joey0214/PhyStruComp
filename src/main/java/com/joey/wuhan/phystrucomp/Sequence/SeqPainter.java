/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import org.biojava.bio.BioRuntimeException;
import org.biojava.bio.alignment.SimpleAlignment;
import org.biojava.bio.gui.sequence.AlignmentRenderer;
import org.biojava.bio.gui.sequence.GUITools;
import org.biojava.bio.gui.sequence.LabelledSequenceRenderer;
import org.biojava.bio.gui.sequence.MultiLineRenderer;
import org.biojava.bio.gui.sequence.RulerRenderer;
import org.biojava.bio.gui.sequence.SequenceRenderContext;
import org.biojava.bio.gui.sequence.SymbolSequenceRenderer;
import org.biojava.bio.gui.sequence.TranslatedSequencePanel;
import org.biojava.bio.seq.ProteinTools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.io.SymbolTokenization;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.bio.symbol.Location;
import org.biojava.bio.symbol.SymbolList;
import org.biojava3.core.sequence.ProteinSequence;

/**
 *
 * @author zhongxf
 */
public class SeqPainter 
{
    private ProteinSequence[] proseqs;
    private Sequence[] nrseqs;
    private Sequence[] secstruSeqs;
    private TranslatedSequencePanel proTsp = new TranslatedSequencePanel();
    private TranslatedSequencePanel nrTsp = new TranslatedSequencePanel();
    private TranslatedSequencePanel secTsp = new TranslatedSequencePanel();
    
    private JPanel  proPanel;
    //LabelledSequenceRenderer for each AlignmentRenderer
    LabelledSequenceRenderer labRen;
    //AlignmentRenderer to hold each sequence
    AlignmentRenderer render;
    //MultiLineRenderer to allow display of multiple tracks in the TranslatedSequencePanel
    MultiLineRenderer multi = new MultiLineRenderer();
    //SymbolSequenceRenderer to handle display of the sequence symbols - only one instance is needed
    SymbolSequenceRenderer symbol = new SymbolSequenceRenderer();
    //RulerRenderer to display sequence coordinates
    RulerRenderer ruler = new RulerRenderer();
    //The width in pixels of the of the label in the LabelledSequenceRenderer 
    int labelWidth = 50;
    //The height in pixels of the of the label in the LabelledSequenceRenderer 
    int labelHeight = 20;
    JScrollBar scrollBar,hscrollBar,vscrollBar;
    
    
    public void setProteinSeq(ProteinSequence[] proteinSeq) throws IllegalSymbolException
    {
        this.proseqs = proteinSeq;
        proseqPainter();
    } 
    
    public void setSequence(Sequence[] nrseqeSequences)
    {
        this.nrseqs = nrseqeSequences;
        nrseqPainter();
    }
    
    public void setSecStruSeq(Sequence[] secstruseq)
    {
        this.secstruSeqs = secstruseq;
        secseqPainter();
    }
    
    public TranslatedSequencePanel getproTsp()
    {
        System.out.println("hello");
        return proTsp;
    }
    
    public TranslatedSequencePanel getnrTsp()
    {
        return nrTsp;
    }
    
    public TranslatedSequencePanel getsecTsp()
    {
        return secTsp;
    }

    private TranslatedSequencePanel proseqPainter() throws IllegalSymbolException 
    {
        if (proseqs == null ||(proseqs != null && proseqs.length ==0)) 
        { 
            System.out.println("Sorry.Did not detect protein sequence.");
            return null;
        }
        else 
        {
            SymbolSequenceRenderer symbol = new SymbolSequenceRenderer() 
            {
                public void paint(Graphics2D g2, SequenceRenderContext context) 
                {
                    Rectangle2D prevClip = g2.getClipBounds();
                    AffineTransform prevTransform = g2.getTransform();
                    Paint outline = null;
                    double depth = 20;
                    g2.setPaint(outline);
                    Font font = context.getFont();
                    Rectangle2D maxCharBounds = font.getMaxCharBounds(g2.getFontRenderContext());
                    double scale = context.getScale();
                    if (scale >= (maxCharBounds.getWidth() * 0.3) && scale >= (maxCharBounds.getHeight() * 0.3)) 
                    {
                        double xFontOffset = 0.0;
                        double yFontOffset = 0.0;
                        xFontOffset = maxCharBounds.getCenterX() * 0.25;
                        yFontOffset = -maxCharBounds.getCenterY() + (depth * 0.5);
                        SymbolList seq = context.getSymbols();
                        SymbolTokenization toke = null;
                        try 
                        {
                            toke = seq.getAlphabet().getTokenization("token");
                        } 
                        catch (Exception ex) 
                        {
//                  throw new BioRuntimeException(ex);
                        }
                        Location visible = GUITools.getVisibleRange(context, g2);
                        for (int sPos = visible.getMin(); sPos <= visible.getMax(); sPos++) 
                        {
                            double gPos = context.sequenceToGraphics(sPos);
                            String s = "?";
                            try 
                            {
                                s = toke.tokenizeSymbol(seq.symbolAt(sPos));
                            } 
                            catch (Exception ex) 
                            {
                                // We'll ignore the case of not being able to tokenize it
                            }

                            //Start of the modifications -------------------------------
                            //Make sure the text is uppercase
                            s = s.toUpperCase();
                            //System.out.println(s);

                            //Set the color according to the nucleotide for the background
                            if (s.equals("A")) {
                                g2.setColor(Color.darkGray);
                            } else if (s.equals("V")) {
                                g2.setColor(Color.darkGray);
                            } else if (s.equals("L")) {
                                g2.setColor(Color.darkGray);
                            } else if (s.equals("I")) {
                                g2.setColor(Color.darkGray);
                            } else if (s.equals("M")) {
                                g2.setColor(Color.darkGray);
                            } else if (s.equals("S")) {
                                g2.setColor(Color.green);
                            } else if (s.equals("T")) {
                                g2.setColor(Color.green);
                            } else if (s.equals("N")) {
                                g2.setColor(Color.green);
                            } else if (s.equals("Q")) {
                                g2.setColor(Color.green);
                            } else if (s.equals("D")) {
                                g2.setColor(Color.red);
                            } else if (s.equals("E")) {
                                g2.setColor(Color.red);
                            } else if (s.equals("H")) {
                                g2.setColor(Color.blue);
                            } else if (s.equals("R")) {
                                g2.setColor(Color.blue);
                            } else if (s.equals("K")) {
                                g2.setColor(Color.blue);
                            } else if (s.equals("F")) {
                                g2.setColor(Color.magenta);
                            } else if (s.equals("Y")) {
                                g2.setColor(Color.magenta);
                            } else if (s.equals("W")) {
                                g2.setColor(Color.magenta);
                            } else if (s.equals("P")) {
                                g2.setColor(Color.cyan);
                            } else if (s.equals("G")) {
                                g2.setColor(Color.cyan);
                            } else {
                                g2.setColor(Color.black);
                            }

                            g2.fill(new Rectangle2D.Double((gPos + xFontOffset) - 1.5, 0, proTsp.getScale(), labelHeight));

//            g2.fill(new Rectangle2D.Double((gPos + xFontOffset)-1.5, 0, tsp.getScale(), labelHeight ));
                            //Set the colour for the text
                            g2.setColor(new Color(83, 83, 83));
                            //End of the modifications ---------------------------------
                            g2.drawString(s, (float) (gPos + xFontOffset), (float) yFontOffset);
                        }
                    }
                    g2.setTransform(prevTransform);
                }
            };

            Map<String, SymbolList> list = new HashMap();
            for (int l = 0; l < proseqs.length; l++) 
            {
                list.put(proseqs[l].getAccession().toString(), ProteinTools.createProtein(proseqs[l].toString()));
            }

            SimpleAlignment ali = new SimpleAlignment((Map) list);

            //System.out.println(ali.getLabels().size());
            //Add the alignment renderers to the MultiLineRenderer
            for (int ilen = 0; ilen < proseqs.length; ilen++) 
            {
                //Instantiate the AlignmentRenderer
                render = new AlignmentRenderer();
                //Set the label for the AlignmentRenderer
                render.setLabel(ali.getLabels().get(ilen));
                //Set the renderer for the AlignmentRenderer
                render.setRenderer(symbol);

                //Instantiate the LabelledSequenceRenderer
                labRen = new LabelledSequenceRenderer(labelWidth, labelHeight);
                //Set the name of the sequence as the label in the LabelledSequenceRenderer
                labRen.addLabelString(render.getLabel().toString());
                //Put the AlignmentRenderer in the LabelledSequenceRenderer
                labRen.setRenderer(render);

                multi.addRenderer(render);
            }

            multi.addRenderer(ruler);

            //Set the sequence in the TranslatedSequencePanel
            proTsp.setSequence((SymbolList) ali);
            //Set the background colour of the TranslatedSequencePanel
            proTsp.setOpaque(true);
            proTsp.setBackground(Color.white);
            //Set the renderer for the TranslatedSequencePanel
            proTsp.setRenderer(multi);
            
            //Create a scrollbar and add an adjustment listener
            //scrollBar = new JScrollBar
            hscrollBar = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,300);
            hscrollBar.addAdjustmentListener(
                    new AdjustmentListener() 
                    {
                        public void adjustmentValueChanged(AdjustmentEvent e) 
                        {
                            //Get the absolute position of the scroll bar
                            double scrollBarValue = e.getValue();
                            //Get the position of the scroll bar relative to the maximum value
                            double scrollBarRatio = scrollBarValue / hscrollBar.getMaximum();
                            //Calculate the new position of the first base to be displayed
                            double pos = scrollBarRatio * (proTsp.getSequence().length() - ((proTsp.getWidth() - labelWidth) / proTsp.getScale()));
                            //Set the new SymbolTranslation for the TranslatedSequencePanel
                            proTsp.setSymbolTranslation((int) Math.round(pos));
                        }
                    });
            vscrollBar = new JScrollBar(JScrollBar.VERTICAL,0,0,0,300);
            vscrollBar.addAdjustmentListener(new AdjustmentListener() 
            {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) 
                {
                    //Get the absolute position of the scroll bar 
                    double scrollBarValue = e.getValue();
                    //Get the position of the scroll bar relative to the maximum value
                    double scrollBarRatio = scrollBarValue / vscrollBar.getMaximum();
                    //Calculate the new position of the first base to be displayed
                    double pos = scrollBarRatio * (proTsp.getSequence().length() - ((proTsp.getHeight() - labelHeight) / proTsp.getScale()));
                    //Set the new SymbolTranslation for the TranslatedSequencePanel
                    proTsp.setSymbolTranslation((int) Math.round(pos));
                    
                }
            });
            
            JPanel tmPanel = new JPanel(new BorderLayout());
            tmPanel.add(proTsp,BorderLayout.CENTER);
            tmPanel.add(vscrollBar,BorderLayout.EAST);
            tmPanel.add(hscrollBar,BorderLayout.SOUTH);
            this.proPanel = tmPanel;

            return proTsp;
        }
        
    }

    private TranslatedSequencePanel nrseqPainter() 
    {
        if (nrseqs == null ||(nrseqs != null && nrseqs.length ==0))
        {
            System.out.println("Sorry.Did not detect protein sequence.");
            return null;
        }
        else 
        {
            SymbolSequenceRenderer symbol = new SymbolSequenceRenderer() 
            {
                Paint outline = null;
                double depth = 15;
                public void paint(Graphics2D g2, SequenceRenderContext context) 
                {
                    Rectangle2D prevClip = g2.getClipBounds();
                    AffineTransform prevTransform = g2.getTransform();
                    g2.setPaint(outline);
                    Font font = context.getFont();
                    Rectangle2D maxCharBounds = font.getMaxCharBounds(g2.getFontRenderContext());
                    double scale = context.getScale();
                    if (scale >= (maxCharBounds.getWidth() * 0.3) && scale >= (maxCharBounds.getHeight() * 0.3)) 
                    {
                        double xFontOffset = 0.0;
                        double yFontOffset = 0.0;
                        xFontOffset = maxCharBounds.getCenterX() * 0.25;
                        yFontOffset = -maxCharBounds.getCenterY() + (depth * 0.5);

                        SymbolList seq = context.getSymbols();
                        SymbolTokenization toke = null;
                        try 
                        {
                            toke = seq.getAlphabet().getTokenization("token");
                        } 
                        catch (Exception ex) 
                        {
                            throw new BioRuntimeException(ex);
                        }
                        Location visible = GUITools.getVisibleRange(context, g2);
                        for (int sPos = visible.getMin(); sPos <= visible.getMax(); sPos++) 
                        {
                            double gPos = context.sequenceToGraphics(sPos);
                            String s = "?";
                            try 
                            {
                                s = toke.tokenizeSymbol(seq.symbolAt(sPos));
                            } 
                            catch (Exception ex) 
                            {
                                // We'll ignore the case of not being able to tokenize it
                            }

                            //Start of the modifications -------------------------------
                            //Make sure the text is uppercase
                            s = s.toUpperCase();
                            //Set the color according to the nucleotide for the background
                            if (s.equals("A")) {
                                g2.setColor(new Color(255, 140, 105));
                            } else if (s.equals("T")) {
                                g2.setColor(new Color(238, 238, 0));
                            } else if (s.equals("G")) {
                                g2.setColor(new Color(176, 226, 255));
                            } else if (s.equals("C")) {
                                g2.setColor(new Color(151, 251, 152));
                            } else {
                                g2.setColor(new Color(230, 230, 250));
                            }

                            //Plot the rectangle to frame the nucleotide symbol
                            g2.fill(new Rectangle2D.Double((gPos + xFontOffset) - 1.5, 0, nrTsp.getScale(), labelHeight));
                            //Set the colour for the text
                            g2.setColor(new Color(83, 83, 83));
                            //End of the modifications ---------------------------------
                            g2.drawString(s, (float) (gPos + xFontOffset), (float) yFontOffset);
                        }
                    }
                    g2.setTransform(prevTransform);
                }
            };

            //Use the Map to create a new SimpleAlignment
            Map<String, Sequence> list = new HashMap();
            for (int i = 0; i < nrseqs.length; i++) 
            {
                list.put(nrseqs[i].getName(), nrseqs[i]);
            }
            SimpleAlignment ali = new SimpleAlignment((Map) list);
            multi.addRenderer(ruler);

            for (int j = nrseqs.length - 1; j >= 0; j--) //===display alignment from top to bottom
            {
                //Instantiate the AlignmentRenderer
                render = new AlignmentRenderer();
                //Set the label for the AlignmentRenderer
                render.setLabel(ali.getLabels().get(j));
                //Set the renderer for the AlignmentRenderer
                render.setRenderer(symbol);
                //Instantiate the LabelledSequenceRenderer
                labRen = new LabelledSequenceRenderer(labelWidth, labelHeight);
                //Set the name of the sequence as the label in the LabelledSequenceRenderer
                labRen.addLabelString(render.getLabel().toString());
                //Put the AlignmentRenderer in the LabelledSequenceRenderer
                labRen.setRenderer(render);
                //Add the alignment renderers to the MultiLineRenderer
                multi.addRenderer(labRen);
            }
            multi.addRenderer(ruler);

            //Set the sequence in the TranslatedSequencePanel
            nrTsp.setSequence((SymbolList) ali);
            //Set the background colour of the TranslatedSequencePanel
            nrTsp.setOpaque(true);
            nrTsp.setBackground(Color.white);
            //Set the renderer for the TranslatedSequencePanel
            nrTsp.setRenderer(multi);

            //Create a scrollbar and add an adjustment listener
            scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
            scrollBar.addAdjustmentListener(
                    new AdjustmentListener() 
                    {
                        public void adjustmentValueChanged(AdjustmentEvent e) 
                        {
                            //Get the absolute position of the scroll bar
                            double scrollBarValue = e.getValue();
                            //Get the position of the scroll bar relative to the maximum value
                            double scrollBarRatio = scrollBarValue / scrollBar.getMaximum();
                            //Calculate the new position of the first base to be displayed
                            double pos = scrollBarRatio * (nrTsp.getSequence().length() - ((nrTsp.getWidth() - labelWidth) / nrTsp.getScale()));
                            //Set the new SymbolTranslation for the TranslatedSequencePanel
                            nrTsp.setSymbolTranslation((int) Math.round(pos));
                        }
                    });

            return nrTsp;
        }
    }

    private void secseqPainter() 
    {
        //TODO
    }
    
    public JPanel getproPanel()
    {
        return proPanel;
    }
    
}
