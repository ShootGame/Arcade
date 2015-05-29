/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.documentation;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class ConfigurationDoc {
    private final boolean required;
    private final Type type;
    
    public ConfigurationDoc(boolean required, Type type) {
        Validate.notNull(type, "type can not be null");
        this.required = required;
        this.type = type;
    }
    
    public String getPrefix() {
        return null;
    }
    
    public abstract String[] getCode();
    
    public String getSuffix() {
        return null;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public boolean isRequired() {
        return this.required;
    }
    
    public static enum Type {
        BOOLEAN,
        ENTITY,
        ID,
        INT,
        MESSAGE,
        MESSAGE_ERROR,
        MESSAGE_SUCCESS,
        SECONDS,
        TICKS,
        WINNER
        ;
    }
}
