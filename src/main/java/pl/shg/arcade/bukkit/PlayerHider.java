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
        if (!player.isObserver() || status == MatchStatus.ENDING || status == MatchStatus.STARTING) {
            show(getBukkit(player));
        } else {
            hide(getBukkit(player));
        }
    }
    
    public static void refreshAll() {
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            refresh(player);
        }
    }
    
    private static org.bukkit.entity.Player getBukkit(Player player) {
        return (org.bukkit.entity.Player) player.getPlayer();
    }
    
    private static void hide(org.bukkit.entity.Player bukkitPlayer) {
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (!player.isObserver()) {
                getBukkit(player).hidePlayer(bukkitPlayer);
            }
        }
    }
    
    private static void show(org.bukkit.entity.Player bukkitPlayer) {
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            getBukkit(player).showPlayer(bukkitPlayer);
        }
    }
}
