package com.MamaDevalayam.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";
//    public static String PASSWORD_PATTERN = "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*[*()_|<>?{}\\[\\]~;:,/.?@#$%^!&+={|}])(?=\\S+$).{8,}";
    public static String PASSWORD_PATTERN = "(?=.*?\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*?[a-zA-Z])(?=.*[*()_|<>?{}\\[\\]~;:,/.?@#$%^!&+={|}])(?=\\S+$).{8,}";
//    public static String PASSWORD_PATTERN = "^" +
//        "(?=.*[0-9])" +         //at least 1 digit
//        "(?=.*[a-z])" +         //at least 1 lower case letter
//        "(?=.*[A-Z])" +         //at least 1 upper case letter
//        "(?=.*[a-zA-Z])" +      //any letter
//        "(?=.*[@#$%^&+=])" +    //at least 1 special character
//        "(?=\\S+$)" +           //no white spaces
//        ".{8,}" +               //at least 8 characters
//        "$";
//    public static String NAME_PATTERN = "^(?!\\d+$)(?:[a-zA-Z0-9][a-zA-Z0-9 @&$]*)?$";
    public static String NAME_PATTERN = "[a-zA-Z /-]+";
    public static String NAMEPATTERN = "[a-zA-Z ]+";
//            "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[a-zA-Z0-9])(?=.*[@#$%]).{6,20})";
    //  public static String PASSWORD_PATTERN="(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,50})$";

    public static boolean CheckPasswordPattern(String pwd){
        Pattern pattern = Pattern.compile(Validation.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }
}
