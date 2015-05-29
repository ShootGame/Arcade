/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.match.MatchStatus;

/**
 *
 * @author Aleksander
 */
public class PlayerMoveListener implements Listener {
    private final Random random = new Random();
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player bukkitPlayer = e.getPlayer();
        if (this.isObserver(bukkitPlayer)) {
            if (e.getTo().getBlockY() < -10) {
                pl.shg.arcade.api.human.Player player = Arcade.getServer().getPlayer(bukkitPlayer.getName());
                List<Spawn> spawns = Arcade.getTeams().getObservers().getSpawns();
                player.teleport(spawns.get(this.random.nextInt(spawns.size())));
            }
        }
    }
    
    private boolean isObserver(Player bukkitPlayer) {
        pl.shg.arcade.api.human.Player player = Arcade.getServer().getPlayer(bukkitPlayer.getUniqueId());
        if (Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            return true;
        } else if (player != null) {
            return player.isObserver();
        } else {
            Log.log(Level.SEVERE, "Gracz " + bukkitPlayer.getName() + " nie istnieje jako Player, wyrzucam.");
            bukkitPlayer.kickPlayer("Drogi Graczu; napotkalismy blad z Twoim uzytkownikiem w grze.\n"
                    + "Prosze zaloguj sie ponownie na serwer.");
            return true;
        }
    }
}
