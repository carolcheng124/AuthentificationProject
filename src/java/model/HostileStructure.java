/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author Zhirun Tian
 */
public class HostileStructure {
    public String IPAddress;

    //from login, forget password or sequrity question
    public String SYSTEM_SOURCE;

    public HostileStructure() {
        this.SYSTEM_SOURCE = "";
    }
    
    public HostileStructure(int Attempts, String IPAddress, String SYSTEM_SOURCE) {
        this.IPAddress = IPAddress;
        this.SYSTEM_SOURCE = SYSTEM_SOURCE;
    }
    

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }



    public String getSYSTEM_SOURCE() {
        return SYSTEM_SOURCE;
    }

    public void setSYSTEM_SOURCE(String SYSTEM_SOURCE) {
        this.SYSTEM_SOURCE = SYSTEM_SOURCE;
    }
    
    
    
}
