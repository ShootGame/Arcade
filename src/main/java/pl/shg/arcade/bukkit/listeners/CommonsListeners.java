/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import java.util.List;
import java.util.Random;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.tablist.ArcadeTabList;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.BukkitPlayer;
import pl.shg.arcade.bukkit.BukkitServer;
import pl.shg.commons.bukkit.event.UserConnectEvent;
import pl.shg.commons.bukkit.event.UserDisconnectEvent;
import pl.shg.commons.users.BukkitUser;

/**
 *
 * @author Aleksander
 */
public class CommonsListeners implements Listener {
    private final Random random = new Random();
    private final BukkitServer server;
    
    public CommonsListeners() {
        this.server = (BukkitServer) Arcade.getServer();
    }
    
    @EventHandler
    public void onUserConnect(UserConnectEvent e) {
        BukkitUser user = (BukkitUser) e.getUser();
        
        Player player = new BukkitPlayer(user.getBukkit());
        this.server.addPlayer(player);
        player.updateDisplayName();
        
        List<Spawn> spawns = player.getTeam().getSpawns();
        Spawn spawn = spawns.get(this.random.nextInt(spawns.size()));
        Arcade.getPlayerManagement().setAsObserver(player, true, true, true);
        user.getBukkit().teleport(BukkitLocation.valueOf(spawn));
        
        player.setTabList(Arcade.getServer().getGlobalTabList());
    }
    
    @EventHandler
    public void onUserDisconnect(UserDisconnectEvent e) {
        BukkitUser user = (BukkitUser) e.getUser();
        
        try {
            this.server.removePlayer(this.server.getPlayer(user.getID()));
        } catch (Throwable ex) {
            Log.noteAdmins("Nie udalo sie usunac gracza " + user.getID() + " poniewaz nie istnial", Log.NoteLevel.SEVERE);
        }
        ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
    }
}
