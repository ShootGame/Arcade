/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.def.JoinCommand;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.MapLoadedEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class AutoJoinModule extends Module {
    private EventListener listener;
    private String message;
    
    public AutoJoinModule() {
        super(new Date(2015, 4, 21), "auto-join", "1.0");
        this.getDocs().setDescription("Ten moduł umożliwia automatyczne dołączanie " +
                "do drużyny przez graczy od razu po załadowaniu mapy. Zaleca się " +
                "użycie tego modułu tylko i wyłącznie na mapach typu <code>Blitz</code>.");
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.MESSAGE) {
            @Override
            public String getPrefix() {
                return "Możesz dodać opcjonalną wiadomość przy dołączaniu do " +
                        "gry. Wiadomość zostanie wysłana zawsze przed próbą " +
                        "dołącznia. Oznacza to, że gracz otzyma tą wiadomość " +
                        "nawet wtedy gdy dołączanie się nie powiedzie (przykładowo "+
                        " z powodu pełnych drużyn).";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "auto-join:",
                    "  message: '`aDolaczasz do losowej druzyny automatycznie...'"
                };
            }
        });
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.listener = new MapLoaded();
        Event.registerListener(this.listener);
        this.message = Config.getValueMessage(Config.get(file), this, null, true);
    }
    
    @Override
    public void unload() {
        Event.unregisterListener(this.listener);
    }
    
    private class MapLoaded implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return MapLoadedEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            MatchStatus status = Arcade.getMatches().getStatus();
            for (Player player : Arcade.getServer().getOnlinePlayers()) {
                if (!JoinCommand.hasTeam(player) && AutoJoinModule.this.message != null) {
                    player.sendSuccess(AutoJoinModule.this.message);
                    JoinCommand.random(player, status);
                }
            }
        }
    }
}
