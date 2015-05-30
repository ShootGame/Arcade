/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.perform;

import com.sun.glass.ui.View;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class DelayedPerformModule extends Module {
    private final List<Perform> performs = new ArrayList<>();
    
    public DelayedPerformModule() {
        super(new Date(2015, 5, 30), "delayed-perform", Version.valueOf("1.0"));
    }
    
    @Override
    public void disable() {
        
    }
    
    @Override
    public void enable() {
        
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        for (String id : Config.getOptions(config, this)) {
            this.load(config, id);
        }
        
        if (!this.performs.isEmpty()) {
            Arcade.getServer().getScheduler().runSync(new Task(), 20L);
        }
    }
    
    @Override
    public void unload() {
        
    }
    
    private void load(FileConfiguration config, String id) {
        int seconds = Config.getValueInt(config, this, id + ".seconds", 1);
        boolean repeating = Config.getValueBoolean(config, this, id + ".repeating", false);
        int times = Config.getValueInt(config, this, id + ".times", 0);
        
        for (String perform : Config.getOptions(config, this, ".perform")) {
            this.loadPerform(config, id, perform, seconds, repeating, times);
        }
    }
    
    private void loadPerform(FileConfiguration config, String id, String perform, int seconds, boolean repeating, int times) {
        Perform obj = null;
        switch (perform.toLowerCase()) {
            case "message":
                obj = MessagePerform.create(this, config, id, seconds, repeating, times);
                break;
            case "cts": // Capture the sheep
                obj = CTSPerform.create(this, config, id, seconds, repeating, times);
                break;
        }
        
        if (obj != null) {
            this.performs.add(obj);
        }
    }
    
    private class Task implements Runnable {
        private int seconds = 0;
        
        @Override
        public void run() {
            for (Perform perform : DelayedPerformModule.this.performs) {
                if (perform.getSeconds() == this.seconds) {
                    perform.run();
                    perform.removeTime();
                }
            }
            
            this.seconds++;
        }
    }
}
