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
public class Enchantment extends EnchantmentType {
    private int level;
    
    public Enchantment(EnchantmentType type) {
        this(type, 0);
    }
    
    public Enchantment(EnchantmentType type, int level) {
        super(type.getEID(), type.getMaxLevel(), type.getName());
        Validate.isTrue(level >= 0);
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
}
