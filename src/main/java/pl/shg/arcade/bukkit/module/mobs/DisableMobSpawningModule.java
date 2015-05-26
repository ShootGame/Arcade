/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.module.mobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ModuleException;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DisableMobSpawningModule extends Module implements BListener {
    private final List<EntityType> allowed = new ArrayList<>();
    private boolean cancelAll = false;
    private final List<EntityType> disallowed = new ArrayList<>();
    
    public DisableMobSpawningModule() {
        super(new Date(2014, 11, 8), "disable-mob-spawning", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie listy " +
                "mobów, które mogą, lub nie mogę zostać narodzone na mapie. " +
                "Proszę pamiętać, że moby które będą już narodzone na mapie " +
                "przed jej zapisem nie zostaną usunięte.");
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.BOOLEAN) {
            @Override
            public String getPrefix() {
                return "Ustaw całkowite blokowanie spawnowania mobów na mapie.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "disable-mob-spawning: true"
                };
            }
        });
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.ENTITY) {
            @Override
            public String getPrefix() {
                return "Ustaw moby, które mogą się spawnować na mapie. Wszystkie " +
                        "inne, które nie zostaną podane w tej opcji zostaną zablokowane.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "disable-mob-spawning:",
                    "  allowed:",
                    "    PIG: true",
                    "    COW: true"
                };
            }
        });
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.ENTITY) {
            @Override
            public String getPrefix() {
                return "Ustaw moby, które nie mogą się spawnować na mapie. Wszystkie " +
                        "inne, które nie zostaną podane w tej opcji zostaną odblokowane.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "disable-mob-spawning:",
                    "  disallowed:",
                    "    CREEPER: true",
                    "    ZOMBIE: true"
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
    public void load(File file) throws ModuleException {
        FileConfiguration config = Config.get(file);
        Listeners.register(this);
        if (!Config.hasOptions(config, this)) {
            this.cancelAll = true;
            return;
        }
        
        for (String option : Config.getOptions(config, this)) {
            try {
                switch (option.toLowerCase()) {
                    case "allowed":
                        for (String entity : Config.getOptions(config, this, option)) {
                            this.allowed.add(EntityType.valueOf(entity.toUpperCase()));
                        }
                        break;
                    case "disallowed":
                        for (String entity : Config.getOptions(config, this, option)) {
                            this.disallowed.add(EntityType.valueOf(entity.toUpperCase()));
                        }
                        break;
                }
            } catch (IllegalArgumentException ex) {
                throw new ConfigurationException("Podano bledna nazwe moba");
            }
        }
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        boolean cancel = false;
        
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            cancel = false;
        } else if (this.cancelAll) {
            cancel = true;
        } else if (!this.allowed.isEmpty()) { // W liscie dozwolonych znajduja sie moby
            if (!this.allowed.contains(e.getEntityType())) {
                cancel = true;
            }
        } else if (!this.disallowed.isEmpty()) { // W liscie zakazanych znajduja sie moby
            if (this.allowed.contains(e.getEntityType())) {
                cancel = true;
            }
        }
        
        e.setCancelled(cancel);
    }
}
