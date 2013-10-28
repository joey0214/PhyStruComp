/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

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
       addSpecies("A", new Color(229,51,25));
        addSpecies("a", new Color(229,51,25));
        addSpecies("T", new Color(25,204,25));
        addSpecies("t", new Color(25,204,25));
        addSpecies("G", new Color(229,153,76));
        addSpecies("g", new Color(229,153,76));
        addSpecies("C", new Color(25,127,229));
        addSpecies("c", new Color(25,127,229));
        addSpecies("-", Color.white);
        addSpecies(" ", Color.white);
        addSpecies("~", Color.white);
        addSpecies(".", Color.white);
   }
   
   public void addSpecies(char seqSymbol, Color charColor )
   {
       addSpecies(seqSymbol+"", charColor);
   }
   
   public void addSpecies(String seqSymbolString, Color color)
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
