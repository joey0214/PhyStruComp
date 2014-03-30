/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.SVDSuperimposer;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author zhongxf
 */
public class CalculationVariation 
{
    private Structure[] structures;
    private double[][] rmsdMatrix;
    private String[] labels;

    public void readIN(Structure[] structureIN) throws StructureException
    {
        this.structures = structureIN;
        calculationAll(structures);
    }
    
    public  CalculationVariation(Structure[] structs ) throws StructureException
    {
        this.structures = structs;
        calculationAll(structures);
        
    }
    
    public double[][] getRmsdMatrix()
    {
        return rmsdMatrix;
    }
    
    public String[] getLabels()
    {
        labels = getStructureNames();
        return labels;
    }

    private void calculationAll(Structure[] structures) throws StructureException 
    {
        
        int length = structures.length;
        rmsdMatrix = new double[length][length];
        for (int i=0; i < length-1; i ++)
        {
            for (int j =0; j < length; j ++)
            {
                if (i == j)
                {
                    rmsdMatrix[i][j] = 0;
                }
                else 
                {
                    Structure structure01 = structures[i];
                    Structure structure02 = structures[i + 1];

                    List<Atom> atomList1 = getAllAtom(structure01);
                    List<Atom> atomList2 = getAllAtom(structure02);

                    Atom[] atomSet1 = atomList1.toArray(new Atom[atomList1.size()]);
                    Atom[] atomSet2 = atomList2.toArray(new Atom[atomList2.size()]);

                    SVDSuperimposer superimposer = new SVDSuperimposer(atomSet1, atomSet2);
                    Calc.rotate(structure02, superimposer.getRotation());
                    Calc.shift(structure02, superimposer.getTranslation());

                    double rmsd = SVDSuperimposer.getRMS(atomSet1, atomSet2);
                    rmsdMatrix[i][j] = rmsd;
                }
            }
        }
    }
    
    private void calculationSelection(Structure[] structures, int srart, int end) throws StructureException 
    {
        //TODO
        int length = structures.length;
        rmsdMatrix = new double[length][length];
        for (int i=0; i < length; i ++)
        {
            for (int j =0; j < length; j ++)
            {
                if (i == j)
                {
                    rmsdMatrix[i][j] = 0;
                }
                else 
                {
                    Structure structure01 = structures[i];
                    Structure structure02 = structures[i + 1];

                    List<Atom> atomList1 = getAllAtom(structure01);
                    List<Atom> atomList2 = getAllAtom(structure02);

                    Atom[] atomSet1 = atomList1.toArray(new Atom[atomList1.size()]);
                    Atom[] atomSet2 = atomList2.toArray(new Atom[atomList2.size()]);

                    SVDSuperimposer superimposer = new SVDSuperimposer(atomSet1, atomSet2);
                    Calc.rotate(structure02, superimposer.getRotation());
                    Calc.shift(structure02, superimposer.getTranslation());

                    double rmsd = SVDSuperimposer.getRMS(atomSet1, atomSet2);
                    rmsdMatrix[i][j] = rmsd;
                }
            }
        }
    }
    

    private List<Atom> getAllAtom(Structure structure01) 
    {
        List<Atom> list = new ArrayList<Atom>();
        List<Chain> chains = structure01.getChains();
        
        if (chains.size() == 1)
        {
            for (Group group: chains.get(0).getAtomGroups())
            {
                try
                {
                    for (int j =0; j <group.size(); j++)
                    {
                        Atom atom = group.getAtom(j);
                        list.add(atom);
                    }                   
                }
                catch(StructureException se)
                {
                    System.out.println("ERROR: in extract atom from structure. \n"+ se.getMessage());
                }
            }
        }
        
        if (chains.isEmpty())
        {
            System.out.println("ERROR: chains extracted fron structure is empty. \n");
        }
        
        else 
        {
            for (int j =0; j < chains.size(); j++)
            {
                Chain chain = chains.get(j);
                for (Group group : chain.getAtomGroups()) 
                {
                    try 
                    {
                        Atom atom = group.getAtom(j);
                        list.add(atom);
                    } 
                    catch (StructureException se) 
                    {
                         System.out.println("ERROR: in extract atom from structure. \n"+ se.getMessage());
                    }
                }
                
            }
        }
        return list;
    }
    
    private List<Atom> getAlphCarbonAtom(Structure struct)
    {
        List<Atom> list = new ArrayList<Atom>();
        List<Chain> chains = struct.getChains();
        
        if (chains.isEmpty())
        {
            System.out.println("ERROR: chains extracted fron structure is empty. \n");
        }
        
        else 
        {
            for (int j =0; j < chains.size(); j++)
            {
                Chain chain = chains.get(j);
                for (Group group : chain.getAtomGroups()) 
                {
                    try 
                    {
                        Atom atom = group.getAtom("C");
                        list.add(atom);
                    } 
                    catch (StructureException se) 
                    {
                         System.out.println("ERROR: in extract atom from structure. \n"+ se.getMessage());
                    }
                }                
            }
        }
        return list;
    }

    private String[] getStructureNames() 
    {
        String[] names = new String[structures.length];
        for (int i =0; i < structures.length; i ++)
        {
            names[i] = structures[i].getName();
            
        }
        return names;
    }
}
