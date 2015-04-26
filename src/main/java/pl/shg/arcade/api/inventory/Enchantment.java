/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.inventory;

/**
 *
 * @author Aleksander
 */
public class Enchantment extends EnchantmentType {
    private int level;
    
    public Enchantment(EnchantmentType type) {
        super(type.getEID(), type.getMaxLevel(), type.getName());
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
}
