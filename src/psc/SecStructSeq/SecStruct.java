/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.SecStructSeq;

import psc.gui.PSCgui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.align.util.AtomCache;
import org.biojava.bio.structure.io.FileParsingParameters;
import org.biojava.bio.structure.io.PDBFileReader;
import psc.WebService.WebService;

/**
 *
 * @author zhongxf
 */
public class SecStruct 
{
    private String path;
    private String[] pdbnameArray;
    
    public void setPath(String pathString)
    {
        this.path = pathString;
    }
    
    public void setNameList(String[] names)
    {
        this.pdbnameArray = names;
    }
    
    public String[] getSecondSeq() throws IOException, StructureException
    {
        FileParsingParameters parameters = new FileParsingParameters();
        parameters.setParseSecStruc(true);
        AtomCache cache = new AtomCache();
        cache.setFileParsingParams(parameters);
        String[] secseqList = new String[pdbnameArray.length];
        for (int i = 0; i < pdbnameArray.length; i++) 
        {
            try 
            {
                String tmpName = pdbnameArray[i];
                cache.setPath(path);
//             Structure tmp =cache.getStructure("1JNX");
                Structure tmp = cache.getStructure(tmpName);
                String secseq = "";
                for (Chain chain : tmp.getChains()) 
                {
                    for (Group group : chain.getAtomGroups()) 
                    {
                        if (group instanceof AminoAcid) 
                        {
                            AminoAcid aa = (AminoAcid) group;
                            Map sec = aa.getSecStruc();
                            secseq += charDecide(sec.get("PDB_AUTHOR_ASSIGNMENT"));
                        }
                    }
                }
                secseqList[i] = secseq;
            }
            catch(Exception e)
            {
                System.out.println("can not find the secondary structure information. maybe is a predicted structure");
                secseqList[i] = "nullseq";
            }
        }
        
        return secseqList;
    }
    
    

    private static Structure getStructure(String filePath1) throws IOException 
    {
        PDBFileReader pdbfr = new PDBFileReader();
        Structure tmp = pdbfr.getStructure(filePath1);
        return tmp;
    }
    String filePath1 = "/home/zhongxf/aTEST/BRCA1/1JNX.pdb";
    String filePath2 = "/home/zhongxf/aTEST/BRCA1/1N5O.pdb";

//    public static void main(String[] args) throws IOException
//    {
//        
////        Structure tmp =getStructure(filePath1);
//       
//        try 
//        {
//            FileParsingParameters parameters = new FileParsingParameters();
//            parameters.setParseSecStruc(true);
//            AtomCache cache = new AtomCache();
//            cache.setFileParsingParams(parameters);
//            cache.setPath("/home/zhongxf/aTEST/BRCA1/");
////             Structure tmp =cache.getStructure("1JNX");
//                     Structure tmp =cache.getStructure("1JNX");
//            for (Chain chain:tmp.getChains())
//            {
//                for (Group group:chain.getAtomGroups())
//                {
//                    if (group instanceof AminoAcid)
//                    {
//                        AminoAcid aa = (AminoAcid)group;
//                        Map sec = aa.getSecStruc();
//                        System.out.println(chain.getChainID() + " " + group.getResidueNumber() + " " + group.getPDBName() + " " + " " +sec.get("PDB_AUTHOR_ASSIGNMENT"));
//                    }
//                }
//            }
//        }
//        catch (Exception e){
//            System.out.println("your structure is predicted!");
//            //e.printStackTrace();
//        }
//        
//    }

    private int getLengthOfResidues(ArrayList<String> stringsInCOMPND) 
    {
        int lengthOfResidues =0;
        int[] residesRange = new int[2];
        for(int i =0; i < stringsInCOMPND.size(); i ++)
        {
            String regEx = "([0-9]{1,}+)-([0-9]{1,}+)";
                Pattern lengthPattern = Pattern.compile(regEx) ;
                Matcher rangeMatcher = lengthPattern.matcher(stringsInCOMPND.get(i));
//                System.out.println( rangeMatcher.lookingAt()); 
//                System.out.println(rangeMatcher.matches());
            
                if (rangeMatcher.find())
                {
                    System.out.println(rangeMatcher.group());
                    String[] range = rangeMatcher.group().toString().split("-");
                    int startRange = Integer.parseInt(range[0]);
                    int endRange = Integer.parseInt(range[1]);
                    lengthOfResidues = endRange - startRange +1;
                    
                }
                
                else 
                {
                    System.out.println("no matcher find! ");
                }
        }
        return lengthOfResidues;
    }

    private ArrayList getHelixRange(ArrayList<String> stringsInHELIX) 
    {
        ArrayList helixRange = new ArrayList();
        for (int i =0; i < stringsInHELIX.size(); i++)
        {
            ArrayList helixLineContent = new ArrayList();
            String[] spiltedString = stringsInHELIX.get(i).split(" ");
            for (int k =0; k < spiltedString.length; k++)
            {
                if (spiltedString[k] != " ")
                {
                    helixLineContent.add(spiltedString[k]);
                }
                System.out.println("spiltedString[k].toString()  " +k+ "--->" + spiltedString[k].toString()+ "\n");
            }
        }
        
        return helixRange;
    }

    private String charDecide(Object type) 
    {
        String charact = null ;
        String tmpString = (String)type;
        if (tmpString.equals("HELIX"))          { charact = "H";  }
        else if (tmpString.equals("STRAND"))    { charact = "E";  }
        else if (tmpString==null)               { charact = "C";  }
        else if (tmpString.equals("null"))      { charact = "C";  }
        else if (tmpString.equals("-"))         { charact = "-";  }
                
        return charact;            
    }

   
}




