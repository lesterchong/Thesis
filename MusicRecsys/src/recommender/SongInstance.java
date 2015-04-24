/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommender;

import java.util.LinkedList;

/**
 *
 * @author mikeyboy213
 */
public class SongInstance {
    private int id;
    private String name;
    private LinkedList <Float> attributes;
    private String cluster;
    private int x, y;
    private float tempdistance;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cluster
     */
    public String getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the attributes
     */
    public LinkedList <Float> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(LinkedList <Float> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the tempdistance
     */
    public float getTempdistance() {
        return tempdistance;
    }

    /**
     * @param tempdistance the tempdistance to set
     */
    public void setTempdistance(float tempdistance) {
        this.tempdistance = tempdistance;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
