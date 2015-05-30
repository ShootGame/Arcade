/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class Visibility {
    public static void update(Player player) {
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            update(player, online);
            update(online, player);
        }
    }
    
    private static org.bukkit.entity.Player getBukkit(Player player) {
        return ((BukkitPlayer) player).bukkit();
    }
    
    private static void update(Player player, Player other) {
        if (player.canSee(other)) {
            getBukkit(player).showPlayer(getBukkit(other));
        } else {
            getBukkit(other).showPlayer(getBukkit(player));
        }
    }
}
