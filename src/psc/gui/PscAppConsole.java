/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.jmol.api.JmolAppConsoleInterface;
import org.jmol.api.JmolViewer;
import org.jmol.console.JmolConsole;
import org.jmol.i18n.GT;
import org.jmol.util.CommandHistory;
import org.jmol.util.Logger;
import org.jmol.viewer.Viewer;
import org.openscience.jmol.app.jmolpanel.DisplayPanel;

/**
 *
 * @author zhongxf
 */
public class PscAppConsole extends JmolConsole implements 
        JmolAppConsoleInterface
{

    @Override
    protected void setupLabels() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void clearContent(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void execute(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendConsoleMessage(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendConsoleEcho(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JmolAppConsoleInterface getAppConsole(JmolViewer jv) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void zap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//
//    protected ConsoleTextPane console;   //ConsoleTextPane
//    private JButton varButton, haltButton, closeButton, clearButton,
//            questButton, helpButton, undoButton, redoButton;
//    private JButton checkButton;
//    protected JButton stepButton;
//    private JButton topButton;
//    private ConsoleTextPane consoleTextPane;
//
//    public PscAppConsole(){}
//
//    public JmolAppConsoleInterface getPscAppConsole(Viewer viewer1, Component display)
//    {
//        return new PscAppConsole(viewer, display instanceof DisplayPanel
//                ? ((DisplayPanel)display).getFrame()
//                : display instanceof JFrame ? (JFrame) display : null);
//
//    }
//
//    private PscAppConsole(JmolViewer viewer, JFrame frame)
//    {
//        super(viewer , frame);
//        layoutWindow(getContentPane());
//        setSize(400,400);
//        setLocationRelativeTo(frame);
//    }
//
//
//    @Override
//    JButton setButton(String s)
//    {
//        JButton b = new JButton(s);
//        b.addActionListener(this);
//        buttonPanel.add(b);
//        return b;
//    }
//
//    JPanel buttonPanel = new JPanel();
//
//    void layoutWindow(Container conainer)
//    {
//        console = new ConsoleTextPane(this);
//        console.setPrompt();
//        console.setDragEnabled(false);
//        JScrollPane consolePane = new JScrollPane(console);
//
//        editButton = setButton(GT._("Editor"));
//        checkButton = setButton(GT._("Check"));
//        topButton = setButton(GT._("Top"));
//        stepButton = setButton(GT._("Step"));
//
//        varButton = setButton(GT._("Variables"));
//        clearButton = setButton(GT._("Clear"));
//        haltButton = setButton(GT._("Halt"));
//
//        historyButton = setButton(GT._("history"));
//        stateButton = setButton(GT._("state"));
//
//        helpButton = setButton(GT._("help"));
//        closeButton = setButton(GT._("close"));
//        undoButton = setButton(GT._("undo"));
//        redoButton = setButton(GT._("redo"));
//
//        undoButton.setEnabled(false);
//        redoButton.setEnabled(false);
//
//        JPanel buttJPanelWrapper = new JPanel();
//        buttJPanelWrapper.setLayout(new BorderLayout());
//        buttJPanelWrapper.add(buttonPanel, BorderLayout.CENTER);
//
//        JSplitPane splitPane = new JSplitPane(
//                JSplitPane.VERTICAL_SPLIT, consolePane, buttJPanelWrapper);
//        consolePane.setMinimumSize(new Dimension(200,200));
//        consolePane.setPreferredSize(new Dimension(400, 400));
//        buttJPanelWrapper.setMinimumSize(new Dimension(50, 50));
//        buttJPanelWrapper.setMaximumSize(new Dimension(100, 100));
//        buttJPanelWrapper.setPreferredSize(new Dimension(200, 200));
//
//        splitPane.setDividerSize(0);
//        splitPane.setResizeWeight(0.95);
//        conainer.add(splitPane);
//    }
//
//
//    public void sendConsoleEcho(String strEcho)
//    {
//        if (strEcho != null)
//        {
//            console.outputEcho(strEcho);
//        }
//        setError(false);
//    }
//
//    boolean isError = false;
//    private void setError(boolean TF)
//    {
//        isError = TF;
//    }
//
//    @Override
//    public void sendConsoleMessage(String strStatus)
//    {
//        if (strStatus == null)
//        {
//            console.clearContent(null);
//            console.outputStatus("");
//
//        }
//        else if (strStatus.indexOf("ERROR:") >= 0)
//        {
//            console.outputError(strStatus);
//        }
//        else
//        {
//            console.outputStatus(strStatus);
//            isError = false;
//        }
//    }
//
//    public void enterPressed()
//    {
//        executeCommandThread(null);
//    }
//
//    void executeCommandThread(String strCommand)
//    {
//        if (strCommand == null)
//        {
//            strCommand = console.getCommandString().trim();
//        }
//        if (strCommand.equalsIgnoreCase("undoCmd"))
//        {
//            undoRedo(false);
//            console.appendNewline();
//            console.setPrompt();
//            return;
//        }
//        else if (strCommand.equalsIgnoreCase("redoCmd"))
//        {
//            undoRedo(false);
//            console.appendNewline();
//            console.setPrompt();
//            return;
//        }
//        else if (strCommand.equalsIgnoreCase("exitJmol"))
//        {
//            System.exit(0);
//        }
//        else if (strCommand.length() == 0 )
//        {
//            strCommand = "!resume";
//        }
//
//        if (strCommand.length() > 0)
//        {
//            execThread = new ExecuteCommandThread(strCommand);
//            execThread.start();
//        }
//
//    }
//
//    private static int MAXUNDO = 10;
//    private String[] undoStack = new String[MAXUNDO +1];
//    private int undoPointer = 0;
//    private boolean undoSaved = false;
//
//    public void zap(){}
//
//
//
//     void undoRedo(boolean isRedo) {
//        if (undoButton == null)
//        {
//            return;
//        }
//        if(!viewer.getBooleanProperty("undo")
//                || !viewer.getBooleanProperty("preserveState"))
//        {
//            return;
//        }
//        int ptr = undoPointer + (isRedo ? 1:-1);
//
//        if (!undoSaved)
//        {
//            undoSave(false);
//        }
//        if (ptr > MAXUNDO || ptr < 0)
//        {return;}
//        String state = undoStack[ptr];
//        if (state != null)
//        {
//            state += CommandHistory.NOHISTORYATALL_FLAG;
//            setError(false);
//            viewer.evalStringQuiet(state);
//            undoPointer = ptr;
//        }
//
//        undoSetEnabled();
//
//    }
//
//     private void undoSave(boolean incrementPtr)
//     {
//         if (undoButton == null) {return;}
//
//         if(!viewer.getBooleanProperty("undo")
//                 || !viewer.getBooleanProperty("preserveState"))
//         {  return;}
//         for (int i = undoPointer + 1; i <= MAXUNDO; i++)
//         {
//             undoStack[i] = null;
//         }
//
//         Logger.startTimer();
//         undoStack[undoPointer] = (String) viewer.getProperty
//                 ("readable", "stateInfo", null);
//         if (incrementPtr && undoPointer == MAXUNDO)
//         {
//             for (int i =1; i <= MAXUNDO; i++)
//             {
//                 undoStack[i-1] = undoStack[i];
//             }
//             undoStack[MAXUNDO] = null;
//         }
//         else if (incrementPtr)
//         {
//             undoPointer ++;
//         }
//
//         if (Logger.checkTimer(null) > 1000)
//         {
//             Logger.info("command processing slow; undo disabled");
//
//         }
//         else {
//         undoSetEnabled();
//         }
//         undoSaved = true;
//
//     }
//
//    void undoSetEnabled() {
//        if (undoButton == null)
//        {
//            return;
//        }
//        undoButton.setEnabled(undoPointer > 0 && undoStack[undoPointer -1]
//                != null);
//        redoButton.setEnabled(undoPointer < MAXUNDO
//                && undoStack[undoPointer -1] != null);
//
//    }
//
//    void executeCommand(String strCommand)
//    {
//        boolean doWait;
//        console.appendNewline();
//        console.setPrompt();
//        if (strCommand.length() == 0)
//        {
//            console.grabFocus();
//            return;
//        }
//        if (strCommand.contentEquals(0) != '!'
//                && viewer.getBooleanProperty("executionPaused"))
//        {
//            strCommand = "!" + strCommand;
//        }
//        if (strCommand.charAt(0) != '!' && ! isError)
//        {
//            undoSave(true);
//        }
//        setError(false);
//        undoSaved = false;
//
//        String strErrorMessage = null;
//        doWait = (strCommand.indexOf("WAITTEST ") == 0);
//        if (doWait)
//        {
//            List info = (List) viewer.scriptWaitStatus(
//                    strCommand.substring(5),
//                    "+fileLoaded, +scriptStarted, +scirptStatus, +scriptEcho, +scriptTerminated");
//            for (int i = 0; i < info.size(); i++)
//            {
//                List statuRecordSet = (List)info.get(i);
//                for (int j = 0 ; j < statuRecordSet.size(); j++)
//                {
//                    List statuRecord = (List)statuRecordSet.get(j);
//                    Logger.info("msg#=" + statuRecord.get(0) + statuRecord.get(1)
//                            + " intInfo=" + statuRecord.get(2) + " stringInfo="
//                             + statuRecord.get(3));
//                }
//            }
//            console.appendNewline();
//        }
//        else
//        {
//            boolean isScriptExecuting = viewer.isScriptExecuting();
//            strErrorMessage = "";
//            String str = strCommand;
//            boolean isInterrupt = (str.charAt(0) == '!');
//            if (isInterrupt)
//            {
//                str = str.substring(1);
//            }
//            if (viewer.checkHalt(str, isInterrupt))
//            {
//                strErrorMessage = (isScriptExecuting ? "script execution halted with"
//                        + strCommand : "no script was executing");
//                if (strErrorMessage.length() > 0)
//                {
//                    console.outputError(strErrorMessage);
//                }
//                else
//                {
//
//                }
//            }
//        }
//
//    }
//
//
//
//    class ConsoleTextPane extends JTextPane
//    {
//        private ConsoleDocument consoleDoc;
//        private EnterListener enterListener;
//
//        boolean checking = false;
//
//         ConsoleTextPane(PscAppConsole pscAppConsole )
//        {
//             super(new ConsoleDocument());
//             consoleDoc = (ConsoleDocument)getDocument();
//             consoleDoc.setConsoleTextPane(this);
//
//        }
//
//        public void outputEcho(String strEcho) {
//            consoleDoc.outputEcho(strEcho);
//        }
//
//        public void setPrompt() {
//            consoleDoc.setPrompt();
//        }
//
//        public void String getCommandString()
//        {
//            String cmd = consoleDoc.getCommandString();
//            return cmd;
//        }
//
//        public void appendNewline() {
//            consoleDoc.appendNewLine();
//        }
//
//        public void outputError(String strError) {
//            consoleDoc.outputError(strError);
//        }
//
//        public void clearContent(String  text) {
//            consoleDoc.clearContent();
//            if (text != null)
//            {
//                consoleDoc.outputEcho(text);
//            }
//            setPrompt();
//        }
//
//        void outputStatus(String strStatus) {
//            outputBefortPrompt(strStatus, attStatus);
//        }
//
//    }
//
//
//    public void setSize(int width, int height)
//    {
//        super.setSize(width,height);
//    }
//
//    class ExecuteCommandThread extends Thread
//    {
//        String strCommand;
//
//        ExecuteCommandThread(String command) {
//            strCommand = command;
//            this.setName("appConsoleExecuteCommandThread");
//        }
//
//        public void run()
//        {
//            try
//            {
//                while (console.checking)
//                {
//                    try
//                    {
//                        Thread.sleep(1000);
//
//                    }
//                    catch(Exception e)
//                    {
//                        break;
//                    }
//                }
//                executeCommand(strCommand);
//            }
//            catch(Exception ie)
//            {
//                Logger.error("execution command interrupted!", ie);
//            }
//        }
//
//    }
//
//    ExecuteCommandThread execThread;
//
//    protected void execute(String strCommand)
//    {
//        executeCommandThread(strCommand);
//    }
//
//    void executeCommandAsThread(String strCommand)
//    {
//        if (strCommand == null)
//        {
//            strCommand = console.getCommandString().trim();
//        }
//        if (strCommand.equalsIgnoreCase("undoCmd"))
//        {
//            undoRedo(false);
//            console.appendNewline();
//            console.setPrompt();
//            return;
//        }
//    }
//
//    void setConsoleTextPane(ConsoleTextPane consoleTextPane)
//    {
//        this.consoleTextPane = consoleTextPane;
//    }
//
//    class ConsoleDocument extends DefaultStyledDocument
//    {
//        private ConsoleTextPane consoleTextPane;
//
//        SimpleAttributeSet attError;
//        SimpleAttributeSet attEcho;
//        SimpleAttributeSet attPrompt;
//        SimpleAttributeSet attUserInput;
//        SimpleAttributeSet attStatus;
//        private Position positionBeforePrompt;
//        private Position positionAfterPrompt;
//
//        public ConsoleDocument() {
//            super();
//
//            attError = new SimpleAttributeSet();
//            StyleConstants.setForeground(attError, Color.red);
//
//            attEcho = new SimpleAttributeSet();
//            StyleConstants.setForeground(attEcho, Color.blue);
//            StyleConstants.setBold(attEcho, true);
//
//            attPrompt = new SimpleAttributeSet();
//            StyleConstants.setForeground(attPrompt, Color.magenta);
//
//            attUserInput = new SimpleAttributeSet();
//            StyleConstants.setForeground(attUserInput, Color.black);
//
//            attStatus = new SimpleAttributeSet();
//            StyleConstants.setForeground(attStatus, Color.black);
//            StyleConstants.setBold(attStatus, true);
//        }
//
//         void appendNewLine() {
//            try
//            {
//                super.insertString(getLength(), "\n", attUserInput);
//                consoleTextPane.setCaretPosition(getLength());
//            }
//            catch (BadLocationException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        void outputError(String strError) {
//            outputBeforePrompt(strError, attError);
//        }
//
//        void outputBeforePrompt(String str, SimpleAttributeSet attribute)
//        {
//            try
//            {
//                int pt  =  consoleTextPane.getCaretPosition();
//                Position caretPosition = creatPosition(pt);
//                pt = positionBeforePrompt.getOffset();
//                super.insertString(pt, str + "\n", attribute);
//                offsetAfterPrompt += str.length() +1;
//                positionBeforePrompt = createPosition(offsetAfterPrompt -2);
//                positionAfterPrompt = createPosition(offsetAfterPrompt -1);
//
//                pt = caretPosition.getOffset();
//                consoleTextPane.setCaretPosition(pt);
//
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//                consoleTextPane.setCaretPosition(getLength());
//            }
//        }
//
//        public void insertString(int offs, String str, AttributeSet a)
//        {
//
//        }
//
//        void outputErrorForeground(String strError)
//        {
//            try
//            {
//                super.insertString(getLength(), strError + "\n", attError);
//                consoleTextPane.setCaretPosition(getLength());
//            }
//            catch(BadLocationException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//
//        void outputEcho(String strEcho)
//        {
//            outputBeforePrompt(strEcho, attEcho);
//
//        }
//
//        void outputStatus(String strStatus)
//        {
//            outputBeforePrompt(strStatus, attStatus);
//        }
//
//        private int offsetAfterPrompt;
//
//        void clearContent() {
//            try
//            {
//                super.remove(0, getLength());
//            }
//            catch(BadLocationException exception)
//            {
//                Logger.error("Could not clear script window content", exception);
//            }
//        }
//    }
//
//
//    interface  EnterListener
//    {
//        public void enterPressed();
//    }



    
    
}
