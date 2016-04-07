/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joey.wuhan.phystrucomp.Sequence;

import java.awt.Color;
import java.awt.Label;
import java.util.HashMap;

/**
 *
 * @author zhongxf
 */
public  class PscColorScheme implements ColorSchemeI
{
    private HashMap<String, Color> colorMap = new HashMap<String, Color>();
    
   public  PscColorScheme()
   {
       addSpecies("A", new Color(229, 51, 25));
       addSpecies("a", new Color(229, 51, 25));
       addSpecies("T", new Color(25, 204, 25));
       addSpecies("t", new Color(25, 204, 25));
       addSpecies("G", new Color(229, 153, 76));
       addSpecies("g", new Color(229, 153, 76));
       addSpecies("C", new Color(25, 127, 229));
       addSpecies("c", new Color(25, 127, 229));
       addSpecies("-", Color.white);
       addSpecies(" ", Color.white);
       addSpecies("~", Color.white);
       addSpecies(".", Color.white);
       
//       addSpecies("D", new Color(230,10,10));
//       addSpecies("d", new Color(230,10,10));
//       addSpecies("E", new Color(230,10,10));
//       addSpecies("e", new Color(230,10,10));
//       
//       addSpecies("R", new Color(20,90,255));
//       addSpecies("r", new Color(20,90,255));
//       addSpecies("K", new Color(20,90,255));
//       addSpecies("k", new Color(20,90,255));
//       
//       addSpecies("F", new Color(50,50,170));
//       addSpecies("f", new Color(50,50,170));
//       addSpecies("Y", new Color(50,50,170));
//       addSpecies("y", new Color(50,50,170));
//       
//       addSpecies("R", new Color(20,90,255));
//       addSpecies("r", new Color(20,90,255));
//       addSpecies("K", new Color(20,90,255));
//       addSpecies("k", new Color(20,90,255));
//       
//       addSpecies("G", new Color(235,235,235));
//       addSpecies("g", new Color(235,235,235));
//       
//       addSpecies("A", new Color(200,200,200));
//       addSpecies("a", new Color(200,200,200));
//       
//       addSpecies("h", new Color(130,130,210));
//       addSpecies("H", new Color(130,130,210));
//       
//       addSpecies("W", new Color(180,90,180));
//       addSpecies("w", new Color(180,90,180));
//       
//       addSpecies("c", new Color(230,230,0));
//       addSpecies("C", new Color(230,230,0));
//       addSpecies("M", new Color(230,230,0));
//       addSpecies("m", new Color(230,230,0));
//       
//       addSpecies("s", new Color(250,150,0));
//       addSpecies("S", new Color(250,150,0));
//       addSpecies("t", new Color(250,150,0));
//       addSpecies("T", new Color(250,150,0));
//       
//       addSpecies("N", new Color(0,220,220));
//       addSpecies("n", new Color(0,220,220));
//       addSpecies("Q", new Color(0,220,220));
//       addSpecies("q", new Color(0,220,220));
//       
//       addSpecies("L", new Color(15,130,15));
//       addSpecies("l", new Color(15,130,15));
//       addSpecies("I", new Color(15,130,15));
//       addSpecies("i", new Color(15,130,15));      
//       addSpecies("V", new Color(15,130,15));
//       addSpecies("v", new Color(15,130,15));
//       
//       addSpecies("p", new Color(220,150,130));
//       addSpecies("P", new Color(220,150,130));
       
     
       
   }
   
   public void addSpecies(char seqSymbol, Color charColor )
   {
       addSpecies(seqSymbol+"", charColor);
   }
   
   public final void addSpecies(String seqSymbolString, Color color)
   {
       colorMap.put(seqSymbolString, color);
   }
   
   public Color colorOf(String seqLabel)
   {
       if (colorMap.containsKey(seqLabel))
       {
           return colorMap.get(seqLabel);
       }
       return Color.gray;
   }
   
   public Color colorOf(char label)
   {
       return colorOf(label+"");
   }
   
   public Color colorOf(char cha , Sequence seq, int offset)
   {
       return colorOf(cha);
   }
   
   public HashMap<String, Color> getColorMap()
   {
       return colorMap;
   }
   
   public void setColorMap(HashMap<String,Color> newColorMap)
   {
       this.colorMap = newColorMap;
   }
   
   public Color colorOf(Sequence seq)
   {
       return Color.white;
   }
   
}
