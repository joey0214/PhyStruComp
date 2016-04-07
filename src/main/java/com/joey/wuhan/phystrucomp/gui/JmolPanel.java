/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import org.biojava.nbio.structure.Structure;
import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolAdapter;
import org.jmol.api.JmolViewer;

/**
 *
 * @author zhongxf
 */
public class JmolPanel extends JPanel {

    private static final long serialVersionUID = -3661941083797644242L;
    JmolViewer viewer;
    JmolAdapter adapter;
    final Dimension currentSize = new Dimension();
    final Rectangle rectClip = new Rectangle();

    public JmolPanel() {
        adapter = new SmarterJmolAdapter();
        viewer = JmolViewer.allocateViewer(this, adapter);
//            viewer.setAppletContext("",null,null,"");
//            viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter(), 
//          null, null, null, null, null); 
    }

    public JmolViewer getViewer() {
        return viewer;
    }

    public void executeCmd(String rasmolScript) {
        viewer.evalString(rasmolScript);
    }

    @Override
    public void paint(Graphics g) {

        if (rectClip == null) {
            System.out.println("*****************************************");
            System.out.println("rectClip is null!!");
            System.out.println("*****************************************");
        }

        if (g == null) {
            System.out.println("*****************************************");
            System.out.println("g is null!!");
            System.out.println("*****************************************");
        }

        getSize(currentSize);
        g.getClipBounds(rectClip);  ////java.lang.NullPointerException Nov05
        viewer.renderScreenImage(g, currentSize, rectClip);
    }

    public void setStructure(Structure pdb_structure) {
        try {
            JmolViewer viewer = PSCgui.jmolPanel.getViewer();
            String pdb = pdb_structure.toPDB();
            viewer.openStringInline(pdb);
            viewer.evalString("select *; spacefill off; wireframe off; "
                    + "backbone 0.4;");
            viewer.evalString("color chain;");
            this.viewer = viewer;
        } catch (Exception e) {
        }
    }

//    public void setMultipleStructure(Structure[] pdbStructure) {
//        try {
//            JmolViewer viewer = PSC.jmolPanel.getViewer();
//            String pdbs = null;
//            for (int i = 0; i < pdbStructure.length; i++) {
//                String pdb = pdbStructure[i].toPDB();
//                pdbs += pdb;
//            }
//            //String pdb = pdb_structure.toPDB();
//            viewer.openStringInline(pdbs);
//            viewer.evalString("select *; spacefill off; wireframe off; "
//                    + "backbone 0.4;");
//            viewer.evalString("color chain;");
////                this.viewer = viewer;
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    public void setMultipleStructure(Structure[] pdbStructure) {
        try {
            JmolViewer viewer = PSCgui.jmolPanel.getViewer();
            String pdbs = "";
            for (int i = 0; i < pdbStructure.length; i++) {
                String pdb = pdbStructure[i].toPDB();
                pdbs += pdb;
            }
            //String pdb = pdb_structure.toPDB();
            viewer.openStringInline(pdbs);
            viewer.evalString("select *; spacefill off; wireframe off; "
                    + "backbone 0.4;");
            viewer.evalString("color chain;");
//                this.viewer = viewer;
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
