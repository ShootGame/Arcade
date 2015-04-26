/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.menu;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.map.ArcadeClass;

/**
 *
 * @author Aleksander
 */
public class ClassSelectorMenu extends Menu {
    private SortedMap<Integer, ArcadeClass> classes;
    
    public ClassSelectorMenu() {
        super(Color.DARK_PURPLE + "Zmien swoja klase", 1);
    }
    
    @Override
    public void onClick(Player player, int slot) {
        if (this.classes.containsKey(slot)) {
            Arcade.getCommands().perform("class", player, new String[] {this.classes.get(slot).getName()});
            player.close();
        }
    }
    
    @Override
    public void onCreate(Player player) {
        this.classes = new TreeMap<>();
        List<ArcadeClass> classList = Arcade.getMaps().getCurrentMap().getClasses();
        for (int i = 0; i < classList.size(); i++) {
            ArcadeClass clazz = classList.get(i);
            this.classes.put(i, clazz);
            
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
