/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
@Deprecated
public final class BarMessage {
    private long health;
    private String message;
    private int seconds;
    
    public BarMessage(String message) {
        this.setMessage(message);
    }
    
    public BarMessage(String message, long health) {
        this.setMessage(message);
        this.setHealth(health);
    }
    
    public BarMessage(String message, int seconds) {
        this.setMessage(message);
        this.setSeconds(seconds);
    }
    
    public long getHealth() {
        return this.health;
    }
    
    public void setHealth(long health) {
        Validate.notNegative(health, "health can not be negative");
        Validate.notZero(health, "health can not be zero");
        this.health = health;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        Validate.notNull(message, "message can not be null");
        this.message = message;
    }
    
    public int getSeconds() {
        return this.seconds;
    }
    
    public void setSeconds(int seconds) {
        Validate.notNegative(seconds, "seconds can not be negative");
        Validate.notZero(seconds, "seconds can not be zero");
        this.seconds = seconds;
    }
}
