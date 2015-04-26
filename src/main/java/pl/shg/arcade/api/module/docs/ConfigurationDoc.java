/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module.docs;

/**
 *
 * @author Aleksander
 */
public abstract class ConfigurationDoc {
    public String getPrefix() {
        return null;
    }
    
    public abstract String[] getCode();
    
    public String getSuffix() {
        return null;
    }
    
    public boolean required() {
        return false;
    }
}
