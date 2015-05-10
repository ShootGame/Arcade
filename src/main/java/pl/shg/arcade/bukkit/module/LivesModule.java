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
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.def.JoinCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class LivesModule extends Module implements BListener {
    private int defaults = 3;
    private final HashMap<UUID, Integer> lives = new HashMap<>();
    private String kickMessage, respawnMessage;
    
    public LivesModule() {
        super(new Date(2015, 4, 19), "lives", "1.0");
        this.getDocs().setDescription("Ten moduł dodaje system żyć do meczu. " +
                "Gracz ma do dyspozycji określoną ilość żyć, po śmierci zostaje " +
                "on przeniesiony do obserwatorów.");
        this.addExample(new ConfigurationDoc(true, ConfigurationDoc.Type.INT) {
            @Override
            public String getPrefix() {
                return "Oczywiście istnieje możliwość ustawienia ilości żyć dla " +
                        "gracza. Domyślnie są to <code>3</code> życia. Na mapach " +
                        "zaleca się ustawienie jednego życia, rozgrywka z taką " +
                        "ilością zyć trwa bardzo krótko.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "lives:",
                    "  lives: 3"
                };
            }
            
            @Override
            public String getSuffix() {
                return "Minimalna ilość żyć to <code>1</code>. Nie zaleca się " +
                        "ustawiania większej ilości niż <code>10</code>, ponieważ " +
                        "gra stanie się monotonna.";
            }
        });
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.MESSAGE) {
            @Override
            public String getPrefix() {
                return "Możesz zmienić wszelkie wiadomości do graczy, jakie wysyła ten " +
                        "moduł. Poniżej znajduje się wiadomość przy wyrzuceniu " +
                        "meczu. Następuje to po skończeniu wszyskich żyć. Gracz " +
                        "zostaje przeniesiony do obserwatorów.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "lives:",
                    "  kick-message: '`cStraciles/as wszystkie (%s) zycia, odpadasz z gry!'",
                };
            }
        });
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.MESSAGE) {
            @Override
            public String getPrefix() {
                return "Możesz zmienić wszelkie wiadomości do graczy, jakie wysyła ten " +
                        "moduł. Poniżej znajduje się wiadomość przy odradzaniu " +
                        "gracza oraz przy rozpoczęciu meczu.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "lives:",
                    "  respawn-message: '`aPozostalo Ci jeszcze `3`l%s zyc`r`a.'"
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
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (!player.getTeam().getID().equals(ObserverTeamBuilder.getTeamID())) {
                this.lives.put(player.getUUID(), this.defaults);
                if (this.respawnMessage != null) {
                    player.sendMessage(String.format(this.respawnMessage, this.defaults));
                }
            }
        }
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        this.defaults = Config.getValueInt(config, this, "lives");
        
        this.kickMessage = Config.getValueMessage(config, this, "kick-message",
                Color.RED + "Straciles/as wszystkie (%s) zycia, odpadasz z gry!", true);
        this.respawnMessage = Config.getValueMessage(config, this, "respawn-message",
                Color.GREEN + "Pozostalo Ci jeszcze " + Color.DARK_AQUA + Color.BOLD
                        + "%s" + " zyc" + Color.RESET + Color.GREEN + ".", true);
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage() == null) {
            return;
        }
        
        UUID player = e.getEntity().getUniqueId();
        if (this.lives.containsKey(player)) {
            int newState = this.lives.get(player) - 1;
            if (newState > 0) {
                this.lives.put(player, newState);
            } else {
                this.handleMatchQuit(Arcade.getServer().getPlayer(player));
            }
        } else {
            this.lives.put(player, this.defaults - 1);
        }
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        if (this.lives.containsKey(player) && this.lives.get(player) > 0 && this.respawnMessage != null) {
            e.getPlayer().sendMessage(String.format(this.respawnMessage, this.lives.get(player)));
        }
    }
    
    private void handleMatchQuit(final Player player) {
        this.lives.remove(player.getUUID());
        if (this.kickMessage != null) {
            player.sendMessage(String.format(this.kickMessage, this.defaults));
        }
        
        Team team = Arcade.getTeams().getObservers();
        player.setTeam(team);
        player.sendMessage(String.format(JoinCommand.JOIN_MESSAGE, team.getDisplayName()));
        
        ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
        
        new BukkitRunnable() {
            @Override
            public void run() {
                Arcade.getServer().checkEndMatch();
            }
        }.runTaskLaterAsynchronously(ArcadeBukkitPlugin.getPlugin(), 5L);
    }
}
