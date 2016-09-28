
package model;

import dataAccessObject.RoleDao;

/**
 * This class is to create bean for role
 * @author Hanwei Cheng
 */

public class Role {
        private int id;
	private String role;
        private RoleDao dao;
 
        public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
        
        //create a regular role
        public Role(){
            dao = new RoleDao();
            this.id = dao.getUserID();
            System.out.println("== new role: " + id + "==");
        }
        
        
}
