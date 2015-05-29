/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

/**
 *
 * @author Aleksander
 */
public enum Priority {
    MONITOR(Priority.getMintorLevel()),
    HIGHEST(2),
    HIGH(1),
    NORMAL(0),
    LOW(-1),
    LOWEST(-2),
    ;
    
    private final int level;
    
    private Priority(int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public boolean isCancelable() {
        return this.level < Priority.getCancelableLevel();
    }
    
    public boolean isMonitor() {
        return this.getLevel() == Priority.getMintorLevel();
    }
    
    public static int getCancelableLevel() {
        return 1000;
    }
    
    public static int getMintorLevel() {
        return Integer.MAX_VALUE;
    }
}
