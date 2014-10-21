/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;

/**
 *
 * @author Lester Chong
 */
public class UtilityMatrix {
    private LinkedList<UtilityRow> matrix;
    private UtilityRow temp;
    private Scanner scan;
    
    public UtilityMatrix(){
        //if text file does not exist
            matrix = new LinkedList<>();
        //else
            //readFromFile();
    }
    
    public boolean readFromFile(){
        String line, token[];
        matrix = new LinkedList<>();
        
        try{
            scan = new Scanner(new File("UtilityMatrix.csv"));
            while(scan.hasNext()){
                line = scan.nextLine();
                token = line.split(",");
                temp = new UtilityRow();
                
                temp.setName(token[0]);
                temp.setPlayMethod(Double.parseDouble(token[1]));
                temp.setPlayedDay(Integer.parseInt(token[2]));
                temp.setPlayedWeek(Integer.parseInt(token[3]));
                temp.setPlayedEver(Integer.parseInt(token[4]));
                temp.setSkipped(Double.parseDouble(token[5]));
                matrix.addLast(temp);
            }
            return true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public void writeToFile(){
        
    }
    
    public void addColumn(PlaylistItem item){
        temp = new UtilityRow();
        
        //Initialized as zero
        temp.setName(item.getName());
        temp.setPlayMethod(0);
        temp.setPlayedDay(0);
        temp.setPlayedEver(0);
        temp.setPlayedWeek(0);
        matrix.addLast(temp);
    }
    
    public void updateColumn(UtilityRow row){
        Iterator itr;
        itr = matrix.iterator();
        while(itr.hasNext()){
            
        }
    }
    
    public UtilityRow searchSong(){
        return null;
    }
    
    public void parseUserInfo(){
        
    }
}
