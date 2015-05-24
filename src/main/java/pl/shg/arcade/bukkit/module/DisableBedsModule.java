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
import org.bukkit.event.player.PlayerBedEnterEvent;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DisableBedsModule extends Module implements BListener {
    public DisableBedsModule() {
        super(new Date(2015, 4, 18), "disable-beds", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł blokuje spawnie na łóżkach " +
                "przez graczy na obecnej mapie. Moduł ten jest nierozłączym " +
                "przyjacielem na mapach typu <code>Capture the wool</code> " +
                ", gdzie stworzenie łóżka jest bardzo proste. Pamiętaj, że " +
                "ten moduł nie blokuje stawiania łóżek na mapie, a tylko " +
                "blokuje kładzenie się na nich.");
        this.deploy(true);
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
    public void onBedEnter(PlayerBedEnterEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Color.RED + "Lozka sa wylaczone na tej mapie.");
    }
}
