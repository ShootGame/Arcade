/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class BroadcasterSettings {
    private String format;
    private int time;
    
    public String getFormat() {
        return this.format;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public void setFormat(String format) {
        Validate.notNull(format, "format can not be null");
        this.format = format;
    }
    
    public void setTime(int time) {
        Validate.notNegative(time, "time must be positive");
        Validate.notZero(time, "time can not be zero");
        this.time = time;
    }
}
