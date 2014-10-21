/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Lester Chong
 */
public class UtilityRow {
    private String name;
    private double playMethod;
    private int playedDay;
    private int playedWeek;
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
     * @return the playedWeek
     */
    public int getPlayedWeek() {
        return playedWeek;
    }

    /**
     * @param playedWeek the playedWeek to set
     */
    public void setPlayedWeek(int playedWeek) {
        this.playedWeek = playedWeek;
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

}
