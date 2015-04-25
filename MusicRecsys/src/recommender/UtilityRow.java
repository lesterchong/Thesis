/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recommender;



/**
 *
 * @author Lester Chong
 */

/** 
 * @file UtilityRow.java
 * 
 * @brief This file is the basic object for the utility matrix.
 * 
 **/ 

/** 
 * @class UtilityRow
 * 
 * @brief This class contains the methods for manipulating the values of an instance in the utility matrix.
 * 
 * This class contains the getters and setters for manipulating the values of an instance in the utility matrix.
 * Attributes such as the id and name of the song, computed value for the play methods, boolean values if the instance has been played for the day, month, or at all, and if it has been skipped. 
 * 
 */ 
public class UtilityRow {
    private int ID;
    private String name;
    private double playMethod;
    private int playedDay;
    private int playedMonth;
    private int playedEver;
    private double skipped;

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

    /**
     * @return the playMethod
     */
    public double getPlayMethod() {
        return playMethod;
    }

    /**
     * @param playMethod the playMethod to set
     */
    public void setPlayMethod(double playMethod) {
        this.playMethod = playMethod;
    }

    /**
     * @return the playedDay
     */
    public int getPlayedDay() {
        return playedDay;
    }

    /**
     * @param playedDay the playedDay to set
     */
    public void setPlayedDay(int playedDay) {
        this.playedDay = playedDay;
    }

    /**
     * @return the playedMonth
     */
    public int getPlayedMonth() {
        return playedMonth;
    }

    /**
     * @param playedMonth the playedMonth to set
     */
    public void setPlayedMonth(int playedMonth) {
        this.playedMonth = playedMonth;
    }

    /**
     * @return the playedEver
     */
    public int getPlayedEver() {
        return playedEver;
    }

    /**
     * @param playedEver the playedEver to set
     */
    public void setPlayedEver(int playedEver) {
        this.playedEver = playedEver;
    }

    /**
     * @return the skipped
     */
    public double getSkipped() {
        return skipped;
    }

    /**
     * @param skipped the skipped to set
     */
    public void setSkipped(double skipped) {
        this.skipped = skipped;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
