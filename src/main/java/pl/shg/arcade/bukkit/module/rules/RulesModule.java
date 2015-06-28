/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.command.def.MapinfoCommand;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class RulesModule extends Module implements EventListener {
    public static final int LIMIT = 5;
    private final List<String> rules = new ArrayList<>();
    
    public RulesModule() {
        super(new Date(2015, 6, 18), "rules", Version.valueOf("1.0"));
        this.deploy(true);
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
        
        for (String rule : Config.getValueList(config, this, "rules")) {
            this.rules.add(Color.translate(rule));
        }
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventSubscribtion(event = MapinfoCommand.MapinfoEvent.class)
    public void handleMapinfo(MapinfoCommand.MapinfoEvent e) {
        if (this.rules.isEmpty()) {
            return;
        }
        
        e.getSender().sendMessage(Color.DARK_PURPLE + Color.BOLD + "Zasady:");
        
        if (!e.isFull() && this.rules.size() > LIMIT) {
            // we won't display the whole rule list if it's too big
            e.getSender().sendMessage(Color.GOLD + "uzyj komendy /mapa, aby sie dowiedziec");
        } else {
            for (int i = 0; i < this.rules.size(); i++) {
                e.getSender().sendMessage(Color.DARK_AQUA + (i + 1) + ") " + Color.RED + this.rules.get(i));
            }
        }
    }
}
