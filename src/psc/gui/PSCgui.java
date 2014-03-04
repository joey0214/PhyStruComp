
package psc.gui;

/**
 *this is GUI of protein structure comparison and phylogenetics.
 *this GUI provide the main interface of program.  
 * @author zhongxf
 */

import psc.IO.AppendPDB;
import psc.IO.LoadSeqs;
import psc.IO.LoadPDB;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.jmol.api.JmolViewer;
import psc.Sequence.SeqAligner;
import psc.Structure.StructureAligner;
       
public class PSCgui extends JFrame implements ActionListener 
{ 
    private GridBagConstraints gbc;
    private Dimension screen;
    //***** menu *****
    private JMenu fileMenu, helpMenu,analysisMenu;
    private JMenuBar menuBar;
    private JMenuItem loadItem, appendItem, exitItem, aboutItem,alignItem,calculateRMSDItem;
    private JMenuItem buildTreeItem, deleteItem,saveAlignmentItem, loadSeqsItem;
    //***** frame *****
//    public static  JSplitPane proseqSiltPane;
//    private JSplitPane secseqSiltPane;
    private JPanel structPanel, secseqPanel,  proseqPanel,toolPanel, appPanel,outputPanel;
    //pdb_textarea just for pdb display at the monment
    //change dna_seq_area and aa_seq_area from JTextPane to JTextArea
    private JButton loadButton, calcuateRmsd,appendPDB, TestTable,alignButton;
    public static JmolPanel jmolPanel;
    public JmolViewer viewer;
    public double[][][] structureArrayList;
    public double[][] pdbXYZ;
    public double[] xArray;
    public double[] yArray;
    public double[] zArray;
    private JButton functArea;
    public  static Vector<Integer> functionArea;
    public static int loadRuned, appendRuned,alignMenthodInt;
    public static JComboBox alignMethod;
    public static JTextArea outpuTextArea;
    public static JPanel rightAaPanel;
    public static InputHub inputHub;
    private int frameWidth, frameHeight;
    private JFrame mainFrame;
   
    public PSCgui() 
    {
        super();
        setTitle("ProStructCompar");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        show();
    }

    private void initComponents() 
    { 
        inputHub = new InputHub();
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
        
        makeMenu();

        loadRuned = 0;
        appendRuned = 0;
        //+++++++++++++++++ frame ++++++++++++++++++++++++++++++++++++++++++++++
        //  frame size
        screen = Toolkit.getDefaultToolkit().getScreenSize();
//        setSize(800, 1000);//
        setSize(screen.width , screen.height );
        frameHeight = this.getHeight();
        frameWidth = this.getWidth();
        System.out.println(frameHeight + "   " + frameWidth);
        System.out.println(screen.height + "   " + screen.width);
//        setLocation(screen.width / 6, screen.height / 6);


        makePanel();
 
        addJmol();

        fillToolPanel();      
        
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

    @Override
    public void actionPerformed(ActionEvent ace) 
    {
        if (ace.getSource() == loadItem)
        {
            try 
            {
                try {
                    LoadPDB loadPDB = new  LoadPDB();
                   
                } catch (IllegalSymbolException ex) {
                    Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == appendItem) 
        { 
            try 
            {
                try {
                    psc.IO.AppendPDB appendPDB1 = new  AppendPDB();
                } catch (IllegalSymbolException ex) {
                    Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == exitItem )
        {
           System.exit(0);
        }
        
        if (ace.getSource() == aboutItem)
        {
            (new AboutDialog(this)).show();
        }
        
        if (ace.getSource() == loadButton) 
        {
            try 
            {
                try {
                    LoadPDB loadPDB = new  LoadPDB();
                } catch (IllegalSymbolException ex) {
                    Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == appendPDB )
        {
            try 
            {
                try {
                    psc.IO.AppendPDB appendPDB1 = new AppendPDB();
                } catch (IllegalSymbolException ex) {
                    Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == alignButton )
        {
            try 
            {
                StructureAligner strucrAligner = new StructureAligner();
                strucrAligner.setStructures(inputHub.getStructure());
                strucrAligner.setAlignerMethod(alignMenthodInt);
                strucrAligner.alignRun();
                inputHub.setStructure(strucrAligner.getAlignedStructure());
                
                SeqAligner seqAligner = new SeqAligner();
                seqAligner.setSequernce(inputHub.getProseq());
                seqAligner.alignRun();
                inputHub.setProseq(seqAligner.getAlignedSeq());

                inputHub.update();               
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        
        if (ace.getSource() == calcuateRmsd )
        {
            try 
            {
                RMSDFrame rmsdFrame = new RMSDFrame();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() ==  functArea  )
        {
            FuncAreaSet funcAreaSet = new FuncAreaSet();
        }
        
        if (ace.getSource() ==  saveAlignmentItem  )
        {
            //TODO
        }
        
        if (ace.getSource() ==  deleteItem  )
        {
            //TODO
        }
        
        if (ace.getSource() ==  buildTreeItem  )
        {
            //TODO
        }
        
        if (ace.getSource() ==  loadSeqsItem  )
        {
            try {
                LoadSeqs loadseqs = new LoadSeqs();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (StructureException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalSymbolException ex) {
                Logger.getLogger(PSCgui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (ace.getSource() == TestTable  )
        {
            try 
            {
                 LoadSeqs loadSecondSeq = new LoadSeqs();
//                gui.TestTable testTable = new TestTable();
                //SecStruct secStruct = new SecStruct();
            } 
            catch (Exception e){e.printStackTrace();}
        }
        
        if (ace.getSource() == PSCgui.alignMethod)
        {
           int alignMethIndex = PSCgui.alignMethod.getSelectedIndex();
           switch (alignMethIndex) 
           {
               //alignMeth = {"Default", "CE", "FATCAT","PairAligner", "DefaultAdvanced","MUSTANG"};
               case 0:
                   alignMenthodInt = 0; //Default
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               case 1:
                   alignMenthodInt = 1; //CE
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               case 2:
                   alignMenthodInt = 2; //FATCAT
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               case 3:
                   alignMenthodInt = 3; //PairAligner
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               case 4:
                   alignMenthodInt = 4; //DefaultAdvanced
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               case 5:
                   alignMenthodInt = 5; //MUSTANG
                   System.out.println(alignMethod.getSelectedItem().toString());
                   break;
               
           }
       }
    }

    private void makeMenu() 
    {
        loadItem = new JMenuItem("Load PDB");
        loadItem.addActionListener(this);

        appendItem = new JMenuItem("Append PDB");
        appendItem.addActionListener(this);
        
        loadSeqsItem = new JMenuItem("Load Sequences");
        loadSeqsItem.addActionListener(this);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        
        alignItem = new JMenuItem("Align");
        alignItem.addActionListener(this);
        
        calculateRMSDItem = new JMenuItem("Calcunate RMSD");
        calculateRMSDItem.addActionListener(this);
        
        buildTreeItem = new JMenuItem("Build Tree");
        buildTreeItem.addActionListener(this);
        
        deleteItem = new JMenuItem("Delete selections");
        deleteItem.addActionListener(this);
        
        saveAlignmentItem = new JMenuItem("Save Alignment");
        saveAlignmentItem.addActionListener(this);

        fileMenu = new JMenu("File");
        fileMenu.add(loadItem);
        fileMenu.add(appendItem);
        fileMenu.add(loadSeqsItem);
        fileMenu.add(saveAlignmentItem);
        fileMenu.add(exitItem);
        
        analysisMenu = new JMenu("Analysis");
        analysisMenu.add(alignItem);
        analysisMenu.add(calculateRMSDItem);
        analysisMenu.add(deleteItem);
        analysisMenu.add(buildTreeItem);

        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);

        //***** help menu *****
        helpMenu = new JMenu("Help");
        helpMenu.add(aboutItem);

        //******** menu bar *****
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(analysisMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void makePanel() 
    {
        JComponent pane;
//        GridBagConstraints c;
        pane = (JComponent) getContentPane();
        pane.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        //set distance between containers
        gbc.insets = new Insets(4, 4, 4, 4);
        
        structPanel = new JPanel();
        structPanel.setLayout(new GridBagLayout());
        structPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Protein structure"));
        structPanel.setPreferredSize(new Dimension(frameWidth *3/ 4, frameHeight/ 2));
        
        appPanel = new JPanel();
//        appPanel.setSize(new Dimension(800, 100));
        appPanel.setLayout(new GridBagLayout());
        appPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Jmol Console"));

        secseqPanel = new JPanel();
        secseqPanel.setLayout(new GridBagLayout());
        secseqPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Secondary Structure Sequence"));
        secseqPanel.setPreferredSize(new Dimension(frameWidth *3/ 4, frameHeight / 4));

        proseqPanel = new JPanel();
        proseqPanel.setLayout(new GridBagLayout());
        proseqPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Protein Sequence"));
        proseqPanel.setPreferredSize(new Dimension(frameWidth *3/ 4, frameHeight / 4));
        proseqPanel.setSize(frameWidth *3/ 4, frameHeight / 4);

        toolPanel = new JPanel();
        toolPanel.setLayout(new GridBagLayout());
        toolPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "Tool"));

        outputPanel = new JPanel();
        outputPanel.setLayout(new GridBagLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED), "OutPut"));

        //when the size of frame changed, containes do nothing
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, toolPanel, gbc, 1, 0, 1, 1);
        
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, appPanel, gbc, 1, 1, 1, 1);

        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, outputPanel, gbc, 1, 2, 1, 3);

        //when the size of frame changed, containes change sise to fit new frame
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(pane, structPanel, gbc, 0, 0, 1, 1);
        add(pane, secseqPanel, gbc, 0, 1, 1, 1);
        add(pane, proseqPanel, gbc, 0, 2, 1, 1);

        outpuTextArea = new JTextArea();
//        outpuTextArea.setRows(50);
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollOutputPanel = new JScrollPane(outpuTextArea);
        add(outputPanel, scrollOutputPanel, gbc, 1, 0, 0, 0);
        StateOutput stateOutput = new StateOutput();
        stateOutput.addMessgae("hello");
    }

    private void addJmol() 
    {
        jmolPanel = new JmolPanel();
        jmolPanel.setPreferredSize(new Dimension(400, 400));
//        jmolPanel.setPreferredSize(new Dimension(screen.width/2, screen.height/2));
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        add(structPanel, jmolPanel, gbc, 0, 0, 1, 1);  
    }

    private void fillToolPanel() 
    {
        loadButton = new JButton("Load PDB");
        loadButton.addActionListener(this);

        appendPDB = new JButton("Append");
        appendPDB.addActionListener(this);

        alignButton = new JButton("Align");
        alignButton.setToolTipText("hello world");
        alignButton.addActionListener(this);

        calcuateRmsd = new JButton("calcuate RMSD");
        calcuateRmsd.addActionListener(this);

        functArea = new JButton("set function area");
        functArea.addActionListener(this);

        TestTable = new JButton("TestTable");
        TestTable.addActionListener(this);
        
        String[] alignMeth = {"Default", "CE", "FATCAT","PairAligner", "DefaultAdvanced","MUSTANG"};
        alignMethod = new JComboBox(alignMeth);
        JLabel alignMethodLabel = new JLabel("AlignMethod");
        alignMethod.addActionListener(this);
        

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(toolPanel, loadButton, gbc, 0, 0, 1, 1);
        add(toolPanel, appendPDB, gbc, 0, 1, 1, 1);
        add(toolPanel, alignButton, gbc, 0, 2, 1, 1);
        add(toolPanel, calcuateRmsd, gbc, 0, 3, 1, 1);
        add(toolPanel, functArea, gbc, 0, 4, 1, 1);
        add(toolPanel, TestTable, gbc, 0, 5, 1, 1);
        add(toolPanel, alignMethodLabel, gbc, 0, 6, 1, 1);
        add(toolPanel, alignMethod, gbc, 0, 7, 1, 1);
    }
    
    public void update(){}
}