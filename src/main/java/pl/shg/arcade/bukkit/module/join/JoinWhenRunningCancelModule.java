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
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.PlayerJoinTeamEvent;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class JoinWhenRunningCancelModule extends Module implements EventListener {
    private String message;
    
    public JoinWhenRunningCancelModule() {
        super(new Date(2015, 4, 19), "join-when-running-cancel", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł wyłącza możliwość dołączania " +
                "do drużyn (także rangom premium) w czasie trawnia meczu. Do " +
                "gry można dołączyć tylko i wyłącznie podczas odliczania do " +
                "jej startu.");
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.MESSAGE_ERROR) {
            @Override
            public String getPrefix() {
                return "Moduł posiada opcję zmiany wiadomości próby wejścia do " +
                        "drużyny.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "join-when-running-cancel:",
                    "  message: 'Nie mozesz dolaczyc do druzyny w czasie trawania meczu na tej mapie.'"
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
        this.message = Config.getValueMessage(Config.get(file), this,
                "Nie mozesz dolaczyc do druzyny w czasie trawania meczu na tej mapie.", false);
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventSubscribtion(event = PlayerJoinTeamEvent.class)
    public void handlePlayerJoinTeam(PlayerJoinTeamEvent e) {
        if (Arcade.getMatches().getStatus() == ArcadeMatchStatus.RUNNING &&
                !e.getTeam().getID().equals(ObserverTeamBuilder.getTeamID())) {
            e.setCancel(true);
            e.getPlayer().sendError(this.message);
        }
    }
}
