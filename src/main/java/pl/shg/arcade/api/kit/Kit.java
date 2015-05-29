/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.kit;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class Kit {
    private final String id;
    private final List<KitItem> items = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();
    
    public Kit(String id) {
        Validate.notNull(id, "id can not be null");
        this.id = id;
    }
    
    public String getID() {
        return this.id;
    }
    
    // Items
    public List<KitItem> getItems() {
        return this.items;
    }
    
    public boolean hasItems() {
        return !this.items.isEmpty();
    }
    
    public void registerItem(KitItem item) {
        Validate.notNull(item, "item can not be null");
        this.items.add(item);
    }
    
    // Options
    public List<Option> getOptions() {
        return this.options;
    }
    
    public boolean hasOptions() {
        return !this.options.isEmpty();
    }
    
    public void registerOption(Option option) {
        Validate.notNull(option, "option can not be null");
        this.options.add(option);
    }
}
