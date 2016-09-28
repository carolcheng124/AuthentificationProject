package model;


/**
 *
 * @modified by Carol
 */

import dataAccessObject.TimeStampDao;
import dataAccessObject.UserDao;

public class UserAccountInfo {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long timeStampsID;
    private int access_role_id;
    private UserDao dao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getTimeStampsID() {
        return timeStampsID;
    }

    public void setTimeStampsID(long timeStampsID) {
        this.timeStampsID = timeStampsID;
    }

    public int getAccess_role_id() {
        return access_role_id;
    }

    public void setAccess_role_id(int access_role_id) {
        this.access_role_id = access_role_id;
    }

    public UserDao getUserDao() {
        return dao;
    }

    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    //create new account record in account_info table
    public int register(String firstname, String lastname, String email, int roleID) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.emailAddress = email;
        this.access_role_id = roleID;
        
        UserDao user = new UserDao();
        System.out.println("===== user bean " + "firstname: " + firstName + " lastname: " + lastName+ "email: " +emailAddress + "roleID: " + roleID + "====");
        //create account and return the generated id
        return this.id = user.createAccount(firstName,lastName, emailAddress, access_role_id);
    }
    
    //this method is to change the roleID for user
    public Boolean roleUpdate(int accountID, int roleID) {
       UserDao dao = new UserDao();
       Boolean res = dao.roleIDChange(accountID, roleID);
       
       //update timestamps associated with the user account
        TimeStampDao timeStampDao = new TimeStampDao();
        timeStampDao.updateTimestampOnProfileChange(accountID);
        
       System.out.println("role change successful? " + res);
       return res;
    }

}
