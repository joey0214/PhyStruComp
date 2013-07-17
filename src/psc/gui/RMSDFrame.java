/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package psc.gui;

/**
 *
 * @author zhongxf
 */
import psc.Table.PSCTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.StructureTools;


/**
 *
 * @author zhongxf
 */
public class RMSDFrame extends JFrame implements ActionListener 
{
    public double[][][] structureArrayList;
    public double[][] pdbXYZ;
    public double[] xArray;
    public double[] yArray;
    public double[] zArray;
    protected double xSumRmsd;
    protected double ySumRmsd;
    protected double zSumRmsd;
    private JTextPane content;
    private JMenuItem savetableItem;
    private JMenuItem saveASItem;
    private JMenuItem printItem;
    private JMenuItem exitItem;
    private JMenu optionMenu;
    private Structure[] pdbInput;
    private String[] strNAme;
    private LoadPDB ldb;
    private double RmsdOf2;
    public static double[][] rmsdMartix;
     private SimpleAttributeSet BOLD_BLACK;
    private String[] headers;
    


    /**
     *
     * @param pdbInputSet
     */
    public RMSDFrame() throws FileNotFoundException, IOException, StructureException 
    {
        super();
        setTitle("RMSD Matrix Table");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        show();
    }

    private void initComponents() throws FileNotFoundException, IOException, StructureException 
    {
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                exitForm();
            }
        });

        setSize(500, 500);
        //++++++++++++++ menu ++++++++++++++++++++++++++++++++++++++++++++++++++
        savetableItem = new JMenuItem("Save");
        saveASItem = new JMenuItem("Save as..");
        printItem = new JMenuItem("print");
        exitItem = new JMenuItem("exit");
       
        optionMenu = new JMenu("Option");
        optionMenu.add(savetableItem);
        optionMenu.add(saveASItem);
        optionMenu.add(printItem);
        optionMenu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(optionMenu);
        setJMenuBar(menuBar);
        
         createContent();
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         //=========== ActionListener of menuItem ===============================
        savetableItem.addActionListener(this);
        saveASItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);
       
        //======================================================================

         //#####################################################################
    }
    
    private void createContent() throws IOException 
    {   
        content = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
        
        setTopPadding(2);
        this.setTextAttribute();
        setLeftPadding(5);
        this.insertText("RMSD table", BOLD_BLACK);
        setLeftPadding(5);
        content.insertComponent(createTable());
        content.setEditable(false);
        
//         WBIToolkit.main(rmsdMartix,headers);
    }
    protected void insertText(String textString, AttributeSet set)
    {
        try
        {
            content.getDocument().insertString(content.getDocument().getLength(), textString, set);       
        }
        catch(BadLocationException e)
        {
            e.printStackTrace();
        }
    }

    private void setTopPadding(int i) {
        StringBuilder blank = new StringBuilder();
        for (int l = 0; l < i; l ++)
        {
            blank.append("\n");
            
            this.insertText(blank.toString(), BOLD_BLACK);
            this.setEndSelection();
        }
    }

    private void setEndSelection() {
        content.setSelectionStart(content.getDocument().getLength());
        content.setSelectionEnd(content.getDocument().getLength());
    }

    private void setTextAttribute() {
        BOLD_BLACK = new SimpleAttributeSet();
        
        StyleConstants.setForeground(BOLD_BLACK, Color.red);
        StyleConstants.setBold(BOLD_BLACK, true);
        StyleConstants.setFontFamily(BOLD_BLACK, "Helvetica");
        StyleConstants.setFontSize(BOLD_BLACK, 20);
    }

    private void setLeftPadding(int i) {
        StringBuilder blank = new StringBuilder();
        for (int j = 0; j < i; j++)
        {
            blank.append(" ");
            
            this.insertText(blank.toString(), BOLD_BLACK);
            this.setEndSelection();
        }
    }

    private Component createTable() throws IOException {
        // pdbInput = AlignStruct.structAligns;
         pdbInput = PSCgui.displayInGUI2.getStructure();
         strNAme = PSCgui.displayInGUI2.getStructNames();
        //strNAme = AlignStruct.structNameArray;
        rmsdMartix = Calculate(pdbInput);
        int rowNumber = strNAme.length ;
        
//        int columnNumber = pdbInput.length ;
        int columnNumber = strNAme.length ;

        
        headers = PSCgui.displayInGUI2.getStructNames();
        System.out.println(headers.length);
        for (int i = 0; i < headers.length; i++)
        {
            System.out.println("headers name: " + headers[i]);
        }
        
        PSCTable RmsdTable = new PSCTable(rowNumber, columnNumber, headers);

      Object[][] tableDatas = new Object[columnNumber][columnNumber];
      for (int i=0; i < rmsdMartix.length; i++)
      {
          for (int j = 0; j < rmsdMartix[i].length; j ++)
          {
              tableDatas[i][j] = rmsdMartix[i][j];
          }
      }
      RmsdTable.setRowHeader(strNAme);
      RmsdTable.setData(tableDatas);

        RmsdTable.setPreferredScrollableViewportSize(RmsdTable.getPreferredSize());
        JScrollPane matrixTable = new JScrollPane(RmsdTable);        
        matrixTable.setRowHeaderView(RmsdTable.getRowHeader());     
        matrixTable.setMaximumSize(new Dimension(400,400));      
        return matrixTable;
    }

    private void savetableAction() 
    {
        
    }

    private void exitForm() {
        //System.exit(0);
        setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent ex)
    {
        //MenuItem item = (MenuItem)e.getSource();
        if(ex.getSource() == savetableItem)
        {
                savetableAction();
        }
        else if(ex.getSource() == saveASItem)
        {

        }
        else if (ex.getSource() == printItem)
        {}
        else if (ex.getSource() == exitItem)
        {
            exitForm();
            //System.exit(0);
        }
    }

    public  double[][] Calculate(Structure[] pdbInput) throws IOException 
    {
        double[][] RmsdArray = new double[pdbInput.length][pdbInput.length];
        ArrayList rmsdArrayList =new ArrayList();
        Atom[][]  coordsArray = new Atom[pdbInput.length][];
        
        for (int i =0; i < pdbInput.length; i++)
        {
            coordsArray[i] = StructureTools.getAtomCAArray(pdbInput[i]);
        }
        
        int coordsArrayLength = coordsArray.length;
        
        for(int j=0; j< coordsArrayLength;j++)
        {
            int atomLen = coordsArray[j].length;           
            double sumRmsdInOne =0;
            
            for (int k = 0; k < coordsArrayLength; k++)
            { 
                if (k == j) 
                {
                    RmsdArray[j][k] = 0;
                } 
                else 
                {
                    if (k < j) 
                    {
                        RmsdArray[j][k] = RmsdArray[k][j];
                        
                    } 
                    else 
                    {
                        for (int m = 0; m < atomLen; m++) 
                        {
                            try 
                            {
                                double[] coords1 = null;
                                double[] coords2 = null;;

                                coords1 = coordsArray[j][m].getCoords();
                                coords2 = coordsArray[k][m].getCoords();
                                // line 298 Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 439 at gui.CalculateRMSD.Calculate(CalculateRMSD.java:298)
//                           //  Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 258
                                double atomRmsd = (Math.pow((coords1[0] - coords2[0]), 2)
                                        + Math.pow((coords1[1] - coords2[1]), 2)
                                        + Math.pow((coords1[2] - coords2[2]), 2));

                                sumRmsdInOne += atomRmsd;
                            } 
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            
                        }
                         RmsdOf2 = Math.sqrt( sumRmsdInOne / atomLen);
                        RmsdArray[j][k] = RmsdOf2;
                    }
                }               
            }
            
//            RmsdArray = Convert2RmsdTableArray(rmsdArrayList,pdbInput.length, pdbInput.length);
        }  
        for (int m=0; m<RmsdArray.length; m ++)
        {
            for (int n=0; n< RmsdArray[m].length; n++)
            {
                System.out.println("RmsdArray[m][n]" + RmsdArray[m][n]);
            }
        }
        return RmsdArray;
    }
}

