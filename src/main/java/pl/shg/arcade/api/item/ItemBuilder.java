/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.Builder;
import pl.shg.arcade.api.Material;

/**
 *
 * @author Aleksander
 */
public class ItemBuilder implements Builder<Item> {
    private Material type;
    private int amount;
    private List<String> description;
    private String name;
    private final List<Enchantment> enchantments;
    private boolean unbreakable;
    
    public ItemBuilder() {
        this.enchantments = new ArrayList<>();
    }
    
    public ItemBuilder type(Material type) {
        this.type = type;
        return this;
    }
    
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }
    
    public ItemBuilder description(String... description) {
        this.description = Arrays.asList(description);
        return this;
    }
    
    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public ItemBuilder enchantment(Enchantment enchantment) {
        Validate.notNull(enchantment, "enchantment can not be null");
        this.enchantments.add(enchantment);
        return this;
    }
    
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }
    
    @Override
    public Item build() {
        Validate.notNull(this.type, "type can not be null");
        
        Item item = new Item(this.type, this.amount);
        item.setDescription(this.description);
        item.setName(this.name);
        
        for (Enchantment enchantment : this.enchantments) {
            item.addEnchantment(enchantment);
        }
        
        item.setUnbreakable(this.unbreakable);
        return item;
    }
}
