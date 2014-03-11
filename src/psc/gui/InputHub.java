/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
//import org.biojava.bio.seq.Sequence;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava3.core.sequence.ProteinSequence;
import psc.IO.AlignmentIO;
import psc.IO.SequencesIO;
import psc.IO.StructureIO;
import psc.Sequence.Alignment;
import psc.Sequence.AlignmentPanel;
import psc.Sequence.AlignmentView;
import psc.Sequence.SeqPainter;
import psc.Sequence.Sequence;
//import psc.Structure.StructureFactory;

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
//    private StructureFactory structFactory = new StructureFactory();
    private File[] secseqfiles;
    private Sequence[] secseqs;
    private StructureIO structureIO = new StructureIO();
    private SequencesIO sequenceIO = new SequencesIO();
    private String[] seqsLabels ;
    private Sequence[] gapSequences;
    private AlignmentPanel alignmentPanel;
    
    public void setStructure(Structure[] structArray)
    {
        this.structureList = structArray;
    }
    
    public void setProseq(ProteinSequence[] proseqArray)
    {
        this.proteinSequencesList = proseqArray;
        
    }
    
    public void setSequencesFile(File[] files) throws IOException
    {
        this.inFiles = files;
        sequenceIO.readSequencesFiles(inFiles);
        this.aaSeqList = sequenceIO.getOriginSequences();
        this.gapSequences = sequenceIO.getGapedSequences();
        this.seqsLabels = sequenceIO.getSequencesNames();
    }
    
     public void setPdbFile(File[] files) throws IOException
    {
        this.inFiles = files;
//        extractFiles();
        structureIO.readPDBFiles(inFiles);
        this.structureList = structureIO.getStructures();
        this.proteinSequencesList = structureIO.getProteinSequences();
        this.aaSeqList = structureIO.getAASequences();
        this.structnames = structureIO.getStructureNames();
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
        structureIO.readPDBFiles(files);
        this.structureList = structureIO.getStructures();
        this.proteinSequencesList = structureIO.getProteinSequences();
        this.structnames = structureIO.getStructureNames();
        
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
                
                structureIO.readStructure(structureList);
                this.proteinSequencesList = structureIO.getProteinSequences();
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
        structureIO.readPDBFiles(inFiles);
        this.proteinSequencesList = structureIO.getProteinSequences();
        this.structureList = structureIO.getStructures();
        this.structnames = structureIO.getStructureNames();
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

    private void structureUpdate() 
    {
        PSCgui.jmolPanel.setMultipleStructure(structureList);
    }

    private void alignmentUpdata() 
    {
        AlignmentIO alignmentIO = new AlignmentIO();
        alignmentIO.loadAlignment(aaSeqList);
        Alignment alignment = alignmentIO.getAlignment();
        AlignmentView alignView = new AlignmentView(alignment);

//        if (alignView == null) {
//            System.out.println("test alignment null");
//        }
        
         PSCgui.proseqPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        //set distance between containers
//        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        
        if (PSCgui.proseqPanel.getComponents().length == 0)
        {
            alignmentPanel = new  AlignmentPanel(alignView);
        
//            PSCgui.proseqPanel.add(alignmentPanel);
            add(PSCgui.proseqPanel,alignmentPanel,gbc,0,0,0,0);
        }
        //append files after loaded
        else
        {
            PSCgui.proseqPanel.removeAll();
            AlignmentPanel newAlifnView = new AlignmentPanel(alignView);
            newAlifnView.setSize(PSCgui.proseqPanel.getSize());
//            PSCgui.proseqPanel.getSize()
//            PSCgui.proseqPanel.add(newAlifnView);
            add(PSCgui.proseqPanel,alignmentPanel,gbc,0,0,0,0);
            
        }
    }
    
    private void add(Container cn, Component c, GridBagConstraints gbc, 
            int x, int y, int w, int h) 
    {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        cn.add(c, gbc);
    } 
}
