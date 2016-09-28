
package model;

import dataAccessObject.SecurityQuestionDao;

/**
 *This class is to create a bean for security question
 * @author Hanwei Cheng
 */
public class SecurityQuestion {
    private int id;
    private String question;
    private SecurityQuestionDao dao;
    
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    

    
}
