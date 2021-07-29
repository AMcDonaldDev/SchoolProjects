package org.cmsc495.bpo.util;

public class StringUtility {

    /**
     * Replace braces with each Object consecutively. For example:
     * 
     * <p>String message = "I want {} for {}";</p>
     * 
     * <p>debrace(message, "Pancakes", "Breakfast") returns the String
     * "I want Pancakes for Breakfast"</p>
     * 
     * @param message
     * @param objects
     * @return
     */
    public static String debrace(final String message, Object... objects) {
        String msg = message;
        for (Object o : objects) {
            msg = msg.replaceFirst("\\{\\}", o.toString());
        }
        return msg;
    } 
}
