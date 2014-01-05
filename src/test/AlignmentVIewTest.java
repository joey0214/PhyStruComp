/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import psc.Sequence.Alignment;
import psc.Sequence.Sequence;

/**
 *
 * @author zhongxf
 */
public class AlignmentVIewTest 
{
    
    public static void main(String args[]) 
    {
        Sequence testSeq1 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq2 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence[] seqarray = {testSeq1,testSeq2};
        Alignment testAlignment = new Alignment(seqarray, 0);
        
        if (testAlignment != null)
        {
            System.out.println("true");
        }
     }
    
    public void AlignmentViewTest()
    {
        Sequence testSeq1 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence testSeq2 = new Sequence("test", "AAACTGCTACCAACCC-ATCTGTGTTTGTGAAAATGACGGACCCACCAGCTCAAGTGTCA");
        Sequence[] seqarray = {testSeq1,testSeq2};
        Alignment testAlignment = new Alignment(seqarray, 0);
        
        if (testAlignment != null)
        {
            System.out.println("true");
        }
        
        
    }
    
    
    
}
