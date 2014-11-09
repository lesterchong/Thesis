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
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lester Chong
 */
public class UtilityMatrix {
    private LinkedList<UtilityRow> matrix;
    private UtilityRow temp;
    private Scanner scan;
    private String line, token[];
    private SimpleDateFormat sd;
    
    public UtilityMatrix(){
        try{
            refreshDay();
            refreshMonth();
            readFromFile();
        }catch(FileNotFoundException e){
            matrix = new LinkedList<>();
        }
    }
    
    private boolean readFromFile() throws FileNotFoundException{
        matrix = new LinkedList<>();
        scan = new Scanner(new File("UtilityMatrix.csv"));
        sd = new SimpleDateFormat("yyyy/MM/dd");
        scan.nextLine();
        
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
        File file = new File("UtilityMatrix.csv");
        
        try{
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
    
    public UtilityRow searchSongByName(UtilityRow item){
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
    
    //returns corresponding double value based on the log
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

    //Issue: Problem if user pauses. Pauses found. Problem now is how to get time paused. 
    public double computeSkipped(long songSec){
        //1 - (song length played - length paused)/total song length
        //return value wherein 0 <= value < 1
        return 0;
    }
    
    public void parseUserInfo(){
        File file = new File("old_userinfo.txt");
        BufferedWriter bw;
        
        try{
            bw = new BufferedWriter(new FileWriter(file));
            scan = new Scanner(new File("userinfo.txt"));
            
            while(scan.hasNext()){
                temp = new UtilityRow();
                line = scan.nextLine();
                token = line.split(",");
                
                temp.setName(token[1]);
                temp.setPlayedDay(1);
                temp.setPlayedEver(1);
                temp.setPlayedWeek(1);
                
                if(doesSongExist(temp)==true){
                    temp = searchSongByName(temp);
                    temp.setPlayMethod(temp.getPlayMethod()+playMethodValue(line));
                    temp.setSkipped(0);
                    updateMatrix(temp);
                }else{
                    temp.setPlayMethod(0.1+playMethodValue(line));
                    temp.setSkipped(0);
                    matrix.add(temp);
                }
                    bw.write(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    //Still need to be tested.
    private void refreshDay() throws FileNotFoundException{
        String line, token[];
        Scanner scan;
        int currentDay=0, previousDay=0;
        
        /*
            Insert conditional statement here determining if day has changed
            Possible conditon would be:
            if(opened player date != player last closed date)
            This will be based on userinfo.txt
        */
        
        try {
            scan = new Scanner(new File(""));
            while(scan.hasNext()){
                line = scan.nextLine();
                if(line.contains("OPENED PLAYER")){
                    token = line.split("/");
                    previousDay = currentDay;
                    currentDay = Integer.parseInt(token[1]);
                }
            }
            
            if(currentDay != previousDay){
                //for-loop does the actual resetting of playedDate
                for(int ctr=0; ctr<matrix.size(); ctr++){
                    matrix.get(ctr).setPlayedDay(0);
                }
            }
            
        } catch (FileNotFoundException e) {
            Logger.getLogger(UtilityMatrix.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    
    /*
        Probably should change this shit to month. This shit be computationaly hassle.
        - Hassle in keeping track of week
        - How to know from week 1 to week 2
        - At least with month it'd be easier since it's already in the timestamp. More difficult than refreshDay but more plausible
    */
    private void refreshWeek(){
        /*
            Insert conditional statement here determining if week has changed
            Possible solution: 
                same shit as day
                    How to count date: Use Calendar object to do shit. 
        */
        for(int ctr=0; ctr<matrix.size(); ctr++){
            matrix.get(ctr).setPlayedWeek(0);
        }
    }
    
    private void refreshMonth(){
        int currentMonth=0, previousMonth=0;
        Scanner scan;
        
        try{
            scan = new Scanner(new File(""));
            while(scan.hasNext()){
                line = scan.nextLine();
                if(line.contains("OPENED PLAYER")){
                    token = line.split("/");
                    previousMonth = currentMonth;
                    currentMonth = Integer.parseInt(token[0]);
                }
            }
            
            if(currentMonth != previousMonth){
                for(int ctr=0; ctr<matrix.size(); ctr++){
                    //Tentative. Will be replaced with playedMonth if ever.
                    matrix.get(ctr).setPlayedWeek(0);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    //Returns top three instances that have the highest utility values
    public LinkedList<UtilityRow> topThreeUtility(){
        LinkedList<UtilityRow> list = new LinkedList<>();
        double highestUtility, utilityValue;
        int index=0;
        
        //Currently running at 3^n wherein n is number of instances. Needs to be optimized.
        for(int itr=0; itr<3; itr++){
            highestUtility=0;
            for(int ctr=0; ctr<matrix.size(); ctr++){
                //Below is equation for computing utility value. method+(playedDay*.5)+(playedWeek*.5)-skipped
                utilityValue = matrix.get(ctr).getPlayMethod()+(matrix.get(ctr).getPlayedDay()*.5)+(matrix.get(ctr).getPlayedWeek()*.5)+(matrix.get(ctr).getPlayedEver()*.5)-matrix.get(ctr).getSkipped();
                if(utilityValue > highestUtility){
                    highestUtility = utilityValue;
                    index = ctr;
                }
            }
            list.add(matrix.get(index));
            matrix.remove(index);
        }
        matrix.addAll(list);
        return list;
    }
}
