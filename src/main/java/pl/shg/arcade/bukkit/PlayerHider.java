/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;

/**
 *
 * @author Aleksander
 */
public class PlayerHider {
    public static void refresh(Player player) {
        MatchStatus status = Arcade.getMatches().getStatus();
        if (player.isObserver() && status == MatchStatus.PLAYING) {
            hide(player, getBukkit(player));
        } else {
            show(player, getBukkit(player));
        }
    }
    
    public static void refreshAll() {
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            refresh(player);
        }
    }
    
    private static org.bukkit.entity.Player getBukkit(Player player) {
        return (org.bukkit.entity.Player) player.getPlayer();
    }
    
    private static void hide(Player player, org.bukkit.entity.Player bukkitPlayer) {
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            org.bukkit.entity.Player bukkit = getBukkit(player);
            
            if (!online.isObserver()) {
                bukkit.hidePlayer(bukkitPlayer);
            }
        }
    }
    
    private static void show(Player player, org.bukkit.entity.Player bukkitPlayer) {
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            org.bukkit.entity.Player bukkit = getBukkit(player);
            
            bukkit.showPlayer(bukkitPlayer);
            if (online.isObserver()) {
                bukkitPlayer.hidePlayer(bukkit);
            }
        }
    }
}
