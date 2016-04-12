/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import charite.christo.interfaces.PredictionFromAminoacidSequence;
import charite.christo.strap.extensions.SecondaryStructure_Sopma;

/**
 *
 * @author zhongxf
 */
public class STRAPtest 
{
    public static void main(String[] args)
    {
         PredictionFromAminoacidSequence  predictor=new SecondaryStructure_Sopma ();
         predictor.setGappedSequences(new String[]{"MFLTRSEYDRGVNTFSPEETMTVESVTQAVSNLALQFGEEDADPGAMSRPFGVALLFGGVDEKGPQLFHMDPGRLFQVEYA"});
         predictor.compute();
          char result[]=predictor.getPrediction()[0];
       System.out.println(new String(result));
    }
    
}
