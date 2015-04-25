/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recommender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Lester Chong
 */

/** 
 * @file UtilityMatrix.java
 * 
 * @brief This file contains the class for manipulating the utility matrix
 * 
 **/ 

/** 
 * @class UtilityMatrix
 * 
 * @brief Contains the methods for manipulating the utility matrix
 * 
 * This class contains various methods for manipulating the utility matrix.
 * Methods such as parsing the userinfo.txt, storing the current matrix to UtilityMatrix.mrs, updating the current utility matrix, and selecting the 3 instances with the highest utility values.
 */ 

    
public class UtilityMatrix {
    private static LinkedList<UtilityRow> matrix;
    private UtilityRow temp;
    private Scanner scan;
    private String line, token[];
    private SimpleDateFormat sd;
    private Timestamp lastUsed = new Timestamp(0);
    
    
    /**
    * @brief Constructor of the UtilityMatrix class
    * 
    * This constructor ensures that when UtilityMatrix class is instantiated, it will check if UtilityMatrix.mrs already exists. If it exists, it will set the values of playedDay and playedMonth to 0. If UtilityMatrix.mrs does not exists it will create one.
    * After creating a new UtilityMatrix.mrs or reading an existing one, it then parses the user listening log and outputs a file named UtilityMatrix.mrs
    * 
    */
    public UtilityMatrix(){
        try{
            readFromFile();
            refreshDay();
            refreshMonth();
        }catch(FileNotFoundException | NullPointerException e){
            matrix = new LinkedList<>();
        }
        parseUserInfo();
        assignName();
        writeToFile();
    }
    
    /**
    * @brief Parses UtilityMatrix.mrs and stores it in memory. Returns true if parsing is successful.
    * 
    * This method parses UtilityMatrix.mrs and saves it in memory. Returns true if parsing is successful. Throws FileNotFoundException if UtilityMatrix.mrs does not exist.
    */
    private boolean readFromFile() throws FileNotFoundException{
        int ctr=0;
        matrix = new LinkedList<>();
        scan = new Scanner(new File("UtilityMatrix.mrs"));
        sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        try{
            lastUsed = new Timestamp(sd.parse(scan.nextLine()).getTime());
        }catch(ParseException e){
            System.out.println("Error parsing date");
        }
            scan.nextLine();

            while(scan.hasNext()){
                line = scan.nextLine();
                token = line.split(",");
                temp = new UtilityRow();

                    temp.setID(Integer.parseInt(token[0]));
                    temp.setName(token[1]);
                    temp.setPlayMethod(Double.parseDouble(token[2]));
                    temp.setPlayedDay(Integer.parseInt(token[3]));
                    temp.setPlayedMonth(Integer.parseInt(token[4]));
                    temp.setPlayedEver(Integer.parseInt(token[5]));
                    temp.setSkipped(Double.parseDouble(token[6]));
                    System.out.println(temp.getID());
                    matrix.addLast(temp);
                    ctr++;
            }
        return true;
    }
    
    /**
    * @brief Writes the current utility matrix from memory to a file named UtilityMatrix.mrs
    * 
    * This method writes the utility matrix currently stored in memory to a file named UtilityMatrix.mrs.
    */
    public void writeToFile(){
        File file = new File("data/UtilityMatrix.mrs");
        sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        try{
            assignName();
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            
            bf.write(sd.format(new java.util.Date()));
            bf.newLine();
            bf.write("ID,");
            bf.write("name,");
            bf.write("playMethod,");
            bf.write("playedDay,");
            bf.write("playedEver,");
            bf.write("playedWeek,");
            bf.write("skipped");
            bf.newLine();
            
            
            for(int ctr =0; ctr<matrix.size(); ctr++){
                bf.write(matrix.get(ctr).getID()+",");
                bf.write(matrix.get(ctr).getName()+",");
                bf.write(String.valueOf(matrix.get(ctr).getPlayMethod())+",");
                bf.write(matrix.get(ctr).getPlayedDay()+",");
                bf.write(matrix.get(ctr).getPlayedEver()+",");
                bf.write(matrix.get(ctr).getPlayedMonth()+",");
                bf.write(String.valueOf(matrix.get(ctr).getSkipped()));
                bf.newLine();
            }
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
    * @brief Updates a row in the utility matrix
    * 
    * This method searches through the utility matrix and updates it accordingly.
    */
    public void updateMatrix(UtilityRow row){
        for(int ctr=0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(row.getName())){
                matrix.set(ctr, row);
            }
        }
    }
    
    /**
    * @brief Returns a UtilityRow that matches the song name of the argument
    * 
    * This method searches for the corresponding UtilityRow that matches the song name of the argument. Returns null if a match was not found.
    */
    public UtilityRow searchSongByName(UtilityRow item){
        for(int ctr = 0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(item.getName())){
                return matrix.get(ctr);
            }
        }
        return null;
    }
    
    /**
    * @brief Returns true if the instance exists in the matrix. Returns false otherwise.
    * 
    * This method searches if the instance already exists in the matrix using the instance name. Returns true if a match is found and false if there is no match.
    */
    public boolean doesSongExist(UtilityRow item){
        for(int ctr = 0; ctr<matrix.size(); ctr++){
            if(matrix.get(ctr).getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }
    
    /**
    * @brief Parses the play method from the user log. Returns corresponding value for each play method.
    * 
    * This method parses the string log to determine which kind of play method it is and returns the corresponding value of the play method. Returns 0 if an invalid string was found
    */
    public double playMethodValue(String log){
        double E=1;
        if(log.endsWith("MANUAL") || log.endsWith("REPEAT") || log.endsWith("PREVIOUS")){
            return E;
        }else if(log.endsWith("AUTOMATIC")){
            return E/2;
        }else if(log.endsWith("NEXT")){
            return -E/2;
        }
        return 0;
    }

    /**
    * @brief Parses user_info.txt
    * 
    * This method parses user_info.txt and saves it in memory.
    */
    public void parseUserInfo(){
        Timestamp currentEntry; 
        BufferedWriter bw;
        int ctr = 0;
        sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        try{
            scan = new Scanner(new File("userinfo.txt"));
            
            while(scan.hasNext()){
                temp = new UtilityRow();
                line = scan.nextLine();
                token = line.split(",");
                currentEntry = new Timestamp(sd.parse(token[0]).getTime());
                
                if(!(line.contains("OPENED PLAYER") || line.contains("CLOSED PLAYER"))){
                    if(!currentEntry.before(lastUsed)){
                        temp.setID(ctr);
                        temp.setName(token[1]);
                        temp.setPlayedDay(1);
                        temp.setPlayedEver(1);
                        temp.setPlayedMonth(1);

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
                        ctr++;
                    }
                }
        }
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        
    }
    
    /**
    * @brief Refreshes UtilityRow.playedDay if current date does not match the timestamp when the song was played.
    * 
    * This method refreshes UtilityRow.playedDay if current date does not match the timestamp when the song was played. Throws FileNotFoundException and NullPointerException if UtilityMatrix.mrs was not found.
    */
    private void refreshDay() throws FileNotFoundException, NullPointerException{
        String line, token[];
        Scanner scan;
        int currentDay=0, previousDay=0;
            scan = new Scanner(new File("userinfo.txt"));
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
                //Null Pointer exception
                for(int ctr=0; ctr<matrix.size(); ctr++){
                    matrix.get(ctr).setPlayedDay(0);
                }
            }
            
    }
    
    /**
    * @brief Currently unimplemented. Supposed to refresh playedWeek. For future work. 
    * 
    * This method is supposed to refresh UtilityRow.playedWeek if current week does not match the week when the song was played. Throws UnsupportedOperationException as it currently does nothing.
    */
    private void refreshWeek() throws UnsupportedOperationException{
    }
    
    /**
    * @brief Refreshes UtilityRow.playedDay if current month does not match the month when the song was played.
    * 
    * This method Refreshes UtilityRow.playedDay if current month does not match the month when the song was played.
    */
    private void refreshMonth(){
        int currentMonth=0, previousMonth=0;
        Scanner scan;
        
        try{
            scan = new Scanner(new File("data/userinfo.txt"));
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
                    matrix.get(ctr).setPlayedMonth(0);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    /**
    * @brief Computes for 3 instances with the highest utility values
    * 
    * This method computes for 3 instances with the highest utility values.
    * The method computes for the utility value 3 times, selecting the highest utility value each iteration. 
    * Needs optimization for future work. 
    */
    public LinkedList<UtilityRow> topThreeUtility(){
        LinkedList<UtilityRow> list = new LinkedList<>();
        double highestUtility, utilityValue;
        int index=0;
        
        //Currently running at 3^n wherein n is number of instances. Needs to be optimized.
        for(int itr=0; itr<3; itr++){
            highestUtility=0;
            for(int ctr=0; ctr<matrix.size(); ctr++){
                //Below is equation for computing utility value. method+(playedDay*.5)+(playedWeek*.5)-skipped
                utilityValue = matrix.get(ctr).getPlayMethod()+(matrix.get(ctr).getPlayedDay()*.5)+(matrix.get(ctr).getPlayedMonth()*.5)+(matrix.get(ctr).getPlayedEver()*.5)+matrix.get(ctr).getSkipped();
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
    
    /**
    * @brief Returns utility matrix
    * 
    * This method returns the current utility matrix stored in memory
    */
    public LinkedList<UtilityRow> getUtilityMatrix(){
        return matrix;
    }
    
    /**
    * @brief Assigns the song name to its correct ID
    * 
    * This method checks 'Song Name Database.txt' and reassigns the ID of UtilityRow.ID to its correct value from 'Song Name Database.txt'
    */
    private void assignName(){
        String line;
        int ctr, ctr2=0;
        
        try{
            for(ctr=0; ctr<matrix.size(); ctr++){
                ctr2=0;
                scan = new Scanner(new File("data/Song Name Database.txt"));
                while(scan.hasNext()){
                    line = scan.nextLine();
                    line = line.concat(".wav");
                    line = line.trim();
                    matrix.get(ctr).setName(matrix.get(ctr).getName().trim());
                    if(matrix.get(ctr).getName().equalsIgnoreCase(line)){
                        matrix.get(ctr).setID(ctr2);                        
                    }
                    ctr2++;
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
