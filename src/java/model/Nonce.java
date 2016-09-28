/*
 * This class is a model of nonce object.
 */
package model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Siwei Jiao
 */
public class Nonce {
    private int nonceID;
    private BigDecimal nonceValue;
    private long timeStampsID;
    private int accountInfoID;

    /**
     * @return the nonceID
     */
    public int getNonceID() {
        return nonceID;
    }

    /**
     * @param nonceID the nonceID to set
     */
    public void setNonceID(int nonceID) {
        this.nonceID = nonceID;
    }

    /**
     * @return the nonceValue
     */
    public BigDecimal getNonceValue() {
        return nonceValue;
    }

    /**
     * @param nonceValue the nonceValue to set
     */
    public void setNonceValue(BigDecimal nonceValue) {
        this.nonceValue = nonceValue;
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
    
    
}
