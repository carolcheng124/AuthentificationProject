/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Siwei Jiao
 */
public class IPAddress {
    private String ipAddress;
    
    
    //the method to get client ip address is learned from http://stackoverflow.com/questions/16558869/getting-ip-address-of-client
    private static final String[] HEADERS_LIST = { 
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR" };
    
    public String getClientIpAddress(HttpServletRequest request) {
        String ipAddr; 
        for (String header : HEADERS_LIST) {
            //get the ip addr of client system that might behind a proxy server
            ipAddr = request.getHeader(header);             
            if (ipAddr != null && ipAddr.length() != 0 && !ipAddr.equalsIgnoreCase("unknown")) {
                return ipAddr;
            }
        }
        //return ip address of end client that make request directly to the web application
        return request.getRemoteAddr();
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
