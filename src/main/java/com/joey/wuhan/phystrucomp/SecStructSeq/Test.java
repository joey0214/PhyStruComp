/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.SecStructSeq;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.biojava.bio.gui.sequence.SecondaryStructureFeatureRenderer;
import com.joey.wuhan.phystrucomp.MUSTANG.LocalMUSTANG;

/**
 *
 * @author zhongxf
 */
public class Test 
{
    public static void main(String[] args ) throws FileNotFoundException, IOException
    {
        LocalMUSTANG mustang = new LocalMUSTANG();
        mustang.exeractSuperoimposed();
    }
}
