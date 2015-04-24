package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import javazoom.jlgui.player.amp.playlist.BasePlaylist;
import javazoom.jlgui.player.amp.playlist.Playlist;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;

 /**
 *
 * @author mikeyboy213
 */

 /** 
 * @file Recommendation.java
 * 
 * @brief Contains the class for recommending songs.
 * 
 **/ 

 /** 
 * @class Recommendation
 * 
 * @brief Contains the methods for recommending songs to the user.
 * 
 * This class is in charge of the whole process of recommendation wherein the song instances are read from the .arff file, clustered, then ranked. After which the ten songs with the lowest Euclidean distance (with the three top utility value songs) from all instances are recommended to the user.
 */ 
public class Recommendation {
    private UtilityMatrix matrix;
    private LinkedList <SongInstance> instances;
    private LinkedList <SongInstance> topten;
    private LinkedList <SongInstance> three;
    private Scanner scan;
    private String line, tokens[];
    private int counter = 0;
    private SongInstance temp;
    private LinkedList <Float> tempattributes;
    private LinkedList<SongInstance> cluster0;
    private LinkedList<SongInstance> cluster1;
    private LinkedList<SongInstance> cluster2;
    private LinkedList<SongInstance> cluster3;
    private float defNumber = 0;
    
    /**
    * @brief Constructor of the recommendation class.
    * 
    * This constructor is in charge of obtaining the utility matrix values of all song instances which will be used later on for computing the recommendation algorithm.
    */
    public Recommendation(UtilityMatrix matrix){
        this.matrix = matrix;
        
        try{
            readFromFile();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Cannot find file.");
        }
        
    }
    
    /**
    * @brief Reads the .arff file.
    * 
    * This method reads the .arff file and saves the values found in the file into different variables The Cluster() method is also called in this method.
    */
    private void readFromFile() throws FileNotFoundException{
        instances = new LinkedList<>();
        scan = new Scanner(new File("data/Song Attribute Values.arff"));
        
        for(int ctr = 1; ctr <= 668; ctr++){
            scan.nextLine();
        }
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
    
    /**
    * @brief Clusters the song instances.
    * 
    * This method clusters the song instances into the four different clusters. The clusters are not explicitly used in the code but instead are used for analyzing the listening behaviors of the users.
    */
    private void Cluster (LinkedList<SongInstance> list){
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
    
    /**
    * @brief Converts the UtilityRow values.
    * 
    * This method converts the top three highest utility value UtilityRow instances into SongInstance instances so that it can be used for the computation of the Euclidean distances in the Recommendation class.
    */
    private LinkedList<SongInstance> Convert(LinkedList <UtilityRow> topthree){
        
        LinkedList<SongInstance> temp = new LinkedList<>();
        int id;
        
        for(int ctr = 0; ctr < topthree.size(); ctr++){
            id = topthree.get(ctr).getID();
            
            for(int ctr2 = 0; ctr2 < instances.size(); ctr2++){
                
                if(id == instances.get(ctr2).getId()){
                    temp.add(instances.get(ctr2));
                    //break;
                }
            }
        }
        return temp;
    }
    
    /**
    * @brief Computes for the Euclidean distance
    * 
    * This method computes for the Euclidean distance of all the song instances with the three songs with the highest utility values.
    */
    private void euclideanDistance(SongInstance instance){
        instance.setTempdistance(0);
        /*
        distance = ((three.get(1).getX() - instance.getX()) + (three.get(1).getY() - instance.getY())) +
                   ((three.get(2).getX() - instance.getX()) + (three.get(2).getY() - instance.getY())) +
                   ((three.get(3).getX() - instance.getX()) + (three.get(3).getY() - instance.getY()));
        */
        
        for(int ctr = 0; ctr < instance.getAttributes().size(); ctr++){
            instance.setTempdistance(instance.getTempdistance() + Math.abs((three.get(0).getAttributes().get(ctr) - instance.getAttributes().get(ctr)) +
                                                                   (three.get(1).getAttributes().get(ctr) - instance.getAttributes().get(ctr)) +
                                                                   (three.get(2).getAttributes().get(ctr) - instance.getAttributes().get(ctr))));
        }
        
    }
    
    /**
    * @brief Recommends the songs to the users.
    * 
    * This method returns the ten songs with the lowest Euclidean distances to the music player so it can be evaluated by the user. This method makes use of the Convert() and EuclideanDistance() methods.
    */
    public LinkedList <SongInstance> Recommend(){
        LinkedList<UtilityRow> topThree = matrix.topThreeUtility();
        three = Convert(topThree);
        topten = new LinkedList<>();
        
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
    
    /**
    * @brief Generates a play list with the top ten songs.
    * 
    * This method generates the play list containing the top ten songs returned by the Recommend() method. The Recommend() and GetSongNameFromList() methods are called in this method.
    */
    public Playlist generatePlaylist(){
        PlaylistItem item;
        String filename, directory;
        File file = new File("");
        Playlist playlist = new BasePlaylist();
        LinkedList<SongInstance> list = Recommend();
        
        directory = file.getAbsolutePath();
        for(int ctr=0; ctr<list.size(); ctr++){
            filename = getSongNameFromList(list.get(ctr).getId());
            //Must change during deployment
            item = new PlaylistItem(filename+".wav", directory+"\\Song Repository\\"+filename+".wav", -1, true);
            playlist.appendItem(item);
        }
        playlist.save("recommend.m3u");
        return playlist;
    }
    
    /**
    * @brief Gets the song names from a text file.
    * 
    * This method gets the song names of the songs that are to be recommended. The song names from a text file and is matched to the songs from the file repository for recommendation.
    */
    private String getSongNameFromList(int index){
        File file = new File("data/Song Name Database.txt");
        Scanner scan;
        String line="";
        try{
            scan = new Scanner(file);
            for(int ctr=0; ctr<index; ctr++){
                line = scan.nextLine();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println(file.getName() + " was not found");
        }
        return line;
    }
    
    class distanceComparator implements Comparator<SongInstance> {
        
        @Override
        public int compare(SongInstance a, SongInstance b) {
           return a.getTempdistance() < b.getTempdistance() ? -1 : a.getTempdistance() == b.getTempdistance() ? 0 : 1;
    }
}
    
    
}
