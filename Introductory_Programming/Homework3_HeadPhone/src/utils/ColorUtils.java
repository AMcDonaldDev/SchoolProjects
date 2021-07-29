/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 *
 * @author Castillo
 */
public class ColorUtils {

    /**
     * Returns String name from a Color object or null if none found 
     * @param c
     * @return 
     */
    public static String colorName(Color c) {
        for (Field f : Color.class.getDeclaredFields()) {
            if (f.getType().equals(Color.class)) {
                try {
                    if (f.get(null).equals(c)) {
                        return f.getName().toLowerCase();
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Returns a Color object from a String color name or null if none found
     * @param colorName
     * @return 
     */
    public static Color getColor(String colorName) {
        for (Field f : Color.class.getDeclaredFields()) {
            if (f.getType().equals(Color.class)) {
                try {
                    String fColor = f.getName();
                    if (fColor.equalsIgnoreCase(colorName)) {
                        try {
                            Field field = Class.forName("java.awt.Color").getField(colorName.toLowerCase());
                            return (Color) field.get(null);
                        } catch(Exception e0)  {
                            e0.printStackTrace();
                            return null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
