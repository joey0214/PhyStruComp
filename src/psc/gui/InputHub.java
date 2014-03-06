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
public class InputHub 
{
    private Structure[] structureList ;
    private Sequence[] secSeqList, aaSeqList;
    private ProteinSequence[] proteinSequencesList ;
    private File[] inFiles = null;
    private String[] structnames ;
    private StructureFactory structFactory = new StructureFactory();
    private File[] secseqfiles;
    private Sequence[] secseqs;
    
    public void setStructure(Structure[] structArray)
    {
        this.structureList = structArray;
    }
    
    public void setProseq(ProteinSequence[] proseqArray)
    {
        this.proteinSequencesList = proseqArray;
        
    }
    
    public void setSeqFile(File[] files) throws IOException
    {
        this.inFiles = files;
//        extractFiles();
        structFactory.setInPDBfiles(inFiles);
        this.structureList = structFactory.getStructure();
        this.proteinSequencesList = structFactory.getProSeq();
        this.structnames = structFactory.getStructureName();
    }
    
     public void setStructureFile(File[] files) throws IOException
    {
        this.inFiles = files;
//        extractFiles();
        structFactory.setInPDBfiles(inFiles);
        this.structureList = structFactory.getStructure();
        this.proteinSequencesList = structFactory.getProSeq();
        this.structnames = structFactory.getStructureName();
    }
    
    public Structure[] getStructure()
    {
        return structureList;
    }
    
    public ProteinSequence[] getProseq()
    {
        return proteinSequencesList;
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
        if (structureList == null|| (structureList != null && structureList.length ==0))
        {
            this.structureList = appendStruct;
        }
        else 
        {
            int renewLen = structureList.length + appendStruct.length;
            Structure[] renewStructures = new Structure[renewLen];
            System.arraycopy(structureList, 0, renewStructures, 0, structureList.length);
            System.arraycopy(appendStruct, 0, renewStructures, structureList.length, appendStruct.length);
            this.structureList = renewStructures;
        }
    }
    public void appendProseq(ProteinSequence[] appendProseq)
    {
        if (proteinSequencesList == null || (proteinSequencesList !=null && proteinSequencesList.length == 0))
        {
            this.proteinSequencesList = appendProseq;
        }
        else 
        {
            int renewLen = proteinSequencesList.length + appendProseq.length;
            ProteinSequence[] renewProseq = new ProteinSequence[renewLen];
            System.arraycopy(proteinSequencesList, 0, renewProseq, 0, proteinSequencesList.length);
            System.arraycopy(appendProseq, 0, renewProseq, proteinSequencesList.length, appendProseq.length);
            this.proteinSequencesList = renewProseq;
        }
    }
    
    public void appendFiles(File[] files) throws IOException
    {
        structFactory.appendFiles(files);
        this.structureList = structFactory.getStructure();
        this.proteinSequencesList = structFactory.getProSeq();
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
        structureUpdate();
        alignmentUpdata();
        
        if (structureList != null &&proteinSequencesList.length !=0)
        {
            if (proteinSequencesList != null && proteinSequencesList.length !=0)
            {
                visulazation();
            }
            else 
            {
                
                structFactory.setStructure(structureList);
                this.proteinSequencesList = structFactory.getProSeq();
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
        this.proteinSequencesList = structFactory.getProSeq();
        this.structureList = structFactory.getStructure();
        this.structnames = structFactory.getStructureName();
    }

    private void visulazation() throws IllegalSymbolException 
    {
        proAlignmentUpdate();
     
      System.out.println("you are using displayindui2");
        PSCgui.jmolPanel.setMultipleStructure(structureList);
        
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
        seqPainter.setProteinSeq(proteinSequencesList);
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
//        PSCgui.proseqSiltPane.setRightComponent(jScrollPane);
    }

    private void structureUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void alignmentUpdata() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
