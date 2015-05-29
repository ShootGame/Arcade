/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.item;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;

/**
 *
 * @author Aleksander
 */
public class Item {
    public static final int DEFAULT_AMOUNT = 1;
    private Material type;
    private int amount;
    
    private String name;
    private List<String> description = new ArrayList<>();
    private final List<Enchantment> enchantments = new ArrayList<>();
    
    public Item(Material type) {
        this(type, Item.DEFAULT_AMOUNT);
    }
    
    public Item(Material type, int amount) {
        this.setType(type);
        this.setAmount(amount);
    }
    
    // Type
    public Material getType() {
        return this.type;
    }
    
    public final void setType(Material type) {
        Validate.notNull(type, "type can not be null");
        this.type = type;
    }
    
    // Amount
    public int getAmount() {
        return this.amount;
    }
    
    public final void setAmount(int amount) {
        this.amount = amount;
    }
    
    // Name
    public String getName() {
        return this.name;
    }
    
    public boolean hasName() {
        return this.name != null;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Description
    public List<String> getDescription() {
        return this.description;
    }
    
    public boolean hasDescription() {
        return !this.description.isEmpty();
    }
    
    public void setDescription(List<String> description) {
        Validate.notNull(description, "description");
        this.description = description;
    }
    
    // Enchantment
    public void addEnchantment(Enchantment enchantment) {
        Validate.notNull(enchantment, "enchantment can not be null");
        this.enchantments.add(enchantment);
    }
    
    public List<Enchantment> getEnchantments() {
        return this.enchantments;
    }
    
    public boolean hasEnchantments() {
        return !this.enchantments.isEmpty();
    }
}
