/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author shaoNPC
 */
public class CheckDateTime {
    
    private static final int MAX_TIME_DIFF_MINS = 20;
    
    public static boolean isValid(Timestamp timestamp) {
        if (timestamp == null) {
            return false;
        }
        Date nonceDate = timestamp;
        long cDate = System.currentTimeMillis();
        long nDate = nonceDate.getTime();
        
        return (cDate - nDate) < (MAX_TIME_DIFF_MINS * 60000);
    }
    
}
