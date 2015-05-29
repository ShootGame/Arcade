/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class ScoreboardScore {
    private String name, oldName, prefix, suffix;
    private int score;
    
    public ScoreboardScore(String name) throws IllegalArgumentException {
        this.setName(name);
        this.setScore(0);
    }
    
     public ScoreboardScore(String name, int score) throws IllegalArgumentException {
        this.setName(name);
        this.setScore(score);
    }
     
    public ScoreboardScore(String prefix, String suffix, String name, @Deprecated int score) throws IllegalArgumentException {
        this.setName(name);
        this.setPrefix(prefix);
        this.setScore(score);
        this.setSuffix(suffix);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getOldName() {
        return this.oldName;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    @Deprecated
    public int getScore() {
        return this.score;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public boolean isNameEdited() {
        if (this.getOldName() != null) {
            return !this.getOldName().equals(this.getName());
        }
        return false;
    }
    
    public boolean isSeparator() {
        return this.getName().equals("");
    }
    
    public final void setName(String name) {
        Validate.notNull(name, "name can not be null");
        Validate.isTrue(name.length() <= 16, "name is too long");
        this.oldName = this.name;
        this.name = name;
    }
    
    public final void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    @Deprecated
    public final void setScore(int score) {
        this.score = score;
    }
    
    public final void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
