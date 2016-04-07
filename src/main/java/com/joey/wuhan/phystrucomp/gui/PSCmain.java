/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.gui;

import com.joey.wuhan.phystrucomp.cmd.PSCcmd;

/**
 *
 * @author zhongxf
 */
public class PSCmain 
{
    public static void main(String args[]) 
    {
        if (args.length == 0)
        {
            PSCgui psc = new PSCgui();
        }
        else if (args[0].equals("GTK"))
        {
            PSCgui psc = new PSCgui();
        }
        else if (args[0].equals("CMD"))
        {
            //TODO to run program under terminal.
            PSCcmd psc = new PSCcmd();
        }
    }
}
