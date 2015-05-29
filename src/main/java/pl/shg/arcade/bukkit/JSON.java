/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class JSON {
    public static String textOf(String text) {
        Validate.notNull(text, "text can not be null");
        return toJSON(new String[] {"text"}, new String[] {text});
    }
    
    public static String toJSON(String[] variables, String[] values) {
        Validate.notNull(variables, "variables can not be null");
        Validate.notNull(values, "values can not be null");
        Validate.isTrue(variables.length == 0, "variables can not be empty");
        Validate.isTrue(variables.length > values.length, "length of values can not be smaller than length of variables");
        
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < variables.length; i++) {
            builder.append("\"").append(variables[i]).append("\": ");
            builder.append("\"").append(values[i]).append("\"");
            if (i < variables.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
