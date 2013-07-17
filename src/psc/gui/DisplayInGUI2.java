/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava3.core.sequence.ProteinSequence;
import psc.Sequence.SeqPainter;
import psc.Structure.StructureFactory;

/**
 *
 * @author zhongxf
 */
public class DisplayInGUI2 
{
    private Structure[] dispStructs = null;
    private ProteinSequence[] dispProseq = null;
    private File[] inFiles = null;
    private String[] structnames ;
    private StructureFactory structFactory = new StructureFactory();
    private File[] secseqfiles;
    private Sequence[] secseqs;
    
    public void setStructure(Structure[] structArray)
    {
        this.dispStructs = structArray;
    }
    
    public void setProseq(ProteinSequence[] proseqArray)
    {
        this.dispProseq = proseqArray;
        
    }
    
    public void setFile(File[] files) throws IOException
    {
        this.inFiles = files;
//        extractFiles();
        structFactory.setInPDBfiles(inFiles);
        this.dispStructs = structFactory.getStructure();
        this.dispProseq = structFactory.getProSeq();
        this.structnames = structFactory.getStructureName();
    }
    
    public Structure[] getStructure()
    {
        return dispStructs;
    }
    
    public ProteinSequence[] getProseq()
    {
        return dispProseq;
    }
    
    public String[] getStructNames()
    {
        return structnames;
    }
    
    public void setSecstructSeq()
    {
        //TODO
    }
    public void appendStructure(Structure[] appendStruct)
    {
        if (dispStructs == null|| (dispStructs != null && dispStructs.length ==0))
        {
            this.dispStructs = appendStruct;
        }
        else 
        {
            int renewLen = dispStructs.length + appendStruct.length;
            Structure[] renewStructures = new Structure[renewLen];
            System.arraycopy(dispStructs, 0, renewStructures, 0, dispStructs.length);
            System.arraycopy(appendStruct, 0, renewStructures, dispStructs.length, appendStruct.length);
            this.dispStructs = renewStructures;
        }
    }
    public void appendProseq(ProteinSequence[] appendProseq)
    {
        if (dispProseq == null || (dispProseq !=null && dispProseq.length == 0))
        {
            this.dispProseq = appendProseq;
        }
        else 
        {
            int renewLen = dispProseq.length + appendProseq.length;
            ProteinSequence[] renewProseq = new ProteinSequence[renewLen];
            System.arraycopy(dispProseq, 0, renewProseq, 0, dispProseq.length);
            System.arraycopy(appendProseq, 0, renewProseq, dispProseq.length, appendProseq.length);
            this.dispProseq = renewProseq;
        }
    }
    
    public void appendFiles(File[] files) throws IOException
    {
        structFactory.appendFiles(files);
        this.dispStructs = structFactory.getStructure();
        this.dispProseq = structFactory.getProSeq();
        this.structnames = structFactory.getStructureName();
        
//        if (inFiles == null ||(inFiles != null && inFiles.length ==0))
//        {
//            this.inFiles = files;
//        }
//        else 
//        {
//            int renewLen = inFiles.length + files.length;
//            File[] renewFiles = new File[renewLen];
//            System.arraycopy(inFiles, 0, renewFiles, 0, inFiles.length);
//            System.arraycopy(files, 0, renewFiles, inFiles.length, files.length);
//            this.inFiles = renewFiles;
//            extractFiles();
//        }
        
    }
    public void update() throws IllegalSymbolException
    {
        if (dispStructs != null &&dispProseq.length !=0)
        {
            if (dispProseq != null && dispProseq.length !=0)
            {
                visulazation();
            }
            else 
            {
                
                structFactory.setStructure(dispStructs);
                this.dispProseq = structFactory.getProSeq();
                visulazation();
            }
        }
        else 
        {
            System.out.println("Do not find the input structures or files");
            //error 
            // or no input
        }
    }

    private void extractFiles() throws IOException 
    {
        StructureFactory structFactory = new StructureFactory();
        structFactory.setInPDBfiles(inFiles);
        this.dispProseq = structFactory.getProSeq();
        this.dispStructs = structFactory.getStructure();
        this.structnames = structFactory.getStructureName();
    }

    private void visulazation() throws IllegalSymbolException 
    {
        proAlignmentUpdate();
     
      System.out.println("you are using displayindui2");
        PSCgui.jmolPanel.setMultipleStructure(dispStructs);
        
        //PSCmain.jmolPanel.setMultipleStructure(structAligns);
    }

    public void setSecseqFile(File[] secseqFiles) 
    {
        this.secseqfiles = secseqFiles;
        if (secseqfiles !=null &&(secseqfiles.length != 0))
        {
            getSecSeq();
        }
    }
    
    private void getSecSeq()
    {
        
    }
    
    public String getFilePath()
    {
        String filePath = null;
        for (int i =0 ; i < inFiles.length; i ++)
        {
            String tmppath = inFiles[i].getParent();
            System.out.println("file path " + inFiles[i].getParent());
            System.out.println("file name "+inFiles[i].getName());
            System.out.println(tmppath);
            if (filePath == null)
            {
                filePath = tmppath;
            }
            else 
            {
                if (!filePath.equals(tmppath))
                {
                    System.out.println("Please put all input structures in the same directory!");
                }
            }
            
        }
        return filePath;
    }
    
    public String[] getFilesName()
    {
        String[] filesname = new String[inFiles.length];
        if (inFiles != null && inFiles.length != 0)
        {
            for (int i = 0; i < inFiles.length; i++) 
            {
                filesname[i] = inFiles[i].getName();
            }
        }
        else 
        {
            System.out.println("Fatal error: check inpur files!");
        }
        return filesname;
    }

    //update chengs in protein sequences alignment in translatedSequencePanel
    private void proAlignmentUpdate() throws IllegalSymbolException 
    {
        final int labelHeight = 20;
        final int labelWidth = 50;
        final SeqPainter seqPainter = new SeqPainter();
        seqPainter.setProteinSeq(dispProseq);
        JScrollPane jScrollPane = new JScrollPane(seqPainter.getproTsp());

        final JScrollBar vScrollBar = new JScrollBar(JScrollBar.VERTICAL,0,0,0,100);
        vScrollBar.addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) 
            {
                //Get the absolute position of the scroll bar
                    double scrollBarValue = e.getValue();
                    //Get the position of the scroll bar relative to the maximum value
                    double scrollBarRatio = scrollBarValue / vScrollBar.getMaximum();
                    //Calculate the new position of the first base to be displayed
                    double pos = scrollBarRatio * (seqPainter.getproTsp().getSequence().length() - ((seqPainter.getproTsp().getHeight() - labelHeight) / seqPainter.getproTsp().getScale()));
                    //Set the new SymbolTranslation for the TranslatedSequencePanel
                    seqPainter.getproTsp().setSymbolTranslation((int) Math.round(pos));
                   
            }
        });
        
        final JScrollBar hScrollBar = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,100);
//        vScrollBar.addAdjustmentListener(new AdjustmentListener() {
//
//            @Override
//            public void adjustmentValueChanged(AdjustmentEvent e) 
//            {
//                //Get the absolute position of the scroll bar
//                    double scrollBarValue = e.getValue();
//                    //Get the position of the scroll bar relative to the maximum value
//                    double scrollBarRatio = scrollBarValue / hScrollBar.getMaximum();
//                    //Calculate the new position of the first base to be displayed
//                    double pos = scrollBarRatio * (seqPainter.getproTsp().getSequence().length() - ((seqPainter.getproTsp().getWidth() - labelWidth) / seqPainter.getproTsp().getScale()));
//                    //Set the new SymbolTranslation for the TranslatedSequencePanel
//                    seqPainter.getproTsp().setSymbolTranslation((int) Math.round(pos));
//            }
//        });
       
        jScrollPane.setHorizontalScrollBar(hScrollBar);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBar(vScrollBar);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
//        PSCgui.proseqSiltPane.setRightComponent(seqPainter.getproPanel());
        PSCgui.proseqSiltPane.setRightComponent(jScrollPane);
    }
}
