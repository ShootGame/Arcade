/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;

/**
 *
 * @author Aleksander
 */
public class Menu extends MenuListener {
    private static final List<Menu> registeredMenus = new ArrayList<>();
    
    private final String name;
    private final int slots;
    private final UUID id;
    
    private final HashMap<Integer, Item> items;
    
    public Menu(String name) {
        this(name, 1);
    }
    
    public Menu(String name, int rows) {
        Validate.notNull(name, "name can not be null");
        Validate.isTrue(rows > 0);
        this.name = name;
        this.slots = 9 * rows;
        this.id = UUID.randomUUID();
        
        this.items = new HashMap<>();
    }
    
    public void clear() {
        this.items.clear();
    }
    
    public void close(Player player) {
        if (player != null) {
            player.close();
        }
    }
    
    public Object create() {
        return Arcade.getServer().createMenu(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSlots() {
        return this.slots;
    }
    
    public UUID getID() {
        return this.id;
    }
    
    public void addItem(Item item) {
        Validate.notNull(item, "item can not be null");
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i) == null) {
                this.addItem(item, this.getSlots());
                return;
            }
        }
    }
    
    public void addItem(Item item, int slot) {
        Validate.notNull(item, "item can not be null");
        Validate.isTrue(slot >= 0);
        this.items.put(slot, item);
    }
    
    public Item getItem(int slot) {
        Validate.isTrue(slot >= 0);
        return this.items.get(slot);
    }
    
    public HashMap<Integer, Item> getItems() {
        return this.items;
    }
    
    public void register() {
        Menu.register(this);
    }
    
    @Override
    public String toString() {
        return null;
    }
    
    public static List<Menu> getRegistered() {
        return registeredMenus;
    }
    
    public static void register(Menu menu) {
        Validate.notNull(menu, "menu can not be null");
        registeredMenus.add(menu);
    }
    
    public static boolean unregister(Menu menu) {
        Validate.notNull(menu, "menu can not be null");
        return registeredMenus.remove(menu);
    }
    
    public static void unregisterAll() {
        registeredMenus.clear();
    }
}
