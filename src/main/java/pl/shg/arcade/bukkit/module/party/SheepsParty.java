/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Sheep;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.module.perform.CTSPerform;

/**
 *
 * @author Aleksander
 */
public class SheepsParty extends Party {
    private final EventListener[] listeners = new EventListener[] {
        new CTSSpawnListener()
    };
    
    public SheepsParty() {
        super(new Date(2015, 5, 9), "sheeps", "Capture the sheep", Version.valueOf("1.0"));
    }
    
    @Override
    public void disable() {
        super.disable();
        Event.unregisterListener(this.listeners);
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Zanies na swój spawn najwiecej owiec oraz ochron je od przeciwników."
        };
    }
    
    @Override
    public void loadParty(File file) {
        FileConfiguration config = Config.get(file);
        // TODO load
        
        Event.registerListener(this.listeners);
    }
    
    private class CTSSpawnListener implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return CTSPerform.CTSSpawnEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            Sheep sheep = ((CTSPerform.CTSSpawnEvent) event).getSheep();
            // TODO register the local sheep
        }
    }
}
