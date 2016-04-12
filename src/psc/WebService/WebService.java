/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psc.WebService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

/**
 *
 * @author zhongxf
 */
public class WebService 
{
    private boolean webaccess;
    
    //IFRGLEICCYGPFTNMPTDQLEWMVQLCGASVVKELSSFTLGTGVHPIVVVQPDAWT---GFHAIGQMCEAPVVTREWVLDSVALYQCQELDTYLIPQIP

    //TODO: check access to internet
    public boolean check()
    {
        URL testUrl = null;
        try 
        {
            testUrl = new URL("http://bioinf.cs.ucl.ac.uk/psipred/");
            try 
            {
                InputStream in = testUrl.openStream();
                System.out.println("Hello! can access to Internet!");
                webaccess = true;
                in.close();
            }
            catch (IOException ioex)
            {
                System.out.println("Sorry! can not access to Internet!");
                webaccess = false;
            }
        }
        catch (MalformedURLException mae)
        {
            mae.printStackTrace();
            webaccess = false;
        }
        return webaccess;
    }
    
//    public  static  void   main(String[] args) throws IOException
//    {
//        String testString = "IFRGLEICCYGPFTNMPTDQLEWMVQLCGASVVKELSSFTLGTGVHPIVVVQPDAWT---GFHAIGQMCEAPVVTREWVLDSVALYQCQELDTYLIPQIP";
//       
//       // System.out.println(getPSIPRED(testString));
//    }
    public String getPSIPRED(String proseq) throws IOException 
    {
        String checkAccess = "ping bioinf.cs.ucl.ac.uk/psipred/";

        Socket socket = new Socket("bioinf.cs.ucl.ac.uk/psipred/", 80);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.toString();
        
        return (out.toString());
        
    }  
}
