/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.menu;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.map.ArcadeClass;
import pl.shg.arcade.api.map.Map;

/**
 *
 * @author Aleksander
 */
public class ClassSelectorMenu extends Menu {
    private static SortedMap<Integer, ArcadeClass> classes;
    private static Map map;
    
    public ClassSelectorMenu() {
        super(Color.DARK_PURPLE + "Zmien swoja klase", 1);
        this.register();
    }
    
    @Override
    public void onClick(Player player, int slot) {
        if (classes.containsKey(slot)) {
            Arcade.getCommands().perform("class", player, new String[] {classes.get(slot).getName()});
            this.close(player);
        }
    }
    
    @Override
    public void onCreate(Player player) {
        Map current = Arcade.getMaps().getCurrentMap();
        if (classes == null || !map.equals(current)) {
            classes = new TreeMap<>();
            map = current;
            
            List<ArcadeClass> classList = current.getClasses();
            for (int i = 0; i < classList.size(); i++) {
                ArcadeClass clazz = classList.get(i);
                classes.put(i, clazz);
                
                Item item = new Item(clazz.getIcon(), 1);
                item.setName(Color.DARK_AQUA + "Graj jako " + Color.GREEN + clazz.getName());
                item.setDescription(Arrays.asList(
                        Color.GOLD + clazz.getDescription(),
                        Color.GRAY + clazz.getFullDescription()
                ));
                this.addItem(item, i);
            }
        }
    }
    
    public static Collection<ArcadeClass> getClasses() {
        return classes.values();
    }
    
    public static void reset() {
        classes = null;
    }
}
