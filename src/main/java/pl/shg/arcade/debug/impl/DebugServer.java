/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import pl.shg.arcade.ArcadeServer;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.menu.Menu;
import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.arcade.debug.ArcadeDebug;

/**
 *
 * @author Aleksander
 */
public class DebugServer extends ArcadeServer {
    private final HashMap<UUID, Player> connected = new HashMap<>();
    private final Runtime runtime;
    
    public DebugServer(Runtime runtime) {
        this.runtime = runtime;
    }
    
    @Override
    public SchedulerManager getScheduler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object createMenu(Menu menu) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Player> getConnectedPlayers() {
        return this.connected.values();
    }
    
    @Override
    public String getName() {
        return "Debug";
    }
    
    @Override
    public Player getPlayer(String name) {
        Iterator<Player> it = this.getConnectedPlayers().iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (player.getName().toLowerCase().contains(name.toLowerCase())) {
                return player;
            }
        }
        return null;
    }
    
    @Override
    public Player getPlayer(UUID uuid) {
        return this.connected.get(uuid);
    }
    
    @Override
    public int getSlots() {
        return 0;
    }
    
    @Override
    public void shutdown() {
        ArcadeDebug.getConsole().sendMessage("Bye!");
        System.exit(0);
    }
    
    public void addPlayer(Player player) {
        this.connected.put(player.getUUID(), player);
        ArcadeDebug.getConsole().sendMessage(player.getName() + " (UUID: " + player.getUUID() + ") connected.");
    }
    
    public Runtime getRuntime() {
        return this.runtime;
    }
    
    public void removePlayer(UUID uuid) {
        this.connected.remove(uuid);
    }
}
