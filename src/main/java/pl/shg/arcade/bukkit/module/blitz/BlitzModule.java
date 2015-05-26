/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.blitz;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.match.MatchType;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.ScoreboardManager;

/**
 *
 * @author Aleksander
 */
public class BlitzModule extends ObjectiveModule implements BListener {
    private int defaults = 3;
    private final HashMap<UUID, Integer> lives = new HashMap<>();
    private String kickMessage, respawnMessage;
    
    public BlitzModule() {
        super(new Date(2015, 4, 26), "blitz", Version.valueOf("1.0"));
        this.getDocs().setDescription("Dodaje tryb gry, w którym wygrywa drużyna " +
                "w której ostatni zostaną gracze. Gracz ma do dyspozycji określoną " +
                "ilość żyć, po śmierci zostaje on przeniesiony do obserwatorów.");
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
                    "blitz:",
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
                    "blitz:",
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
                    "blitz:",
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
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), team.getPlayers().size());
        }
        ScoreboardManager.Sidebar.updateScoreboard();
        
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
        this.defaults = Config.getValueInt(config, this, "lives", 3);
        
        this.kickMessage = Config.getValueMessage(config, this, "kick-message",
                Color.RED + "Straciles/as wszystkie (%s) zycia, odpadasz z gry!", true);
        this.respawnMessage = Config.getValueMessage(config, this, "respawn-message",
                Color.GREEN + "Pozostalo Ci jeszcze " + Color.DARK_AQUA + Color.BOLD
                        + "%s" + " zyc" + Color.RESET + Color.GREEN + ".", true);
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public Score[] getMatchInfo(Team team) {
        return new Score[] {
            Score.byID(team, "blitz", new Score(new String(),
                    Color.GOLD, "Gracze" + Color.RED + ": ", Color.DARK_AQUA + Color.BOLD + team.getPlayers().size()))
        };
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Blitz",
                "Twoim zadaniem jest przetrwanie ataku druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna, która jako ostatnia zostanie w grze.");
    }
    
    @Override
    public void makeScoreboard() {
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), 0);
        }
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        MatchType matchType = Arcade.getMatches().getMatch().getType();
        List<Team> stay = new ArrayList<>();
        
        for (Team onlineTeam : Arcade.getTeams().getTeams()) {
            if (!onlineTeam.getPlayers().isEmpty() || matchType == MatchType.PLAYERS) {
                stay.add(onlineTeam);
            }
        }
        
        if (stay.size() == 1) { // if list contains one team
            if (matchType == MatchType.PLAYERS && stay.get(0).getPlayers().size() <= 1) { // if type is players
                return true;
            } else if (stay.get(0).equals(team)) { // if this one team is this team
                return true;
            }
        }
        return false;
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        return null;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage() == null) {
            return;
        }
        Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        Arcade.getPlayerManagement().playSound(player, Sound.ELIMINATION);
        
        Team oldTeam = player.getTeam();
        UUID uuid = e.getEntity().getUniqueId();
        if (!this.lives.containsKey(uuid)) {
            this.lives.put(uuid, this.defaults - 1);
        }
        
        int newState = this.lives.get(uuid) - 1;
        if (newState > 0) {
            this.lives.put(uuid, newState);
        } else {
            this.handleMatchQuit(player);
            if (!oldTeam.getID().equals(ObserverTeamBuilder.getTeamID())) {
                ScoreboardManager.Sidebar.getScore(oldTeam.getID(), null, oldTeam.getPlayers().size());
            }
            
            if (Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
                this.updateObjectives();
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (this.lives.containsKey(uuid) && this.lives.get(uuid) > 0 && this.respawnMessage != null) {
            e.getPlayer().sendMessage(String.format(this.respawnMessage, this.lives.get(uuid)));
        }
    }
    
    private void handleMatchQuit(final Player player) {
        this.lives.remove(player.getUUID());
        if (this.kickMessage != null) {
            player.sendMessage(String.format(this.kickMessage, this.defaults));
        }
        
        player.resetPlayerState();
        ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
        
        if (Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
            Arcade.getServer().checkEndMatch();
        }
    }
}
