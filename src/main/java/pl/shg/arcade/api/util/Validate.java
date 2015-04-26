/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.util;

/**
 *
 * @author Aleksander
 */
public class Validate {
    public static void isTrue(boolean bool, String message) {
        if (bool) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notNegative(double d, String message) {
        isTrue(d < 0.0, message);
    }
    
    public static void notNegative(int i, String message) {
        isTrue(i < 0, message);
    }
    
    public static void notNegative(long l, String message) {
        isTrue(l < 0, message);
    }
    
    public static void notNull(Object o, String message) {
        isTrue(o == null, message); 
   }
    
    public static void notZero(double d, String message) {
        isTrue(d == 0.0, message);
    }
    
    public static void notZero(int i, String message) {
        isTrue(i == 0, message);
    }
    
    public static void notZero(long l, String message) {
        isTrue(l == 0, message);
    }
}
