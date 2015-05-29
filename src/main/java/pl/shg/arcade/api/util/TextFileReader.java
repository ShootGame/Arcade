/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class TextFileReader {
    private List<Line> lines;
    
    public TextFileReader(List<String> lines) {
        Validate.notNull(lines, "lines can not be null");
        this.read(lines);
    }
    
    public List<Line> getLines() {
        return this.lines;
    }
    
    public String getSetting(String path) {
        return this.getSetting(path, null);
    }
    
    public String getSetting(String path, String def) {
        Validate.notNull(path, "path can not be null");
        for (Line line : this.lines) {
            if (line.isSetting()) {
                String value = line.getValue();
                String[] list = value.split("=", 2);
                if (list[0].toLowerCase().equals(path.toLowerCase())) {
                    return list[1];
                }
            }
        }
        return def;
    }
    
    private void read(List<String> lines) {
        Validate.notNull(lines, "lines can not be null");
        this.lines = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("#")) {
                // Comment line - do nothing
            } else {
                this.lines.add(new Line(line));
            }
        }
    }
    
    public class Line {
        private final String value;
        
        public Line(String value) {
            Validate.notNull(value, "value can not be null");
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public boolean isSetting() {
            return this.getValue().contains("=");
        }
    }
}
