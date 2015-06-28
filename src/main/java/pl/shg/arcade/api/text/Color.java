/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.text;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;

/**
 *
 * @author Aleksander
 */
public final class Color {
    public static final char CONFIGURATION_COLOR = '`';
    public static final char SECTION_SIGN = '\u00A7'; // section sign
    
    public static final String BLACK = create('0');
    public static final String DARK_BLUE = create('1');
    public static final String DARK_GREEN = create('2');
    public static final String DARK_AQUA = create('3');
    public static final String DARK_RED = create('4');
    public static final String DARK_PURPLE = create('5');
    public static final String GOLD = create('6');
    public static final String GRAY = create('7');
    public static final String DARK_GRAY = create('8');
    public static final String BLUE = create('9');
    public static final String GREEN = create('A');
    public static final String AQUA = create('B');
    public static final String RED = create('C');
    public static final String LIGHT_PURPLE = create('D');
    public static final String YELLOW = create('E');
    public static final String WHITE = create('F');
    
    public static final String OBFUSCATED = create('K');
    public static final String BOLD = create('L');
    public static final String STRIKETHROUGH = create('M');
    public static final String UNDERLINE = create('N');
    public static final String ITALIC = create('O');
    public static final String RESET = create('R');
    
    private final char section, color;
    
    public Color(char color) {
        this(Color.SECTION_SIGN, color);
    }
    
    protected Color(char section, char color) {
        Validate.notNull(section);
        Validate.notNull(color);
        this.section = section;
        this.color = color;
    }
    
    public char getSection() {
        return this.section;
    }
    
    public char getColor() {
        return this.color;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.section) + String.valueOf(this.color);
    }
    
    public static String create(char code) {
        return new Color(code).toString();
    }
    
    public static String fixColors(String message) {
        StringBuilder builder = new StringBuilder();
        if (message.contains(String.valueOf(Color.SECTION_SIGN))) { // We need to remove colors from this message
            boolean startsWith = message.startsWith(String.valueOf(Color.SECTION_SIGN));
            String[] split = message.split(String.valueOf(Color.SECTION_SIGN));
            for (int i = 0; i < split.length; i++) {
                String label = split[i];
                if (!startsWith && i == 0) {
                    builder.append(label);
                } else if (label.length() > 0) {
                    builder.append(label.substring(1));
                }
            }
        } else {
            builder.append(message);
        }
        return builder.toString();
    }
    
    public static String translate(String message) {
        Validate.notNull(message);
        return Color.translate(message, Color.CONFIGURATION_COLOR);
    }
    
    public static String translate(String message, char code) {
        Validate.notNull(message);
        Validate.notNull(code);
        return message.replace(String.valueOf(code), String.valueOf(Color.SECTION_SIGN));
    }
    
    public static class Wool {
        public static final Material WOOL = new Material(35);
        
        public static final Wool WHITE = new Wool(0, "White");
        public static final Wool ORANGE = new Wool(1, "Orange");
        public static final Wool MAGNETA = new Wool(2, "Magenta");
        public static final Wool LIGHT_BLUE = new Wool(3, "Light Blue");
        public static final Wool YELLOW = new Wool(4, "Wool");
        public static final Wool LIME = new Wool(5, "Lime");
        public static final Wool PINK = new Wool(6, "Pink");
        public static final Wool GRAY = new Wool(7, "Gray");
        public static final Wool LIGHT_GRAY = new Wool(8, "Light Gray");
        public static final Wool CYAN = new Wool(9, "Cyan");
        public static final Wool PURPLE = new Wool(10, "Purple");
        public static final Wool BLUE = new Wool(11, "Blue");
        public static final Wool BROWN = new Wool(12, "Brown");
        public static final Wool GREEN = new Wool(13, "Green");
        public static final Wool RED = new Wool(14, "Red");
        public static final Wool BLACK = new Wool(15, "Black");
        
        private final int id;
        private final String name;
        
        public Wool(int id, String name) {
            Validate.isTrue(id >= 0);
            Validate.notNull(name);
            this.id = id;
            this.name = name;
        }
        
        public int getID() {
            return this.id;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Material toMaterial() {
            return new Material(WOOL.getID(), this.getID());
        }
    }
}
