/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author shao dai
 */
public class ResetPasswordObj {
    public String userID;
    public String securityAnswerID;
    public String securityQuestion;
    
    public ResetPasswordObj(String userID, String securityAnswerID, String securityQuestion) {
        this.userID = userID;
        this.securityAnswerID = securityAnswerID;
        this.securityQuestion = securityQuestion;
    }
}
