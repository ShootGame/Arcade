/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

/**
 *
 * @author Aleksander
 */
public class Time {
    private long end = 0;
    private final long start;
    
    public Time() {
        this.start = System.currentTimeMillis();
    }
    
    public long getEndMs() {
        return this.end;
    }
    
    public long getStartMs() {
        return this.start;
    }
    
    public long getValue() {
        long ms = System.currentTimeMillis();
        if (!this.isRunning()) {
            ms = this.getEndMs();
        }
        return ms - this.start;
    }
    
    public boolean isRunning() {
        return this.end != 0;
    }
    
    public void stop() {
        this.end = System.currentTimeMillis();
    }
}
