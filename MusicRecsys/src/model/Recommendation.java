/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author mikeyboy213
 */
public class Recommendation {
    UtilityMatrix matrix;
    LinkedList <SongInstance> instances;
    LinkedList <SongInstance> topten;
    LinkedList <SongInstance> three = new LinkedList<>();
    private Scanner scan;
    private String line, tokens[];
    private int counter = 1;
    SongInstance temp;
    
    public Recommendation(UtilityMatrix matrix){
        this.matrix = matrix;
        
        try{
        readFromFile();
        }catch(FileNotFoundException e){
            //File does not exist
        }
    }
    
    public void readFromFile() throws FileNotFoundException{
        instances = new LinkedList<>();
        scan = new Scanner(new File("SongValues.arff"));
        scan.nextLine();
        
        while(scan.hasNext() && scan.nextLine() != "@data")
            scan.nextLine();
        
        while(scan.hasNext()){
            line = scan.nextLine();
            tokens = line.split(",");
            temp = new SongInstance();
            
            temp.setId(counter);
           // temp.setCluster(tokens[tokens.length()-1]);
           // temp.setX(?);
           // temp.setY(?);
            instances.add(temp);
            counter ++;
        }    
        
    }
    
    public LinkedList<SongInstance> Convert(){
        LinkedList <UtilityRow> topthree = matrix.topThreeUtility();
        LinkedList<SongInstance> temp = new LinkedList<>();
        String id;
        
        for(int ctr = 0; ctr < topthree.size(); ctr++){
            
            tokens = topthree.get(ctr).getName().split(" ");
            id = tokens[0];
            
            for(int ctr2 = 0; ctr2 < instances.size(); ctr2++){
                
                if(id.equals(instances.get(ctr2).getId())){
                    temp.add(instances.get(ctr2));
                    //break;
                }
            }
        }
        return temp;
    }
    
    public int euclideanDistance(SongInstance instance){
        int distance;
        distance = ((three.get(1).getX() - instance.getX()) + (three.get(1).getY() - instance.getY())) +
                   ((three.get(2).getX() - instance.getX()) + (three.get(2).getY() - instance.getY())) +
                   ((three.get(3).getX() - instance.getX()) + (three.get(3).getY() - instance.getY()));
        
        return distance;
    }
    
    public LinkedList <SongInstance> Recommend(){ 
        three = Convert();
        int distance;
        
        for(int ctr = 0; ctr < instances.size(); ctr++){
            distance = euclideanDistance(instances.get(ctr));
            
            //if distance is least then topten.add(instance.get(ctr));
        }
        
        return topten;
    }
    
    
}
