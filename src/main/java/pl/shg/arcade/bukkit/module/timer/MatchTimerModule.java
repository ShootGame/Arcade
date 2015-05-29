/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.timer;

import java.io.File;
import java.util.Date;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.match.UnresolvedWinner;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.tablist.ArcadeTabList;
import pl.shg.arcade.api.tablist.TabListUpdateEvent;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class MatchTimerModule extends Module {
    private TabListUpdate tabListUpdate;
    private int taskID;
    private long ticks = 300L;
    private String winner = "auto";
    
    public MatchTimerModule() {
        super(new Date(2015, 5, 8), "match-timer", Version.valueOf("1.0"));
        
        this.getDocs().setDescription("Zakończenie meczu po danym w konfiguracji " +
                "czasie. Dodaje także licznik czasu do listy pod klawiszem TAB.");
        this.addExample(new ConfigurationDoc(true, ConfigurationDoc.Type.SECONDS) {
            @Override
            public String getPrefix() {
                return "Ustaw ilość sekund, po których mecz ma się zakończyć.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "match-timer:",
                    "  time: 300"
                };
            }
            
            @Override
            public String getSuffix() {
                return "W powyższym przykładzie mecz zakończy się po <code>300" +
                        "</code> sekundach (5 minut).";
            }
        });
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.WINNER) {
            @Override
            public String getPrefix() {
                return "Możesz ustawić drużynę, która wygra po zakończeniu trwania " +
                        "meczu. Nie ma możliwości podania dokładnej nazwy drużyny " +
                        "lub gracza.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "match-timer:",
                    "  winner: auto"
                };
            }
            
            @Override
            public String getSuffix() {
                return "W powyższym przykładzie po zakończeniu odliczania, mecz " +
                        "wygra najlepsza drużyna lub gracz.";
            }
        });
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Arcade.getServer().getScheduler().cancel(this.taskID);
    }
    
    @Override
    public void enable() {
        this.taskID = Arcade.getServer().getScheduler().runSync(new Task());
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        this.ticks = Config.getValueLong(config, this, "time", 120) * 20L;
        this.winner = Config.getValueString(config, this, "winner");
        
        this.tabListUpdate = new TabListUpdate();
        Event.registerListener(this.tabListUpdate);
    }
    
    @Override
    public void unload() {
        Event.unregisterListener(this.tabListUpdate);
    }
    
    private class TabListObject extends ArcadeTabList {
        private final int minutesInt, secondsInt;
        
        public TabListObject(boolean tabOnly) {
            this.minutesInt = this.getMinutesInt();
            this.secondsInt = this.getSecondsInt();
            
            if (!tabOnly) {
                this.broadcast();
                this.playSound();
            }
        }
        
        // Override Bungee's footer too
        @Override
        public String getRawBungeeFooter() {
            return super.getRawFooter() + "\n\n" + this.getTime();
        }
        
        @Override
        public String getRawFooter() {
            return super.getRawFooter() + "\n\n" + this.getTime();
        }
        
        private void broadcast() {
            // Broadcast when:
            //    X minutes left
            //    0 - 10 and 15/30/45 seconds left
            if (this.secondsInt == 0 && this.minutesInt != 0) {
                Arcade.getServer().broadcast(Color.GREEN + "Do konca meczu pozostalo " +
                        Color.GOLD + Color.BOLD + this.minutesInt + Color.GREEN + " minut.");
            } else if (this.minutesInt == 0 && (this.secondsInt <= 5 || this.secondsInt == 10 ||
                    this.secondsInt == 15 || this.secondsInt == 30 || this.secondsInt == 45)) {
                Arcade.getServer().broadcast(Color.GREEN + "Do konca meczu pozostalo " +
                        Color.GOLD + Color.BOLD + this.secondsInt + Color.GREEN + " sekund.");
            }
        }
        
        private String getColor() {
            if (this.minutesInt <= 0 && this.secondsInt < 15 && (this.secondsInt & 1) == 0) {
                return Color.DARK_RED;
            }
            return Color.GOLD;
        }
        
        private String getMinutes() {
            int minutes = this.minutesInt;
            if (minutes < 10) {
                return "0" + minutes;
            }
            return String.valueOf(minutes);
        }
        
        private int getMinutesInt() {
            return (int) this.getTicks() / 20 / 60;
        }
        
        private String getSeconds() {
            int seconds = this.secondsInt;
            if (seconds < 10) {
                return "0" + seconds;
            }
            return String.valueOf(seconds);
        }
        
        private int getSecondsInt() {
            return (int) this.getTicks() / 20 - (this.getMinutesInt() * 60);
        }
        
        private long getTicks() {
            return MatchTimerModule.this.ticks;
        }
        
        private String getTime() {
            return Color.DARK_PURPLE + "Pozostaly czas: " + this.getColor() + this.getMinutes() + ":" + this.getSeconds();
        }
        
        private void playSound() {
            PlayerManagement players = Arcade.getPlayerManagement();
            if (this.minutesInt == 0 && this.secondsInt < 20 && this.secondsInt > 0) {
                for (Player player : Arcade.getServer().getConnectedPlayers()) {
                    players.playSound(player, Sound.TICK, 0.25F, 2F);
                }
            }
            
            if (this.minutesInt == 0 && this.secondsInt == 5) {
                for (Player player : Arcade.getServer().getConnectedPlayers()) {
                    players.playSound(player, Sound.TIME_OUT, 1F, 0.78F);
                }
            }
        }
    }
    
    private class TabListUpdate implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return TabListUpdateEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            TabListUpdateEvent e = (TabListUpdateEvent) event;
            if (e.getTabList() instanceof ArcadeTabList) {
                e.setTabList(new TabListObject(Arcade.getMatches().getStatus() != MatchStatus.PLAYING));
            }
        }
    }
    
    private class Task implements Runnable {
        @Override
        public void run() {
            MatchTimerModule.this.ticks = MatchTimerModule.this.ticks - 20L;
            ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
            
            if (MatchTimerModule.this.ticks <= 0) {
                this.finish();
            }
        }
        
        private Winner findAuto() {
            // TOOD find best team or player in the current match
            return new UnresolvedWinner();
        }
        
        private void finish() {
            Winner winner = null;
            String winnerString = MatchTimerModule.this.winner;
            if (winnerString != null) {
                switch (winnerString.toLowerCase()) {
                    case "auto":
                        winner = this.findAuto();
                        break;
                    case "none":
                        winner = null;
                        break;
                    case "unresolved":
                        winner = new UnresolvedWinner();
                        break;
                    default:
                        winner = this.findAuto();
                        break;
                }
            }
            Arcade.getMatches().getMatch().end(winner);
        }
    }
}
