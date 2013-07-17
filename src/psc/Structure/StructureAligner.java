/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.StructureTools;
import org.biojava.bio.structure.align.ClusterAltAligs;
import org.biojava.bio.structure.align.StructureAlignment;
import org.biojava.bio.structure.align.StructureAlignmentFactory;
import org.biojava.bio.structure.align.StructurePairAligner;
import org.biojava.bio.structure.align.ce.CeMain;
import org.biojava.bio.structure.align.ce.CeParameters;
import org.biojava.bio.structure.align.fatcat.FatCatRigid;
import org.biojava.bio.structure.align.fatcat.calc.FatCatParameters;
import org.biojava.bio.structure.align.model.AFPChain;
import org.biojava.bio.structure.align.model.AfpChainWriter;
import org.biojava.bio.structure.align.pairwise.AlternativeAlignment;
import org.biojava.bio.structure.io.PDBFileReader;
import psc.MUSTANG.LocalMUSTANG;
import psc.gui.PSCgui;

/**
 *
 * @author zhongxf
 */
public class StructureAligner 
{
    private Structure[] structures;
    private int methodType = -1;
    private Structure[] alignedStruct ;
    
    public void setStructures(Structure[] structs)
    {
        this.structures = structs;
    }
    
    public void setAlignerMethod(int methType)
    {
        this.methodType = methType;
    }
    
    public Structure[] getAlignedStructure()
    {
        return alignedStruct;
    }
    
    public void alignRun() throws IOException
    {
        switch (methodType)
        {
            case -1:
                System.out.println("do not choose a method");
                break;
            case 0:                 //Default
                defaultAlign();
                break;
            case 1:                 //CE
                ceAlign();
                break;
            case 2:                 //FATCAT
                fatcatAlign();
                break;
            case 3:                 //PairAligner
                pairAlign();
                break;
            case 4:                 //DefaultAdvanced
                defaultAlignaAvanced();
                break;
            case 5:                 //MUSTANG
                localMustang();
                break;
        }
    }

    private void defaultAlign() 
    {
        alignedStruct = new Structure[structures.length];
        String workpath = System.getProperty("user.dir");
        for (int i = 0; i < structures.length; i++) 
        {
            try 
            {
                StructurePairAligner scPairAligner = new StructurePairAligner();
                scPairAligner.align(structures[i], structures[0]);

                System.out.println("Aligning:  " + structures[i].getName() + "---" + structures[0].getName());
                AlternativeAlignment[] aligs = scPairAligner.getAlignments();

                ClusterAltAligs.cluster(aligs);
                for (int k = 0; k < aligs.length; k++) 
                {
                    AlternativeAlignment aa = aligs[i];
                    System.out.println(aa);
                }
                String pdbstr = null;
                if (aligs.length > 0) 
                {
                    AlternativeAlignment aal = aligs[0];
                    pdbstr = aal.toPDB(structures[i], structures[0]);
                    String outputFileName = workpath + "test_" + structures[i].getName() + ".pdb";

                    System.out.println("writing alignment to " + outputFileName);
                    FileOutputStream out = new FileOutputStream(outputFileName);
                    PrintStream p = new PrintStream(out);
                    p.println(pdbstr);
                    p.close();
                    out.close();

                    PDBFileReader pdbFileReader = new PDBFileReader();
                    alignedStruct[i] = pdbFileReader.getStructure(outputFileName);
                }
            } 
            catch (FileNotFoundException e) 
            {
                // TODO Auto-generated catch block
            } 
            catch (IOException e) 
            {
                // TODO Auto-generated catch block
            } 
            catch (StructureException e) 
            {
                //todo
            }
        }
        
    }

    private void ceAlign() 
    {
        try 
        {
            StructureAlignment algorithm = StructureAlignmentFactory.getAlgorithm(CeMain.algorithmName);
            for (int i = 0; i < structures.length; i++) 
            {
                Structure struct1, struct2;
                if (i == structures.length - 1) 
                {
                    struct1 = structures[i];
                    struct2 = structures[0];
                } 
                else 
                {
                    struct1 = structures[i];
                    struct2 = structures[i + 1];
                }
                Atom[] ca1 = StructureTools.getAtomCAArray(struct1);
                Atom[] ca2 = StructureTools.getAtomCAArray(struct2);
                CeParameters params = new CeParameters();
                params.setShowAFPRanges(true);
                params.setMaxGapSize(-1);

                AFPChain afpChain = algorithm.align(ca2, ca1, params);
                afpChain.setName1(struct1.getName());
                afpChain.setName2(struct2.getName());

                System.out.println("AfpChainWriter.toWebSiteDisplay(afpChain, ca1, ca2 \n" + AfpChainWriter.toWebSiteDisplay(afpChain, ca1, ca2));
                System.out.println("afpChain.toRotMat() \n" + afpChain.toRotMat());
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return;
        }
    }

    private void fatcatAlign() 
    {
        try 
        {
            StructureAlignment algorithm = StructureAlignmentFactory.getAlgorithm(FatCatRigid.algorithmName);
            for (int i = 0; i < structures.length; i++) 
            {
                Structure struct1, struct2;
                if (i == structures.length - 1) 
                {
                    struct1 = structures[i];
                    struct2 = structures[0];
                } 
                else 
                {
                    struct1 = structures[i];
                    struct2 = structures[i + 1];
                }

                Atom[] ca1 = StructureTools.getAtomCAArray(struct1);
                Atom[] ca2 = StructureTools.getAtomCAArray(struct2);
                FatCatParameters params = new FatCatParameters();
                AFPChain afpChain = algorithm.align(ca2, ca1, params);
                afpChain.setName1(struct1.getName());
                afpChain.setName2(struct2.getName());
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return;
        }
    }

    private void pairAlign() 
    {
        alignedStruct = new Structure[structures.length];
        try 
        {
            StructurePairAligner aligner = new StructurePairAligner();
            for (int i = 0; i < structures.length; i++) 
            {
                Structure struct1, struct2;
                if (i == structures.length - 1) 
                {
                    struct1 = structures[i];
                    struct2 = structures[0];
                } 
                else 
                {
                    struct1 = structures[i];
                    struct2 = structures[i + 1];

                    Group gp1 = (Group) struct1.getChain(0).getAtomGroup(21).clone();
                    System.out.println("gp1.toString()" + gp1.toString());
                }
                Atom[] ca1 = StructureTools.getAtomCAArray(struct1);
                Atom[] ca2 = StructureTools.getAtomCAArray(struct2);
                List<Chain> chains1 = struct1.getChains();
                List<Chain> chains2 = struct2.getChains();
                for (int ii = 0; ii < chains1.size(); ii++) 
                {
                    Chain chain1 = chains1.get(ii);
                }
                aligner.align(struct1, struct2); //params !!!
                AlternativeAlignment[] aligs = aligner.getAlignments();
                AlternativeAlignment a = aligs[0];
                System.out.println("AlternativeAlignment a = aligs[0] \n" + a);
                Structure artificial = a.getAlignedStructure(struct1, struct2);
                PSCgui.jmolPanel.setStructure(artificial);
                // color the two structures
                PSCgui.jmolPanel.executeCmd("select *; backbone 0.4; wireframe off; spacefill off; "
                        + "select not protein and not solvent; spacefill on;");
                PSCgui.jmolPanel.executeCmd("select *" + "/1 ; color red; model 2; ");
                // now color the equivalent residues ...
                String[] pdb1 = a.getPDBresnum1();
                for (String res : pdb1) 
                {
                    PSCgui.jmolPanel.executeCmd("select " + res + "/1 ; backbone 0.6; color white;");
                }
                PSCgui.jmolPanel.executeCmd("select *" + "/2; color blue; model 2;");
                String[] pdb2 = a.getPDBresnum2();
                for (String res : pdb2) 
                {
                    PSCgui.jmolPanel.executeCmd("select " + res + "/2 ; backbone 0.6; color yellow;");
                }
                // now show both models again.
                PSCgui.jmolPanel.executeCmd("model 0;");
                alignedStruct[i] = artificial;
            }
            PSCgui.jmolPanel.setMultipleStructure(alignedStruct);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return;
        }
    }

    private void defaultAlignaAvanced() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void localMustang() throws IOException 
    {
        LocalMUSTANG mustang = new LocalMUSTANG();
        mustang.runLocalMUSTANG(PSCgui.displayInGUI2.getFilesName(), PSCgui.displayInGUI2.getFilePath());
        mustang.exeractSuperoimposed();
        //PSCgui.jmolPanel.setStructure(mustang.getSuperimposed());
        this.structures = mustang.getAlignedStructure();
        PSCgui.jmolPanel.setMultipleStructure(mustang.getAlignedStructure());
    }
}
