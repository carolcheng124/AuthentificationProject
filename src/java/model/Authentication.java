
package model;

import dataAccessObject.SavePasswordDao;

public class Authentication {
	private int id;
	private byte[] hash;
	private int account_info_id;
	private long timeStampsID;
        private String password_salt;
        private boolean active;

        

        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isActive() {
            return active;
        }

        public void setPassword_salt(String password_salt) {
            this.password_salt = password_salt;
        }

        public String getPassword_salt() {
            return password_salt;
        }
        
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	public int getAccount_info_id() {
		return account_info_id;
	}
	public void setAccount_info_id(int account_info_id) {
		this.account_info_id = account_info_id;
	}
	public long getTimeStampsID() {
		return timeStampsID;
	}
	public void setTimeStampsID(long timeStampsID) {
		this.timeStampsID = timeStampsID;
	}
	
   
    /**
    *This method is to create a authentication table record for new user
    * @author: Hanwei Cheng
    */     
        public void passwordProcess(int accountID, String password){
            SavePasswordDao savePasswordDao = new SavePasswordDao();
            Boolean res = savePasswordDao.setNewPassword(accountID, password); //changed!
            //System.out.println("===== Authentication bean " + " hash: " + hash + " salt: " + password_salt + " account_info_id: "+ account_info_id +" ====");
            //test
            System.out.println("==password&salt==" + res);
        }
        
}
