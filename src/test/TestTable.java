/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author zhongxf
 */
import psc.IO.LoadPDB;
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
public class TestTable extends JFrame implements ActionListener 
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
    private SimpleAttributeSet BOLD_BLACK;
    private String[] headers;
    private double[][] testdata;
    /**
     *
     * @param pdbInputSet
     */
    public TestTable() throws FileNotFoundException, IOException, StructureException 
    {
        super();
        setTitle("TestTable");
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
        savetableItem = new JMenuItem("Save");
        saveASItem = new JMenuItem("Save as..");
        printItem = new JMenuItem("print");
        exitItem = new JMenuItem("exit");
        exitItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                exitForm();
            }
        });
        
        optionMenu = new JMenu("Option");
        optionMenu.add(savetableItem);
        optionMenu.add(saveASItem);
        optionMenu.add(printItem);
        optionMenu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(optionMenu);
        setJMenuBar(menuBar);
        savetableItem.addActionListener(this);
        saveASItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);
        createContent();
    }

    private void savetableAction(ActionEvent e) 
    {
        System.out.println("hello");
    }

    private void exitForm() 
    {
        setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        MenuItem item = (MenuItem)e.getSource();
        if(item.equals(savetableItem))
        {
                savetableAction(e);
        }
        else if(item.equals(saveASItem))
        {

        }
        else if (item.equals(printItem))
        {}
        else if (item.equals(exitItem))
        {
            exitForm();
        }
    }

    private double[][] Calculate(Structure[] pdbInput) 
    {
        double[][] RmsdArray = new double[pdbInput.length][pdbInput.length];
        ArrayList rmsdArrayList =new ArrayList();
        Atom[][]  coordsArray = new Atom[pdbInput.length][];
        for (int i =0; i < pdbInput.length; i++)
        {
            coordsArray[0] = StructureTools.getAtomCAArray(pdbInput[i]);
        }
        
        for(int j=0; j< coordsArray.length;j++)
        {
            int atomLen = coordsArray[j].length;           
            double sumRmsdInOne =0;
            for (int k = 0; k < coordsArray.length; k++)
            {
                if (k >j)
                {
                    for (int m = 0; m < atomLen; m++) 
                    {
                        double[] coords1 = coordsArray[j][m].getCoords();
                        double[] coords2 = coordsArray[k][m].getCoords();
                        double atomRmsd = (Math.pow((coords1[0] - coords2[0]), 2)
                                + Math.pow((coords1[1] - coords2[1]), 2)
                                + Math.pow((coords1[2] - coords2[2]), 2));
                        sumRmsdInOne += atomRmsd;
                    }
                    RmsdOf2 = Math.sqrt((1 / atomLen) * sumRmsdInOne);
                    RmsdArray[j][k] = RmsdOf2;  
                }
                RmsdArray[k][j] = RmsdOf2;
                
                if (j == k )
                {                 
                    RmsdArray[j][k] = 0;
                }               
            }          
        }
        return RmsdArray;
    }

    private void createContent() 
    {   
        content = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);       
        setTopPadding(2);
        this.setTextAttribute();
        setLeftPadding(5);
        this.insertText("test table", BOLD_BLACK);
        setLeftPadding(5);
        content.insertComponent(createTable());
        content.setEditable(false);
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

    private void setTopPadding(int i) 
    {
        StringBuilder blank = new StringBuilder();
        for (int l = 0; l < i; l ++)
        {
            blank.append("\n");            
            this.insertText(blank.toString(), BOLD_BLACK);
            this.setEndSelection();
        }
    }

    private void setEndSelection() 
    {
        content.setSelectionStart(content.getDocument().getLength());
        content.setSelectionEnd(content.getDocument().getLength());
    }

    private void setTextAttribute() 
    {
        BOLD_BLACK = new SimpleAttributeSet();        
        StyleConstants.setForeground(BOLD_BLACK, Color.red);
        StyleConstants.setBold(BOLD_BLACK, true);
        StyleConstants.setFontFamily(BOLD_BLACK, "Helvetica");
        StyleConstants.setFontSize(BOLD_BLACK, 20);
    }

    private void setLeftPadding(int i) 
    {
        StringBuilder blank = new StringBuilder();
        for (int j = 0; j < i; j++)
        {
            blank.append(" ");            
            this.insertText(blank.toString(), BOLD_BLACK);
            this.setEndSelection();
        }
    }

    private Component createTable() 
    {
        int rowNumber = 2 ;      
        int columnNumber = 2 ;
        String[] headers = new String[2];
        headers[0] = "hello88";
        headers[1] = "hello99";
        System.out.println(headers.length);
        System.out.println("hello01");
        PSCTable RmsdTable = new PSCTable(rowNumber, columnNumber, headers);
        Double[][] testdata = new Double[2][2];
        testdata[0][0] = 1.0; testdata[0][1] = 2.0; 
        testdata[1][0] = 3.0; testdata[1][1] =4.0 ; 
      System.out.println("test data : " + testdata); 
      for (int i =0; i<testdata.length; i++)
      {
          for (int l = 0; l < testdata[i].length;l ++)
          {
              System.out.println("testdata" + "[" + i + "]" + "[" + l + "]" + ":" + testdata[i][l]);
          }
      }
      RmsdTable.setRowHeader(headers);
      RmsdTable.setData(testdata);
        RmsdTable.setPreferredScrollableViewportSize(RmsdTable.getPreferredSize());
        JScrollPane matrixTable = new JScrollPane(RmsdTable);        
        matrixTable.setRowHeaderView(RmsdTable.getRowHeader());     
        matrixTable.setMaximumSize(new Dimension(400,400));      
        return matrixTable;
    }

    private Object[][] convertToObj(double[][] testdata) 
    {
        int length1 = testdata.length;
        int length2 = testdata[0].length;
        Object[][] rmsdObjects = new Object[length1][length2];
        for (int i = 0; i< testdata.length;i++)
        {           
            for (int j = 0; j<testdata[i].length; j++)
            {
               rmsdObjects[i][j] = testdata[i][j]; 
            }
        }
        return rmsdObjects;
    }
}