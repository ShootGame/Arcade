/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.kit;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.item.Item;

/**
 *
 * @author Aleksander
 */
public class KitItem extends Item {
    private KitData data;
    private final String id;
    private int slot = -1;
    
    public KitItem(String id, Material type, int amount) {
        super(type, amount);
        Validate.notNull(id, "id can not be null");
        this.id = id;
    }
    
    public KitData getData() {
        return this.data;
    }
    
    public String getID() {
        return this.id;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public boolean hasData() {
        return this.data != null;
    }
    
    public boolean hasSlot() {
        return this.slot >= 0;
    }
    
    public void setData(KitData data) {
        this.data = data;
    }
    
    public void setSlot(int slot) {
        this.slot = slot;
    }
}
