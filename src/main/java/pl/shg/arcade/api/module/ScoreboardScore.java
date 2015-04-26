/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ScoreboardScore {
    private String name;
    private int score;
    
    public ScoreboardScore(String name) throws IllegalArgumentException {
        this.setName(name);
        this.setScore(0);
    }
    
    public ScoreboardScore(String name, int score) throws IllegalArgumentException {
        this.setName(name);
        this.setScore(score);
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public boolean isSeparator() {
        return this.getName().equals("");
    }
    
    public final void setName(String name) {
        Validate.notNull(name, "name can not be null");
        Validate.isTrue(name.length() > 16, "name is too long");
        this.name = name;
    }
    
    public final void setScore(int score) throws IllegalArgumentException {
        Validate.notNegative(score, "score can not be negative");
        this.score = score;
    }
}
