/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicrecsys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lester Chong
 */
public class MusicRecsys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        long mili1, mili2;
        
        try{
            mili1 = sd.parse("2014/10/14 12:59:00").getTime();
            mili2 = sd.parse("2014/10/14 13:00:20").getTime();
            
            System.out.println((mili2-mili1)/1000);
        }catch(ParseException e){
            
        }
        
            
        
    }
}