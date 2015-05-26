/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.pickup;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BukkitModule;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class CancelPickupModule extends BukkitModule {
    private String message;
    
    public CancelPickupModule() {
        super(new Date(2015, 4, 27), "cancel-pickup", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł blokuje podnoszenie itemów (poprzez " +
                "podnoszenie z ziemi) do ekwipunku gracza.");
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.MESSAGE) {
            @Override
            public String getPrefix() {
                return "Ustaw opcjonalną wiadomość do graczy, który próbuje podnieść przedmiot.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "cancel-pickup:",
                    "  message '`cNie mozesz podnosić itemów na tej mapie.'"
                };
            }
        });
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.message = Config.getValueMessage(Config.get(file), this, null, true);
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
        if (this.message != null) {
            e.getPlayer().sendMessage(this.message);
        }
    }
}
