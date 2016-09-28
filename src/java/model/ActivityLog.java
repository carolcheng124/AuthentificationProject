/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Siwei Jiao
 */
public class ActivityLog {
    private int logID;
    private String ipAddr;
    private String systemSource;
    private int activityCount;
    private long timeStampsID;
    private int accountInfoID;
    private String description;

    /**
     * @return the logID
     */
    public int getLogID() {
        return logID;
    }

    /**
     * @param logID the logID to set
     */
    public void setLogID(int logID) {
        this.logID = logID;
    }

    /**
     * @return the ipAddr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * @param ipAddr the ipAddr to set
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    /**
     * @return the systemSource
     */
    public String getSystemSource() {
        return systemSource;
    }

    /**
     * @param systemSource the systemSource to set
     */
    public void setSystemSource(String systemSource) {
        this.systemSource = systemSource;
    }

    /**
     * @return the activityCount
     */
    public int getActivityCount() {
        return activityCount;
    }

    /**
     * @param activityCount the activityCount to set
     */
    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    /**
     * @return the timeStampsID
     */
    public long getTimeStampsID() {
        return timeStampsID;
    }

    /**
     * @param timeStampsID the timeStampsID to set
     */
    public void setTimeStampsID(long timeStampsID) {
        this.timeStampsID = timeStampsID;
    }

    /**
     * @return the accountInfoID
     */
    public int getAccountInfoID() {
        return accountInfoID;
    }

    /**
     * @param accountInfoID the accountInfoID to set
     */
    public void setAccountInfoID(int accountInfoID) {
        this.accountInfoID = accountInfoID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
