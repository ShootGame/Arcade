/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.menu;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.shootgame.api.server.Servers;
import pl.shg.shootgame.api.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class ServerPickerMenu extends Menu {
    private static SortedMap<Integer, TargetServer> servers;
    
    public ServerPickerMenu() {
        super(Color.DARK_RED + "Wybierz serwer " + Color.RESET + "ShootGame", 1);
    }
    
    @Override
    public void onClick(Player player, int slot) {
        if (servers.containsKey(slot)) {
            player.connect(servers.get(slot));
            this.close(player);
        }
    }
    
    @Override
    public void onCreate(Player player) {
        if (servers == null) {
            servers = new TreeMap<>();
            List<TargetServer> serverList = Servers.getServers();
            player.sendSuccess("Ladowanie " + serverList.size() + " serwerów ShootGame...");
            for (int i = 0; i < serverList.size(); i++) {
                TargetServer server = serverList.get(i);
                if (!server.isPublic() && !player.hasPermission("arcade.protected-servers")) {
                    continue;
                }
                
                servers.put(i, server);
                this.addItem(this.createTargetServer(player, server), i);
            }
        }
    }
    
    private Item createTargetServer(Player player, TargetServer server) {
        Item item = new Item(new Material(35, Color.Wool.CYAN.getID()));
        item.setName(Color.GREEN + Color.BOLD + Color.UNDERLINE + server.getName());
        return item;
    }
    
    public static Collection<TargetServer> getServers() {
        return servers.values();
    }
    
    public static void reset() {
        servers = null;
    }
}
