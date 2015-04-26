/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.server.ArcadeServer;
import pl.shg.arcade.api.server.status.ServerStatus;

/**
 *
 * @author Aleksander
 */
public class ServerPickerMenu extends Menu {
    private SortedMap<Integer, ArcadeServer> servers;
    
    public ServerPickerMenu() {
        super(Color.DARK_RED + "Wybierz serwer " + Color.RESET + "ShootGame", 1);
    }
    
    @Override
    public void onClick(Player player, int slot) {
        if (this.servers.containsKey(slot)) {
            player.connect(this.servers.get(slot));
            player.close();
        }
    }
    
    @Override
    public void onCreate(Player player) {
        this.servers = new TreeMap<>();
        List<ArcadeServer> serverList = Arcade.getServers().getServers();
        player.sendSuccess("Ladowanie " + serverList.size() + " serwerów ShootGame...");
        for (int i = 0; i < serverList.size(); i++) {
            ArcadeServer server = serverList.get(i);
            if (server.isProtected() && !player.hasPermission("arcade.protected-servers")) {
                continue;
            }
            this.servers.put(i, server);
            
            if (Arcade.getServers().getCurrentServer().equals(server)) {
                this.addItem(this.createCurrentServer(server), i);
            } else {
                this.addItem(this.createTargetServer(player, server), i);
            }
        }
    }
    
    private Item createCurrentServer(ArcadeServer server) {
        Item item = new Item(new Material(35, Color.Wool.ORANGE.getID()));
        item.setName(Color.RED + Color.UNDERLINE + server.getName());
        item.setDescription(this.getItemDescription(null,
                server.getStatus(), "Obecnie znajdujesz sie na " + server.getName() + "."));
        return item;
    }
    
    private Item createTargetServer(Player player, ArcadeServer server) {
        Item item = new Item(new Material(35, Color.Wool.CYAN.getID()));
        item.setName(Color.GREEN + Color.BOLD + Color.UNDERLINE + server.getName());
        item.setDescription(this.getItemDescription(player,
                server.getStatus(), "Przejdz na " + server.getName() + "."));
        return item;
    }
    
    private List<String> getItemDescription(Player player, ServerStatus status, String first) {
        List<String> description = new ArrayList<>();
        description.add(Color.GRAY + first);
        if (status != null && status.enabled()) {
            description.add(Color.GRAY + first);
            if (status.canConnect(player) != null) {
                description.add(Color.RED + status.canConnect(player));
            }
        }
        return description;
    }
}
