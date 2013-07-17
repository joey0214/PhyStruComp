/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.MUSTANG;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author zhongxf
 */
public class LocalMUSTANG 
{
    private Structure[] rawStructure ,alignedStructure;
    private String pwpath = System.getProperty("user.dir");
    private String inpath = null;
    private Structure superimposedStructure = null;
    
    
    public void setRawStructPath(String inputPath)
    {
        File infile = new File(inputPath);
        if (infile.exists() && infile.isDirectory())
        {
            this.inpath = inputPath;
        }
        else 
        {
            System.out.println("Error!! Check your input directory!");
        }
    }
    
    public void setRawStructure(Structure[] stru)
    {
        if (stru != null && stru.length!=0)
        {
            this.rawStructure = stru;
        }
        else 
        {
            System.out.println("Error! Check your structure files");
        }
    }
    
    public Structure[] getAlignedStructure()
    {
        return alignedStructure;
    }
    
    public void runMUSTANG()
    {
        //TODO
        //run MUSTANG in java 
    }
    
    private void mkdir()
    {
        String tmpString = "/aligned";
        File file = new File(pwpath+"/aligned");
        if (!file.exists())
        {
            file.mkdir();
        }
        else 
        {
            int tmpInt = 1;
            File newfile = new File(pwpath+tmpString+tmpInt);
//            while (!newfile.exists())
//            {
//                
//            }
        }
        
    }
    
    public void runLocalMUSTANG(String[] selectfiles, String filepath) throws IOException
    {
        writeScript(selectfiles, filepath);
        String cmd  = "mustang -f MUSTANG_zf-CCHH -o alignedResult";
        Runtime run = Runtime.getRuntime();
        try
        {
            Process process = run.exec(cmd);
            BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
            BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineString = null;
            while ((lineString = inBufferedReader.readLine()) != null)
            {
                System.out.println(lineString); //print the information of MUSTANG running
            }
            if (process.waitFor() !=0)
            {
                if (process.exitValue() == 1) //=0 execute normal. =1 execute failed
                {
                    System.out.println("command executed failed");
                }
            }
            inBufferedReader.close();
            inputStream.close();
        }
        catch(Exception ex)
        {
            System.out.println("Error whilst running MUSTANG");
        }
        //TODO 
        //run it by installed MUSTANG
        
        readAlignedStruct();
    }

    private void writeScript(String[] selectfiles, String filepath) throws IOException 
    {
        String tmpString = "> "+filepath;
        for (int i =0; i <selectfiles.length; i ++)
        {
            tmpString += ("\n"+"+"+selectfiles[i]);
        }
        
        FileWriter fw = new FileWriter(pwpath+"/MUSTANG_zf-CCHH");
        fw.write(tmpString);
        fw.flush();
        fw.close();
    }

    private void readAlignedStruct() throws IOException 
    {
        //FileReader fileReader = new FileReader("/alignedResult.pdb");
        File alignedFile = new File(pwpath+"/alignedResult.pdb");
        PDBFileReader pdbReader = new PDBFileReader();
        
        Structure alignedStruct = pdbReader.getStructure(alignedFile);
        if (alignedStruct == null)
        {
            System.out.println("structure is null");
        }
        else if (alignedFile != null && alignedStruct.size() ==0)
        {
            System.out.println("size is 0");
        }
        else if (alignedStruct != null && alignedStruct.size() !=0)
        {
            System.out.println("well readed");
        }
        this.superimposedStructure = alignedStruct;
    }
    
    public Structure getSuperimposed()
    {
        return superimposedStructure;
    }
    
    public   void exeractSuperoimposed() throws FileNotFoundException, IOException
    {
        File alignedFile = new File(pwpath+"/alignedResult.pdb");
        
        String readLineString;
        FileReader fileReader = new FileReader(alignedFile);
        BufferedReader buffReader = new BufferedReader(fileReader);
        ArrayList remarkList = new ArrayList();
        ArrayList struStrList = new ArrayList();
        readLineString = buffReader.readLine();
        String tmpString = "";
        String tmpString2 = "";
        while (readLineString != null)
        {   
           // System.out.println(readLineString);
            if (readLineString.startsWith("REMARK"))
            {
                tmpString += (readLineString + "\n");
            }
            else if (readLineString.startsWith("ATOM"))
            {
                //remarkList.add(tmpString);
                tmpString2 +=(readLineString + "\n");
            }
            
            else if ((readLineString.startsWith("TER")))
            {
                struStrList.add(tmpString2);
                tmpString2 = "";
//                if (!tmpString.equals("TER"))
//                {
//                    struStrList.add(tmpString2);
//                }
//                tmpString = "";
            }
            readLineString = buffReader.readLine();
        }
        System.out.println("===========================================================================");
        System.out.println(tmpString);
         System.out.println(tmpString2);
        
         String[] names = getpdbNames(tmpString);
         Structure[] alignedStructures = getAligned(struStrList,names);
//        File[] struFiles = writeToFiles(struStrList);
//        Structure[] structArray = ReadInPDB(struFiles);
//        return structArray;
        
    }

    private String[] getpdbNames(String tmpString) 
    {
        ArrayList tmpList = new ArrayList();
        String[] tmpArray = tmpString.split(" ");
        for (int i=0; i < tmpArray.length; i++)
        {
            if (tmpArray[i].endsWith(".pdb"))
            {
                if (tmpList == null)
                {
                    tmpList.add(tmpArray[i]);
                }
                else 
                {
                    boolean added = true;
                    for (int l =0; l < tmpList.size(); l ++)
                    {
                        if (tmpArray[i].equals( tmpList.get(l).toString()))
                        {
                            added = false;
                        }
                    }
                    if (added == true)
                    {
                        tmpList.add(tmpArray[i]);
                    }
                }
            }
        }
        String[] pdbnames = new String[tmpList.size()];
        for (int k =0 ; k < tmpList.size(); k++)
        {
            pdbnames[k] = (((String)tmpList.get(k)).split("\\."))[0];
            System.out.println(pdbnames[k]);
        }
        
        return pdbnames;
    }

    private Structure[] getAligned(ArrayList struStrList, String[] names) throws IOException 
    {
        mkdir();
        PDBFileReader pdbReader = new PDBFileReader();
        Structure[] pdbStru = new Structure[struStrList.size()]; 
        if (struStrList.size() == names.length)
        {
            for (int i = 0; i < struStrList.size(); i++) 
            {
                File tmpFile = new File(pwpath + "/aligned/" + names[i] + ".pdb");
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
               pdbStru[i] = pdbReader.getStructure(tmpFile);
            }
        }
        else 
        {
            System.out.print("Fatal Bug!");
        }
        this.alignedStructure = pdbStru;
        return pdbStru;
    }
    
   
}
