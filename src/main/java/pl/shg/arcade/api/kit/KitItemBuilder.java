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
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.item.Enchantment;
import pl.shg.arcade.api.item.Item;

/**
 *
 * @author Aleksander
 */
public class KitItemBuilder {
    private final String id;
    private final Material type;
    
    private int amount = Item.DEFAULT_AMOUNT;
    private KitData data;
    private String name;
    private List<String> description;
    private List<Enchantment> enchantments = new ArrayList<>();
    private int slot;
    
    public KitItemBuilder(String id, Material type) {
        Validate.notNull(id, "id can not be null");
        Validate.notNull(type, "type can not be null");
        this.id = id;
        this.type = type;
    }
    
    public KitItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }
    
    public KitItemBuilder data(KitData data) {
        this.data = data;
        return this;
    }
    
    public KitItemBuilder description(List<String> description) {
        if (description == null) {
            description = new ArrayList<>();
        }
        this.description = description;
        return this;
    }
    
    public KitItemBuilder enchantment(Enchantment enchantment) {
        Validate.notNull(enchantment, "enchantment can not be null");
        this.enchantments.add(enchantment);
        return this;
    }
    
    public KitItemBuilder enchantments(List<Enchantment> enchantments) {
        if (enchantments == null) {
            enchantments = new ArrayList<>();
        }
        this.enchantments = enchantments;
        return this;
    }
    
    public KitItemBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public KitItemBuilder slot(int slot) {
        if (slot > 39) {
            throw new IllegalArgumentException("Slot ID can not be greater than 39");
        }
        this.slot = slot;
        return this;
    }
    
    public KitItem toItem() {
        KitItem item = new KitItem(this.id, this.type, this.amount);
        item.setAmount(this.amount);
        item.setData(this.data);
        item.setDescription(this.description);
        item.setName(this.name);
        item.setSlot(this.slot);
        for (Enchantment enchantment : this.enchantments) {
            item.addEnchantment(enchantment);
        }
        return item;
    }
}
