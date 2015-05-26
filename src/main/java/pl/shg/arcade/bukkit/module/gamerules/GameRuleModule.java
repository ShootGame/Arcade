/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.gamerules;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class GameRuleModule extends Module {
    private final HashMap<String, String> defaultGameRules = new HashMap();
    private World world;
    
    public GameRuleModule() {
        super(new Date(2015, 3, 18), "game-rule", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie własnych " +
                "wartości <code>gamerule</code> z Minecraft");
        this.addExample(new ConfigurationDoc(true, ConfigurationDoc.Type.BOOLEAN) {
            @Override
            public String getPrefix() {
                return "Nie możesz ustawić każdej wartości dla gamerule, ponieważ " +
                        "część z nich nie dotyczy map, a serwera. Lista wszystkich " +
                        "gamerule w Minecraft dostępna jest na <a " +
                        "href=\"http://minecraft.gamepedia.com/Commands#gamerule\">Minecraft Wiki</a>.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "game-rule:",
                    "  game-rules:",
                    "    doDaylightCycle: false",
                    "    doTileDrops: false"
                };
            }
            
            @Override
            public String getSuffix() {
                return "Powyższy przykład blokuje cykl czasu <code>" +
                        "doDaylightCycle</code> na mapie oraz wyłącza drop " +
                        "itemów <code>doTileDrops</code> z bloków. ";
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
        FileConfiguration config = Config.get(file);
        this.world = BukkitLocation.getWorld();
        
        for (String gameRule : this.world.getGameRules()) {
            this.defaultGameRules.put(gameRule, this.world.getGameRuleValue(gameRule));
        }
        
        for (String gameRule : Config.getOptions(config, this, "game-rules")) {
            this.world.setGameRuleValue(gameRule, Config.getValueString(config, this, "game-rules." + gameRule));
        }
    }
    
    @Override
    public void unload() {
        for (String gameRule : this.defaultGameRules.keySet()) {
            this.world.setGameRuleValue(gameRule, this.defaultGameRules.get(gameRule));
        }
    }
}
