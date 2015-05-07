/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.def.MapinfoCommand;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.MapLoadedEvent;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.map.Configuration;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.GameableBlock;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.map.team.TeamManager;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class CycleScheduler implements Runnable {
    private static int defaultSeconds = 20;
    private static int id;
    
    private final MapManager maps;
    private int seconds;
    private final Server server;
    
    public CycleScheduler(int seconds) {
        this.maps = Arcade.getMaps();
        if (seconds < 0) {
            this.seconds = getDefaultSeconds();
        } else {
            this.seconds = seconds;
        }
        this.server = Arcade.getServer();
    }
    
    @Override
    public void run() {
        this.print();
        
        if (this.seconds <= 0) {
            Arcade.getServer().getScheduler().cancel();
            if (this.maps.getNextMap() != null) {
                this.cycle();
            } else {
                this.server.shutdown();
            }
        } else {
            this.seconds--;
        }
    }
    
    private void cycle() {
        Log.noteAdmins("Ladowanie mapy " + this.maps.getNextMap().getName() + "...", Log.NoteLevel.INFO);
        long ms = System.currentTimeMillis();
        
        TeamManager teams = Arcade.getTeams();
        PlayerManagement players = Arcade.getPlayerManagement();
        
        List<UUID> forceRespawn = new ArrayList<>();
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.isDead()) {
                forceRespawn.add(player.getUUID());
            }
        }
        
        for (UUID respawn : forceRespawn) {
            Player player = Arcade.getServer().getPlayer(respawn);
            if (player != null) {
                player.kick(); // TODO we kick dead players, but respawn would be better
            }
        }
        
        Arcade.getModules().inactiveAll();
        GameableBlock.reset();
        Map oldMap = this.maps.getCurrentMap();
        try {
            this.maps.getConfiguration().load(new Configuration(this.maps.getNextMap()), false);
            this.maps.getWorlds().load(this.maps.getNextMap().getName());
            this.maps.updateCurrentMap();
            this.maps.getConfiguration().registerModules();
        } catch (ConfigurationException ex) {
            Log.noteAdmins("Mapa " + maps.getCurrentMap().getName() + " nie jest prawidlowo skonfigurowana.\n" + Color.RESET +
                    Color.RED + ex.getMessage() + ".\n" +
                    Color.RED + "Zaladuj inna mape komendami /setnext <map...> oraz /cycle 5. Mozesz takze zrestartowac serwer uzywajac /setnext -r",
                    Log.NoteLevel.SEVERE);
        }
        
        Map map = Arcade.getMaps().getCurrentMap();
        List<Spawn> spawns = teams.getObservers().getSpawns();
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            player.setTeam(teams.getObservers());
            player.teleport(spawns.get(new Random().nextInt(spawns.size())));
            players.setAsObserver(player, true, false);
            player.setArcadeClass(null);
            MapinfoCommand.show(player, map);
        }
        
        Arcade.getMatches().setStatus(MatchStatus.STARTING);
        if (oldMap != null) {
            this.maps.getWorlds().unload(oldMap.getName());
        }
        ms = System.currentTimeMillis() - ms;
        Log.noteAdmins("Zaladowano mape " + this.maps.getCurrentMap().getName() + " w " + ms + " ms.", Log.NoteLevel.INFO);
        
        MapLoadedEvent event = new MapLoadedEvent(this.maps.getCurrentMap());
        Event.callEvent(event);
        
        Arcade.getServer().setGlobalTabList(new ArcadeTabList());
        ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
    }
    
    private void print() {
        for (int second : this.server.getScheduler().seconds()) {
            if (this.seconds == second) {
                if (this.maps.getNextMap() != null) {
                    String msg = Color.DARK_AQUA + "Przenoszenie na " + Color.AQUA + "{0}" + Color.DARK_AQUA + " za " + Color.DARK_RED + "{1}" + Color.DARK_AQUA + " sekund";
                    this.server.broadcast(msg.replace("{0}", this.maps.getNextMap().getDisplayName()).replace("{1}", String.valueOf(second)));
                } else {
                    String msg = Color.RED + "Restartowanie serwera za " + Color.DARK_RED + "{0}" + Color.RED + " sekund";
                    this.server.broadcast(msg.replace("{0}", String.valueOf(second)));
                }
            }
        }
    }
    
    public static int getDefaultSeconds() {
        return defaultSeconds;
    }
    
    public static int getID() {
        return id;
    }
    
    public static void setDefaultSeconds(int seconds) {
        Validate.isTrue(seconds <= 3, "seconds are incorrent");
        defaultSeconds = seconds;
    }
    
    public static void setID(int id) {
        CycleScheduler.id = id;
    }
}
