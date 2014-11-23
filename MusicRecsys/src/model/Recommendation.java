/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author mikeyboy213
 */
public class Recommendation {
    private UtilityMatrix matrix;
    private LinkedList <SongInstance> instances;
    private LinkedList <SongInstance> topten;
    private LinkedList <SongInstance> three;
    private Scanner scan;
    private String line, tokens[];
    private int counter = 1;
    private SongInstance temp;
    private LinkedList <Float> tempattributes;
    private LinkedList<SongInstance> cluster0;
    private LinkedList<SongInstance> cluster1;
    private LinkedList<SongInstance> cluster2;
    private LinkedList<SongInstance> cluster3;
    private float defNumber = 0;

    public Recommendation(UtilityMatrix matrix){
        this.matrix = matrix;
        
        try{
        readFromFile();
        }catch(FileNotFoundException e){
            System.out.println("Cannot find file.");
        }
        
    }
    
    public void readFromFile() throws FileNotFoundException{
        instances = new LinkedList<>();
        scan = new Scanner(new File("Song Attributes.arff"));
        
        for(int ctr = 1; ctr <= 195; ctr++){
            scan.nextLine();
        }
        
        /*
        while(!scan.nextLine().equals("@data")){
            System.out.println(scan.nextLine());
            scan.nextLine();
        }
        */
        
        while(scan.hasNext()){
            line = scan.nextLine();
            tokens = line.split(",");
            temp = new SongInstance();
            tempattributes = new LinkedList();
            
            temp.setId(counter);
            
            for(int ctr = 1; ctr < tokens.length-1; ctr++){
                
                if(tokens[ctr].equals("?"))
                    tempattributes.add(defNumber);
                
                else
                    tempattributes.add(Float.parseFloat(tokens[ctr]));
            }
            
            temp.setAttributes(tempattributes);
            temp.setCluster(tokens[tokens.length-1]);
            instances.add(temp);
            counter ++;
        }
        scan.close();
        
        Cluster(instances);
        
    }
    
    
    public void Cluster (LinkedList<SongInstance> list){
        cluster0 = new LinkedList<>();
        cluster1 = new LinkedList<>();
        cluster2 = new LinkedList<>();
        cluster3 = new LinkedList<>();
        
        for(int ctr = 0; ctr < list.size(); ctr++){
            
            if(list.get(ctr).getCluster().equals("cluster0"))
                cluster0.add(list.get(ctr));
            
            else if(list.get(ctr).getCluster().equals("cluster1"))
                cluster1.add(list.get(ctr));
            
            else if(list.get(ctr).getCluster().equals("cluster2"))
                cluster2.add(list.get(ctr));
            
            else if(list.get(ctr).getCluster().equals("cluster3"))
                cluster3.add(list.get(ctr));
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
    
    public void euclideanDistance(SongInstance instance){
        instance.setTempdistance(0);
        /*
        distance = ((three.get(1).getX() - instance.getX()) + (three.get(1).getY() - instance.getY())) +
                   ((three.get(2).getX() - instance.getX()) + (three.get(2).getY() - instance.getY())) +
                   ((three.get(3).getX() - instance.getX()) + (three.get(3).getY() - instance.getY()));
        */
        
        for(int ctr = 0; ctr < instance.getAttributes().size(); ctr++){
            instance.setTempdistance(instance.getTempdistance() + ((three.get(0).getAttributes().get(ctr) - instance.getAttributes().get(ctr)) +
                                                                   (three.get(1).getAttributes().get(ctr) - instance.getAttributes().get(ctr)) +
                                                                   (three.get(2).getAttributes().get(ctr) - instance.getAttributes().get(ctr))));
        }
        
    }
    
    public LinkedList <SongInstance> Recommend(){ 
        topten = new LinkedList<>();
        three = Convert();
        
        for(int ctr = 0; ctr < instances.size(); ctr++){
            euclideanDistance(instances.get(ctr));
            //if distance is least then topten.add(instance.get(ctr));
        }          
        
        Collections.sort(instances, new distanceComparator());
        
        for(int ctr = 0; ctr < 10; ctr++){
            topten.add(instances.get(ctr));
        }
        return topten;
    }
    
    class distanceComparator implements Comparator<SongInstance> {
        
        @Override
        public int compare(SongInstance a, SongInstance b) {
           return a.getTempdistance() < b.getTempdistance() ? -1 : a.getTempdistance() == b.getTempdistance() ? 0 : 1;
    }
}
    
    
}
