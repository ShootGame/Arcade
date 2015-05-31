/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.perform;

import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Aleksander
 */
public abstract class Perform implements Runnable {
    private final FileConfiguration config;
    private final String id;
    private final String name;
    private int seconds;
    private final boolean repeating;
    private int times;
    
    public Perform(String name, FileConfiguration config, String id, int seconds, boolean repeating, int times) {
        this.config = config;
        this.id = id;
        this.name = name;
        this.seconds = seconds;
        this.repeating = repeating;
        this.times = times;
    }
    
    public void destroy() {
        this.setSeconds(-1);
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public String getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSeconds() {
        return this.seconds;
    }
    
    public int getTimes() {
        return this.times;
    }
    
    public boolean isEnabled() {
        return this.seconds > -1;
    }
    
    public boolean isRepeating() {
        return this.repeating;
    }
    
    public void removeTime() {
        this.times--;
        
        if (!this.isRepeating() && this.times <= 0) {
            this.destroy();
        }
    }
    
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
