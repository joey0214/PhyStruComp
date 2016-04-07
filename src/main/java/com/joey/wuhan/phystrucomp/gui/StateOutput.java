/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.gui;

/**
 *
 * @author zhongxf
 */
// to tell user the statement of program runing.
public class StateOutput 
{
    public StateOutput() 
    {
        PSCgui.outpuTextArea.setEditable(false);
        PSCgui.outpuTextArea.setLineWrap(true);
        PSCgui.outpuTextArea.setRows(29);
        PSCgui.outpuTextArea.append("PSC:> welcome to PSC! \n" );
        PSCgui.outpuTextArea.append("PSC:> \n");
    } 

    public void addMessgae(String hello) 
    {
        PSCgui.outpuTextArea.append("PSC:> "+hello+ "\n");
        PSCgui.outpuTextArea.append("PSC:> \n");
    }
}
