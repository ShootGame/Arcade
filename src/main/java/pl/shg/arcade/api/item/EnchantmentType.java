/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.item;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class EnchantmentType {
    private final int eid;
    private final int maxLevel;
    private final String name;
    
    public EnchantmentType(int eid, int maxLevel, String name) {
        Validate.notNull(name, "name can not be null");
        this.eid = eid;
        this.name = name;
        this.maxLevel = maxLevel;
    }
    
    public int getEID() {
        return this.eid;
    }
    
    public int getMaxLevel() {
        return this.maxLevel;
    }
    
    public int getMinLevel() {
        return 1;
    }
    
    public String getName() {
        return this.name;
    }
}
