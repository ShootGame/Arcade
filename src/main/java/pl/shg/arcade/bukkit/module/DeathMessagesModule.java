/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DeathMessagesModule extends Module implements BListener {
    public DeathMessagesModule() {
        super(new Date(2015, 4, 21), "death-messages", "1.0");
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie własnych " +
                "wiadomość po śmierci (przykładowo <code>TheMolkaPL hit the " +
                "ground too hard.</code> może zostać zamienione na <code>TheMolkaPL " +
                "spadł z wysokości.</code>). Ten moduł umożliwia ustawienie " +
                "każdej wiadomości po śmierci jaka istnieje w pluginie Arcade.");
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this);
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage() == null) {
            return;
        }
        
        
    }
}
