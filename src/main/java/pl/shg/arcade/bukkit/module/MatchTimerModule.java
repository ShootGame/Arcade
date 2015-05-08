/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.TabListUpdateEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.match.UnresolvedWinner;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class MatchTimerModule extends Module {
    private TabListUpdate tabListUpdate;
    private int taskID;
    public long ticks;
    
    public MatchTimerModule() {
        super(new Date(2015, 5, 8), "match-timer", "1.0");
        
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
                    "match-timer",
                    "  time: 300"
                };
            }
            
            @Override
            public String getSuffix() {
                return "W powyższym przykładzie mecz zakończy się po <code>300" +
                        "</code> sekundach (5 minut).";
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
        this.taskID = Arcade.getServer().getScheduler().run(new Task());
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.ticks = Config.getValueLong(Config.get(file), this, "time") * 20L;
        
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
        
        @Override
        public String getRawFooter() {
            return super.getRawFooter() + "\n\n" + this.getTime();
        }
        
        private void broadcast() {
            // Broadcast when:
            //    X minutes left
            //    0 - 10 and 15/30/45 seconds left
            if (this.secondsInt == 0 && this.minutesInt != 0) {
                String minute = " minut.";
                switch (this.minutesInt) {
                    case 1:
                        minute = " minuta!";
                        break;
                    case 2: case 3: case 4:
                        minute = " minuty.";
                        break;
                }
                
                Arcade.getServer().broadcast(Color.GREEN + "Do konca meczu pozostalo " +
                        Color.GOLD + Color.BOLD + this.minutesInt + Color.GREEN + minute);
            } else if (this.minutesInt == 0 && (this.secondsInt == 10 || this.secondsInt == 15 || this.secondsInt == 30 || this.secondsInt == 45)) {
                Arcade.getServer().broadcast(Color.GREEN + "Do konca meczu pozostalo " +
                        Color.GOLD + Color.BOLD + this.secondsInt + Color.GREEN + " sekund.");
            } else if (this.minutesInt == 0 && this.secondsInt <= 5) {
                String second = " sekund.";
                switch (this.secondsInt) {
                    case 1:
                        second = " sekunda!";
                        break;
                    case 2: case 3: case 4:
                        second = " sekundy.";
                        break;
                }
                
                Arcade.getServer().broadcast(Color.GREEN + "Do konca meczu pozostalo " +
                        Color.GOLD + Color.BOLD + this.secondsInt + Color.GREEN + second);
            }
        }
        
        private String getColor() {
            if (this.minutesInt <= 0 && this.secondsInt < 15) {
                if ((this.secondsInt & 1) == 0) {
                    return Color.DARK_RED;
                } else {
                    return Color.RED;
                }
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
            String color = this.getColor();
            return Color.DARK_PURPLE + "Pozostaly czas: " + color + this.getMinutes() +
                    Color.GOLD + ":" + color + this.getSeconds();
        }
        
        private void playSound() {
            if (this.minutesInt == 0 && this.secondsInt < 10 && this.secondsInt > 0) {
                PlayerManagement players = Arcade.getPlayerManagement();
                for (Player player : Arcade.getServer().getOnlinePlayers()) {
                    players.playSound(player, Sound.TICK);
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
                Arcade.getMatches().getMatch().end(new UnresolvedWinner()); // TOOD call correctly
            }
        }
    }
}
