/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.Sequence;

import java.awt.Color;

/**
 *
 * @author zhongxf
 */
public interface ColorSchemeI
{
    public Color colorOf(char c);
    
    public Color colorOf(String s);
    
    public Color colorOf(char c, Sequence seq, int offset);
    
    public Color colorOf(Sequence seq);
}
