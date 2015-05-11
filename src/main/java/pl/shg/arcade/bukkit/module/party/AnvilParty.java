/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import java.util.Random;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class AnvilParty extends Party {
    private int task, ticks;
    
    public AnvilParty() {
        super(new Date(2015, 5, 10), "anvil", "Spadajce kowadla", "1.0");
        this.deploy(true);
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Twoim zadaniem jest przetrwac spadajce na Ciebie kowadla."
        };
    }
    
    @Override
    public void loadParty(File file) {
        FileConfiguration config = Config.get(file);
        this.ticks = Config.getValueInt(config, this, "ticks", 10);
    }
    
    @Override
    public void onMatchFinish() {
        Arcade.getServer().getScheduler().cancel(this.task);
    }
    
    @Override
    public void onMatchStart() {
        this.task = new Task().runTaskTimer(ArcadeBukkitPlugin.getPlugin(), 0L, this.ticks * 1L).getTaskId();
    }
    
    private class Task extends BukkitRunnable {
        private final Random random = new Random();
        
        @Override
        public void run() {
            
        }
    }
}
