/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Structure;

import java.io.File;
import java.io.IOException;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;


/**
 *
 * @author zhongxf
 */
public class Test 
{
    public static  void main(String[] args) throws IOException
    {
//        String path1 = "/home/zhongxf/analyTools/results.pdb";
//        String path2 = "/home/zhongxf/research/myPerl/cupin/1DZT.pdb";
//        
//        File pdbfile = new File(path2);
//        PDBFileReader pdbfr = new PDBFileReader();
//        //pdbfr.setPdbDirectorySplit(true);
//        Structure stru = pdbfr.getStructure(pdbfile);
//        Structure[] tmp = new Structure[1];
//        tmp[0] = stru;
//        ChainConformity check = new ChainConformity();
//        check.readIn(tmp);
        Separater separater = new Separater();
        separater.Separater();
    }
}
