/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.join;

import java.io.File;
import java.util.Date;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.def.JoinCommand;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.MapLoadedEvent;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class AutoJoinModule extends Module implements EventListener {
    private String message;
    
    public AutoJoinModule() {
        super(new Date(2015, 4, 21), "auto-join", Version.valueOf("1.0"));
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
        this.message = Config.getValueMessage(Config.get(file), this, null, true);
    }
    
    @Override
    public void unload() {
        
    }

    @EventSubscribtion(event = MapLoadedEvent.class)
    public void handleMapLoaded(MapLoadedEvent e) {
        ArcadeMatchStatus status = Arcade.getMatches().getStatus();
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (!JoinCommand.hasTeam(player) && AutoJoinModule.this.message != null) {
                player.sendSuccess(AutoJoinModule.this.message);
                JoinCommand.random(player, status);
            }
        }
    }
}
