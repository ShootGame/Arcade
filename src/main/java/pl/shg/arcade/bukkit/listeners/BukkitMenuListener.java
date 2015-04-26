/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.menu.Menu;

/**
 *
 * @author Aleksander
 */
public class BukkitMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        for (Menu menu : Menu.getRegistered()) {
            if (menu.getName().equals(e.getInventory().getTitle())) {
                e.setCancelled(true);
                menu.onClick(Arcade.getServer().getPlayer(e.getWhoClicked().getUniqueId()), e.getRawSlot());
            }
        }
    }
}
