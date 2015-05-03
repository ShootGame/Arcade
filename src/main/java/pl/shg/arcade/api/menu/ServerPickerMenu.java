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
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.shootgame.api.server.ArcadeTarget;
import pl.shg.shootgame.api.server.LobbyTarget;
import pl.shg.shootgame.api.server.Servers;
import pl.shg.shootgame.api.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class ServerPickerMenu extends Menu {
    private static ServerPickerMenu menu;
    private static SortedMap<Integer, TargetServer> servers;
    
    public ServerPickerMenu() {
        super(Color.DARK_RED + "Wybierz serwer " + Color.RESET + "ShootGame", 1);
        menu = this;
        this.register();
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
        
    }
    
    public void update() {
        servers = new TreeMap<>();
        int lobbies = 0;
        
        List<TargetServer> serverList = Servers.getServers();
        for (int i = 0; i < serverList.size(); i++) {
            TargetServer server = serverList.get(i);
            if (server.isOnline() && server.isPublic()) {
                if (server instanceof ArcadeTarget) {
                    servers.put(i, server);
                    addItem(this.createTargetServer((ArcadeTarget) server), i);
                } else if (server instanceof LobbyTarget) {
                    int slot = this.getSlots() - lobbies;
                    servers.put(slot, server);
                    addItem(this.createLobbyServer((LobbyTarget) server), slot);
                }
            }
        }
    }
    
    private Item createLobbyServer(LobbyTarget server) {
        Item item = new Item(new Material(399));
        item.setName(Color.GREEN + server.getName());
        item.setDescription(Arrays.asList(
                Color.DARK_PURPLE + Color.BOLD + server.getPlayers() + Color.RESET + "/" + Color.GRAY + server.getSlots()
        ));
        return item;
    }
    
    private Item createTargetServer(ArcadeTarget server) {
        int status = server.getStatus();
        Item item = new Item(new Material(35, this.getColor(status).getID()));
        
        item.setName(Color.GREEN + Color.BOLD + Color.UNDERLINE + server.getName());
        item.setDescription(Arrays.asList(
                Color.GRAY + Color.BOLD + server.getMap(),
                Color.DARK_PURPLE + Color.BOLD + server.getArcadePlayers() + Color.RESET + "/" + Color.GRAY +
                        server.getArcadeSlots() + Color.RESET + " - " + Color.GOLD + this.getStatus(status)
        ));
        return item;
    }
    
    private Color.Wool getColor(int status) {
        Color.Wool result = Color.Wool.CYAN;
        switch (status) {
            case 0: result = Color.Wool.RED; break; // nothing - probably cycling
            case 1: result = Color.Wool.LIME; break; // starting
            case 2: result = Color.Wool.YELLOW; break; // running
            case 3: result = Color.Wool.RED; break; // cycling
        }
        return result;
    }
    
    private String getStatus(int status) {
        String result = null;
        switch (status) {
            case 0: result = "PRZENOSZENIE"; break;
            case 1: result = "STARTOWANIE"; break;
            case 2: result = "W TRAKCIE GRY"; break;
            case 3: result = "PRZENOSZENIE"; break;
        }
        return result;
    }
    
    public static ServerPickerMenu getMenu() {
        return menu;
    }
    
    public static Collection<TargetServer> getServers() {
        return servers.values();
    }
    
    public static void reset() {
        servers = null;
    }
}
