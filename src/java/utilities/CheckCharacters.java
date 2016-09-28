/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author shao
 */
public  class CheckCharacters {
    public static boolean checkSpecialCharExist2(String input) {
        Pattern p = Pattern.compile(input).compile("[^a-zA-Z0-9-_@./ ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.find();
    }
    
    public static boolean checkSpecialCharExist(String input) {
        Pattern p = Pattern.compile(input).compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.find();
    }
}
