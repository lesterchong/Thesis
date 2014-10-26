/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Lester Chong
 */
public class UtilityMatrix {
    private LinkedList<UtilityRow> matrix;
    private UtilityRow temp;
    private Scanner scan;
    private String line, token[];
    
    
    public UtilityMatrix(){
        try{
            readFromFile();
        }catch(FileNotFoundException e){
            matrix = new LinkedList<>();
        }
    }
    
    public boolean readFromFile() throws FileNotFoundException{
        matrix = new LinkedList<>();

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
    }
    
    public void writeToFile(){
        try{
            File file = new File("UtilityMatrix.csv");
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            
            bf.write("name");
            bf.write("playMethod");
            bf.write("playedDay");
            bf.write("playedEver");
            bf.write("playedWeek");
            bf.write("skipped");
            
            
            while(!matrix.isEmpty()){
                bf.write(matrix.getFirst().getName());
                bf.write(String.valueOf(matrix.getFirst().getPlayMethod())+", ");
                bf.write(matrix.getFirst().getPlayedDay()+", ");
                bf.write(matrix.getFirst().getPlayedEver()+", ");
                bf.write(matrix.getFirst().getPlayedWeek()+", ");
                bf.write(String.valueOf(matrix.getFirst().getSkipped()));
                bf.newLine();
                matrix.removeFirst();
            }
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void updateMatrix(UtilityRow row){
        for(int ctr=0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(row.getName())){
                matrix.set(ctr, row);
            }
        }
    }
    
    public UtilityRow searchSong(UtilityRow item){
        for(int ctr = 0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(item.getName())){
                return matrix.get(ctr);
            }
        }
        return null;
    }
    
    public boolean doesSongExist(UtilityRow item){
        for(int ctr = 0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }
    
    public double playMethodValue(String log){
        if(log.endsWith("MANUAL") || log.endsWith("REPEAT") || log.endsWith("PREVIOUS")){
            return 1;
        }else if(log.endsWith("AUTOMATIC")){
            return .5;
        }else if(log.endsWith("NEXT")){
            return -.5;
        }
        return 0;
    }
    
    public void parseUserInfo(){
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        UtilityRow temp;
        //BufferedWriter bw = new FileWriter(new File())
        
        try{
            scan = new Scanner(new File("userinfo.txt"));
            
            while(scan.hasNext()){
                temp = new UtilityRow();
                line = scan.nextLine();
                token = line.split(",");
                
                temp.setName(token[1]);
                temp.setPlayedDay(1);
                temp.setDayPlayed(new Timestamp(System.currentTimeMillis()));
                temp.setPlayedEver(1);
                temp.setPlayedWeek(1);
                
                if(doesSongExist(temp)==true){    
                    temp = searchSong(temp);
                    temp.setPlayMethod(temp.getPlayMethod()+playMethodValue(line));
                    temp.setSkipped(0);
                    updateMatrix(temp);
                }else{
                    temp.setPlayMethod(0.1+playMethodValue(line));
                    temp.setSkipped(0);
                    matrix.add(temp);
                }
                
                
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public void refreshForTheDay(){
        for(int ctr=0; ctr<matrix.size(); ctr++){
            matrix.get(ctr).setPlayedDay(0);
        }
    }
    
    public void refreshForTheWeek(){
        for(int ctr=0; ctr<matrix.size(); ctr++){
            matrix.get(ctr).setPlayedWeek(0);
        }
    }
    
    //Returns top three instances that have the highest utility values
    public LinkedList<UtilityRow> topThreeUtility(){
        LinkedList<UtilityRow> list = new LinkedList<>();
        double highestUtility=0, temp;
        int index=0;
        
        //Currently running at 3^n wherein n is number of instances. Needs to be optimized.
        for(int itr=0; itr<3; itr++){
            for(int ctr=0; ctr<matrix.size(); ctr++){
                //Below is equation for computing utility value. method+(playedDay*.5)+(playedWeek*.4)-skipped
                temp = matrix.get(ctr).getPlayMethod()+(matrix.get(ctr).getPlayedDay()*.5)+(matrix.get(ctr).getPlayedWeek()*.4)-matrix.get(ctr).getSkipped();
                if(temp > highestUtility){
                    highestUtility = temp;
                    index = ctr;
                }
            }
        list.add(matrix.get(index));
        }
        return list;
    }
}
