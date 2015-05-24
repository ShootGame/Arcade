/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DeathMessagesModule extends Module implements BListener {
    private SimpleConfig config;
    private final HashMap<String, String> messages = new HashMap<>();
    
    public DeathMessagesModule() {
        super(new Date(2015, 4, 21), "death-messages", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie własnych " +
                "wiadomość po śmierci (przykładowo <code>TheMolkaPL hit the " +
                "ground too hard.</code> może zostać zamienione na <code>TheMolkaPL " +
                "spadł z wysokości.</code>). Ten moduł umożliwia ustawienie " +
                "każdej wiadomości po śmierci jaka istnieje w pluginie Arcade.");
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
        
        Set<String> sections = SimpleConfig.getOptions(this.config.getConfig(), this, "custom-messages");
        if (sections != null) {
            for (String section : sections) {
                this.messages.put(section, this.config.getDeathMessage(section.toLowerCase(), "default", "arcade"));
            }
        }
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.config = new SimpleConfig();
        this.config.load(file);
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage() == null) {
            return;
        }
        
        Handler handler = Handler.getHandler(e.getEntity(), e.getEntityType());
        if (handler == null) {
            Log.error("Nie znaleziono zadnej wiadomosci dla smierci \"" + e.getDeathMessage() + "\".");
            return;
        }
        
        String customMessage = this.messages.get(handler.getConfigPath());
        String message = handler.getMessage();
        if (false && customMessage == null) { // if message is "none"
            e.setDeathMessage(null);
        } else if (true || customMessage.equalsIgnoreCase("arcade")) { // if message is "arcade"
            pl.shg.arcade.api.human.Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
            String deathMessage = String.format(message, new Object[] {player.getDisplayName() + Color.GRAY});
            
            if (e.getEntity().getKiller() != null) {
                pl.shg.arcade.api.human.Player killer = Arcade.getServer().getPlayer(e.getEntity().getKiller().getUniqueId());
                deathMessage = String.format(Handler._PLAYER_KILLER.getMessage(),
                        new Object[] {deathMessage, killer.getDisplayName()});
            }
            
            e.setDeathMessage(deathMessage);
        } else if (customMessage.equalsIgnoreCase("default")) { // if message don't exists in the configuration
            e.setDeathMessage(e.getDeathMessage());
        }
    }
    
    private enum Handler {
        // Simple death messages
        BLOCK_EXPLOSION("%s wybuchl/a od bloku"),
        CACTUS("%s ukul/a sie smiertelnie"),
        ENTITY_EXPLOSION("%s wybuchl/a od stworzenia"),
        FALLING_BLOCK("%s zostal/a przygnieciony/a blokiem"),
        FIRE_TICK("%s splonal/ela"),
        FIRE_WALK("%s spalil/a sie"),
        HUNGER("%s zmarl/a z glodu"),
        HIGH_PLACE("%s spadl/a z wysokosci"),
        LAVA("%s spalil/a sie w lawie"),
        LIGHTNING("%s zabil piorun"),
        MAGIC("%s zostal/a zabity/a magicznie"),
        PLAYER("%s zostal/a zabity/a"),
        POISON("%s zatrul/a sie smiertlenie"),
        PROJECTILE("%s zostal/a zastrzelony/a"),
        SUFFOCATION("%s zablokowal/a sie w bloku"),
        THORNS("%s zabil/a sie atakiem na wroga"),
        UNKNOWN("%s zabil/a sie"),
        VOID("%s wypadl/a z mapy"),
        WATER("%s udusil/a sie"),
        WITHER("%s zostal/a zabity/a przez witherera"),
        
        // Extra death messages (simple death message is required)
        _PLAYER_KILLER("%s przez %s"),
        ;
        
        private final String message;
        
        private Handler(String message) {
            this.message = message;
        }
        
        public String getConfigPath() {
            return this.name().toLowerCase().replace('_', '-');
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public static Handler getHandler(Player player, EntityType killer) {
            EntityDamageEvent cause = player.getLastDamageCause();
            if (cause == null) {
                return Handler.UNKNOWN; // suicide?
            }
            
            switch (cause.getCause()) {
                case BLOCK_EXPLOSION: return Handler.BLOCK_EXPLOSION;
                case CONTACT: return Handler.CACTUS;
                case CUSTOM: break;
                case DROWNING: return Handler.WATER;
                case ENTITY_ATTACK: return Handler.PLAYER; // ?
                case ENTITY_EXPLOSION: return Handler.ENTITY_EXPLOSION;
                case FALL: return Handler.HIGH_PLACE;
                case FALLING_BLOCK: return Handler.FALLING_BLOCK;
                case FIRE: return Handler.FIRE_WALK;
                case FIRE_TICK: return Handler.FIRE_TICK;
                case LAVA: return Handler.LAVA;
                case LIGHTNING: return Handler.LIGHTNING;
                case MAGIC: return Handler.MAGIC;
                case MELTING: break; // ?
                case POISON: return Handler.POISON;
                case PROJECTILE: return Handler.PROJECTILE;
                case STARVATION: return Handler.HUNGER;
                case SUFFOCATION: return Handler.SUFFOCATION;
                case SUICIDE: break;
                case THORNS: return Handler.THORNS;
                case VOID: return Handler.VOID;
                case WITHER: return Handler.WITHER;
                default: break;
            }
            return null;
        }
    }
    
    private class SimpleConfig extends Config {
        private FileConfiguration config;
        
        public FileConfiguration getConfig() {
            return this.config;
        }
        
        public String getDeathMessage(String path, String def, String arcade) {
            String message = Config.getValueMessage(this.config, DeathMessagesModule.this, path, def, true);
            if (message != null && message.equalsIgnoreCase("arcade")) {
                if (arcade != null) {
                    return Color.translate(arcade);
                }
                return def;
            } else {
                return message;
            }
        }
        
        public void load(File file) {
            this.config = Config.get(file);
        }
    }
}
