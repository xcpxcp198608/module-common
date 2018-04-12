package com.px.common.utils;

import java.util.regex.Pattern;

/**
 * regular util
 */
public class RegularUtil {

    /**
     * validate email input format
     */
    public static boolean validateEmail(String email){
        String regular = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regular);
        return pattern.matcher(email).matches();
    }

    /**
     * validate IP address input format
     */
    private boolean validateIP(String ip){
        String pattern = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern compile = Pattern.compile(pattern);
        return compile.matcher(ip).matches();
    }
}
