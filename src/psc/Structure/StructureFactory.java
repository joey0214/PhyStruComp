/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.biojava.bio.seq.ProteinTools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureTools;
import org.biojava.bio.structure.io.PDBFileReader;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.ProteinSequence;
import sun.swing.FilePane;

/**
 *
 * @author zhongxf
 */
public class StructureFactory 
{
    private Structure[] structureArray ;
    private String[] structNames;
    private ProteinSequence[] proseqs ;
    private File[] pdbFiles;
    double[][] rmsdMatrix; // hold the rmsd of structures
    String pwdString = System.getProperty("user.dir"); //hold current work directory
    private String outputPath = System.getProperty("user.dir");
    
    public void setStructure(Structure[] structArray)
    {
        this.structureArray = structArray;
    }
    
    public void setInPDBfiles(File[] pdbFiles) throws IOException
    {
        this.pdbFiles = pdbFiles;
        Structure[] inStructArray = getStructure(pdbFiles);
        setStructure(inStructArray);
        extractStructureNames();
    }
    
    public Structure[] getStructure()
    {
        return structureArray;
    }
    public void appendFiles(File[] appendFiles) throws IOException
    {
        File[] checkedFiles = checkRepeat(pdbFiles, appendFiles);
        
        int oldLen = pdbFiles.length;
        int renewLen = checkedFiles.length +oldLen;
        File[] renewFiles = new File[renewLen];
        System.arraycopy(pdbFiles, 0, renewFiles, 0, oldLen);
        System.arraycopy(checkedFiles, 0, renewFiles, oldLen, checkedFiles.length);
        this.pdbFiles = renewFiles;
        Structure[] inStructArray = getStructure(renewFiles);
        setStructure(inStructArray);
        extractStructureNames();
    }
    
    public void appendStructure(Structure[] appendStruct)
    {
        int renewLen = structureArray.length + appendStruct.length;
        Structure[] renewStructures = new Structure[renewLen];
        System.arraycopy(structureArray, 0, renewStructures, 0, structureArray.length);
        System.arraycopy(appendStruct, 0, renewStructures, structureArray.length, appendStruct.length);
        this.structureArray = renewStructures;
    }
    
    public String[] getStructureName()
    {
        return structNames;
    }
    
    private void extractStructureNames()
    {
        structNames = new String[pdbFiles.length];
        for (int i=0; i < pdbFiles.length; i ++)
        {
            String[] tmpstrings = (pdbFiles[i].getName().toString()).split("\\.");
            structNames[i] = tmpstrings[0];
        }
    }
    
    public ProteinSequence[] getProSeq()
    {
        ProteinSequence[] proseqs = new ProteinSequence[structureArray.length];
        //String[] structnames = getStructureName();
        for (int i = 0; i < structureArray.length; i++) 
        {
            Structure tmpStruct = structureArray[i];
            String aminoChainSeq = "";
            for (Chain chain : tmpStruct.getChains()) 
            {
                aminoChainSeq += chain.getAtomSequence();
            }
            String aminoSeq = aminoChainSeq;
            proseqs[i] = new ProteinSequence(aminoSeq);
            AccessionID tmpID = new AccessionID(structNames[i]);
            proseqs[i].setAccession(tmpID);         
        }
        return proseqs;
    }
    
    public double[][] getRmsdMatrix()
    {
        calculateRmsd(structureArray);
        return rmsdMatrix;
    }

    public double[] calculateRMSD(Structure targetStruct, Structure[] sourceStructArray )
    {
        double[] rmsdArray = new double[sourceStructArray.length];
        Atom[] targetAtoms = StructureTools.getAtomCAArray(targetStruct);
        
        for (int i=0; i < sourceStructArray.length; i++)
        {
            Atom[] tmpSourceAtoms = StructureTools.getAtomCAArray(sourceStructArray[i]);
            if ((targetAtoms.length) == (tmpSourceAtoms.length))
            {
                double tmpsum = -1;
                for (int j = 0; j < targetAtoms.length; j++) 
                {
                    try 
                    {
                        double[] coords1 = null;
                        double[] coords2 = null;;
                        coords1 = targetAtoms[j].getCoords();
                        coords2 = tmpSourceAtoms[j].getCoords();
                        double atomRmsd = (Math.pow((coords1[0] - coords2[0]), 2)
                                + Math.pow((coords1[1] - coords2[1]), 2)
                                + Math.pow((coords1[2] - coords2[2]), 2));
                        tmpsum += atomRmsd;
                    } 
                    catch (Exception ex) 
                    {
                        ex.printStackTrace();
                        rmsdArray[i] = -1;///????
                    }
                }
                rmsdArray[i] = Math.sqrt(tmpsum / targetAtoms.length);
            }
            else 
            {
                rmsdArray[i] = -1;
            }
        }
        return rmsdArray;
    }
    
    public double[] calculateRMSD(int index, Structure[] structArray)
    {
        double[] rmsdArray = new double[structArray.length];
        if (index > structArray.length)
        {
            System.out.println("The index is out of boundary!");
        }
        else 
        {
            Structure tmpStruct = structArray[index];
            rmsdArray = calculateRMSD(tmpStruct, structArray);
        }
        return rmsdArray;
    }
    
    private void calculateRmsd(int index, Structure[] structArray) 
    {
        if (index > (structArray.length))
        {
            System.out.println("The index is out of boundary!");
        }
        else 
        {
            double[] rmsdArray = new double[structArray.length];
            Atom[][] atomCAArray = new Atom[structArray.length][];
            for (int i = 0; i < structArray.length; i++) 
            {
                atomCAArray[i] = StructureTools.getAtomCAArray(structArray[i]);
            }
            Atom[] indexAtomCAArray = StructureTools.getAtomCAArray(structArray[index]);
            for (int j =0; j < atomCAArray.length; j++)
            {
                pairwiseRmsdCalcul(indexAtomCAArray, atomCAArray[j]);
            }
        } 
    }
    private double[][] calculateRmsd(Structure[] structArray) 
    {
        double[][] RmsdArray = new double[structArray.length][structArray.length];      
        Atom[][]  atomCAArray = new Atom[structArray.length][];
        //initial
        for (int i =0; i < structArray.length; i++)
        {
            atomCAArray[i] = StructureTools.getAtomCAArray(structArray[i]);
        }
        
        int atomCAArrayLength = atomCAArray.length;
        
        for(int j=0; j< atomCAArrayLength;j++)
        {
            int atomLen = atomCAArray[j].length;           
            double sumTmp =0;           
            for (int k = 0; k < atomCAArrayLength; k++)
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
                                coords1 = atomCAArray[j][m].getCoords();
                                coords2 = atomCAArray[k][m].getCoords();                               
                                double atomRmsd = (Math.pow((coords1[0] - coords2[0]), 2)
                                        + Math.pow((coords1[1] - coords2[1]), 2)
                                        + Math.pow((coords1[2] - coords2[2]), 2));

                                sumTmp += atomRmsd;
                            } 
                            catch (Exception ex) 
                            {
                                ex.printStackTrace();
                            }                           
                        }
                         RmsdArray[j][k] = Math.sqrt( sumTmp / atomLen);
                    }
                }               
            }
        }  
        return RmsdArray;
    }

    public Structure[] readAlignedStruct(File[] files) throws FileNotFoundException, IOException
    {
        ArrayList struArrayList = new ArrayList();
        for (int i =0; i<files.length; i++)
        {
            Structure[] tmpStructures =readAlignedStruct(files[i]);
            for (int j=0; j<tmpStructures.length; j++)
            {
                struArrayList.add(tmpStructures[j]);
            }
        }
        //ArrayList convert to Array
        Structure[] struArray = new Structure[struArrayList.size()];
        for (int k=0; k<struArrayList.size(); k++)
        {
            struArray[k] = (Structure)struArrayList.get(k);
        }
        return struArray;
    }
    
    public Structure[] readAlignedStruct(File file) throws FileNotFoundException, IOException 
    {
        String readLineString;
        FileReader fileReader = new FileReader(file);
        BufferedReader buffReader = new BufferedReader(fileReader);
        ArrayList struStrList = new ArrayList();
        readLineString = buffReader.readLine();
        String tmpString = "";
        while (readLineString != null)
        {           
            if (readLineString.startsWith("ATOM"))
            {
                tmpString +=(readLineString + "\n");
            }
            else 
            {
                if (!tmpString.equals(""))
                {
                    struStrList.add(tmpString);
                }
                tmpString = "";
            }
            readLineString = buffReader.readLine();
        }
        File[] struFiles = writeToFiles(struStrList);
        Structure[] structArray = ReadInPDB(struFiles);
        return structArray;
    }

    private File[] writeToFiles(ArrayList struStrList) throws IOException 
    {
        File[] struFiles = new File[struStrList.size()];
        for (int i=0; i < struStrList.size(); i++)
        {
//            File tmpFile = new File(pwdString+"/test" + (i + 1) + ".pdb");
            File tmpFile = new File(pwdString +"test" + (i + 1) + ".pdb");
            if (tmpFile.exists())
            {
                System.out.println("File exists");
            }
            else 
            {
                if (tmpFile.createNewFile())
                {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tmpFile));
                    bufferedWriter.write((String) struStrList.get(i));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            }
            struFiles[i] = tmpFile;
        }
        return struFiles;
    }

    public Structure[] ReadInPDB(File[] files) throws IOException 
    {
        Structure[] structures = new Structure[files.length];
        PDBFileReader pdbRead = new PDBFileReader();
         
        for (int i =0; i < files.length; i ++)
        {
            structures[i] = pdbRead.getStructure(files[i]);
            
        }
        return structures;
    }

    private double pairwiseRmsdCalcul(Atom[] atomCAArray1, Atom[] atomCAArray2) 
    {
        int len1 = atomCAArray1.length;
        int len2 = atomCAArray2.length;
        double tmpsum = -1;
        double rmsdValu = -1;
        // the structure have the same length 
        if (len1 == len2)
        {
            for (int i = 0; i < len1; i++) 
            {
                try 
                {
                    double[] coords1 = null;
                    double[] coords2 = null;;
                    coords1 = atomCAArray1[i].getCoords();
                    coords2 = atomCAArray2[i].getCoords();
                    double atomRmsd = (Math.pow((coords1[0] - coords2[0]), 2)
                            + Math.pow((coords1[1] - coords2[1]), 2)
                            + Math.pow((coords1[2] - coords2[2]), 2));
                    tmpsum += atomRmsd;
                } 
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                }
            }
            rmsdValu = Math.sqrt(tmpsum / len1);
        }
        //the two structure have different length.
        else 
        {
            //TODO
            rmsdValu = -1;
        }
        return rmsdValu;
    }

    private Structure[] getStructure(File[] pdbFiles) throws IOException 
    {
        Structure[] structures = new Structure[pdbFiles.length];
        PDBFileReader pdbRead = new PDBFileReader();
        for (int i =0; i <pdbFiles.length;i++)
        {
            structures[i] = pdbRead.getStructure(pdbFiles[i]);
            String[] tmpstrings = (pdbFiles[i].getName().toString()).split("\\.");
            structures[i].setName(tmpstrings[0]);
        }
        return structures;
    }

    private File[] checkRepeat(File[] pdbFiles, File[] appendFiles)
    {
        ArrayList newlist = new ArrayList();
        ArrayList repeatList = new ArrayList();
        ArrayList repeatNmaelist = new ArrayList();
        ArrayList newNamelist = new ArrayList();
        
        HashMap existMap = new HashMap();
        for (int i=0; i < pdbFiles.length; i ++)
        {
            String[] tmpstrings = (pdbFiles[i].getName().toString()).split("\\.");
            existMap.put(tmpstrings[0], "");
        }
        
        for (int i=0; i <appendFiles.length; i++)
        {
            String[] tmpstrings = (appendFiles[i].getName().toString()).split("\\.");
            System.out.println("hello:  "+existMap.get(tmpstrings[0]));
            if (existMap.get(tmpstrings[0]) != null)
            {
                repeatList.add(appendFiles[i]);
                repeatNmaelist.add(tmpstrings[0]);
            }
            else 
            {
                newlist.add(appendFiles[i]);
                newNamelist.add(tmpstrings[0]);
                System.out.println(tmpstrings[0]);
            }
        }
        
        File[] newaddFiles = convertList(newlist);
        File[] repeatFiles = convertList(repeatList);
        String newaddNames = convertList2(newNamelist);
        String repeatstr = convertList2(repeatNmaelist);
        System.out.println("Attention: in the files you selecyed, "+ repeatstr +"are already existed. So just append "+ newaddNames);
        return newaddFiles;
    }

    private File[] convertList(ArrayList newlist) 
    {
        File[] newaddFiles = new File[newlist.size()];
        for (int i =0; i < newlist.size(); i++)
        {
            newaddFiles[i] = (File)newlist.get(i);
        }
        return newaddFiles;
    }

    private String convertList2(ArrayList newNamelist) 
    {
        String tmpstr = "";
       String[] tmpStrings = new String[newNamelist.size()];
       for (int i =0 ; i < newNamelist.size(); i++)
       {
           tmpStrings[i] = (String)newNamelist.get(i);
           if (i == (newNamelist.size()-1))
           {
               tmpstr += (tmpStrings[i] +", ");
           }
           else 
           {
               tmpstr += (tmpStrings[i] +". ");
           }
           
       }
       return tmpstr;
    }
}

