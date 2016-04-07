/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.joey.wuhan.phystrucomp.gui;

/**
 *
 * @author zhongxf
 */
import com.joey.wuhan.phystrucomp.Trees.TreeFrame;
import com.joey.wuhan.phystrucomp.Table.PSCTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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


/**
 *
 * @author zhongxf
 */
public class RMSDFrame extends JFrame implements ActionListener 
{
    private JTextPane content;
    private JMenuItem savetableItem, buildTree;
    private JMenuItem saveASItem;
    private JMenuItem printItem;
    private JMenuItem exitItem;
    private JMenu optionMenu;
    public static double[][] rmsdMartix;
    private SimpleAttributeSet BOLD_BLACK;
    private String[] headers;
    private StateOutput message;
    


    /**
     *
     * @param 
     */
    public RMSDFrame(double[][] testmatrix,String[] lables) 
    {
        super();
        setTitle("RMSD Matrix Table");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.rmsdMartix = testmatrix;
        this.headers = lables;
        initComponents();
        createContent();
        content.insertComponent(fuilfilTable(testmatrix,lables));
        show();
    }

    private void initComponents()
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

        buildTree = new JMenuItem("Build Tree");
        savetableItem = new JMenuItem("Save");
        saveASItem = new JMenuItem("Save as..");
        saveASItem.setEnabled(false);
        printItem = new JMenuItem("print");
        exitItem = new JMenuItem("exit");
       
        optionMenu = new JMenu("Option");
        optionMenu.add(buildTree);
        optionMenu.addSeparator();
        optionMenu.add(savetableItem);
        optionMenu.add(saveASItem);
        optionMenu.addSeparator();
        optionMenu.add(printItem);
        optionMenu.addSeparator();
        optionMenu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(optionMenu);
        setJMenuBar(menuBar);

        buildTree.addActionListener(this);
        savetableItem.addActionListener(this);
        saveASItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);
    }
    
    private void createContent()
    {   
        content = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
        
        setTopPadding(0);
        this.setTextAttribute();
        setLeftPadding(0);
        
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

    private void savetableAction() throws IOException 
    {
        String fileName = "variation_matrix.csv";
        String savePath = System.getProperty("user.dir");
        
        FileWriter writer = new FileWriter(fileName);  
        writer.write(dataPreparing());  
        writer.close();  
//        message.addMessgae(savePath + "/"+fileName);
        System.out.println(savePath + "/"+fileName); 
    }

    private void exitForm() {
        //System.exit(0);
        setVisible(false);
    }
    @Override
    public void actionPerformed(ActionEvent ex)
    {
        if (ex.getSource() == buildTree)
        {
            TreeFrame treeFrame = new TreeFrame(rmsdMartix, headers);
        }
        
        if(ex.getSource() == savetableItem)
        {
            try 
            {
                savetableAction();
            } 
            catch (IOException ex1) 
            {
                Logger.getLogger(RMSDFrame.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        if(ex.getSource() == saveASItem)
        {

        }
        if (ex.getSource() == printItem)
        {
            //TODO
        }
        
        if (ex.getSource() == exitItem)
        {
            setVisible(false);
            //System.exit(0);
        }
    }

    private JScrollPane fuilfilTable(double[][] testmatrix,String[] lables) 
    {
        int rowNumber = lables.length ;
        int columnNumber = lables.length ;
        rmsdMartix = testmatrix;

        
        headers = lables;
        System.out.println(headers.length);
//        for (int i = 0; i < headers.length; i++)
//        {
//            System.out.println("headers name: " + headers[i]);
//        }
//        for (int i =0; i <rmsdMartix.length; i ++)
//        {
//            for (int j =0; j < rmsdMartix[i].length; j ++)
//            {
//                System.out.println(rmsdMartix[i][j]);
//            }
//            System.out.println("test");
//        }
        
        PSCTable RmsdTable = new PSCTable(rowNumber, columnNumber, headers);

        System.out.println("testmatrix.length        " + testmatrix.length);
        System.out.println("testmatrix.length        " + testmatrix[0].length);
        
        Object[][] tableDatas = new Object[columnNumber][columnNumber];
        for (int i = 0; i < testmatrix.length; i++) 
        {
            for (int j = 0; j < testmatrix[i].length; j++) 
            {
                tableDatas[i][j] = testmatrix[i][j];
            }
        }
        RmsdTable.setRowHeader(lables);
        RmsdTable.setData(tableDatas);

        RmsdTable.setPreferredScrollableViewportSize(RmsdTable.getPreferredSize());
        JScrollPane matrixTable = new JScrollPane(RmsdTable);        
        matrixTable.setRowHeaderView(RmsdTable.getRowHeader());     
        matrixTable.setMaximumSize(new Dimension(500,500));      
        return matrixTable;
    }

    private String dataPreparing() 
    {
        String data = "header";
        for (int i =0; i < headers.length; i++)
        {
            data += ","+headers[i];
        }
        
        data += "\n";
        
        for (int j =0; j < headers.length; j++)
        {
            data += headers[j];
            for (int k =0; k < headers.length; k ++)
            {
                if (k == headers.length - 1)
                {
                     data +="," + rmsdMartix[j][k] + "\n";
                }
                else 
                {
                   data +="," + rmsdMartix[j][k]; 
                }
               
            }
        }
        
        return data;   
    }
}

