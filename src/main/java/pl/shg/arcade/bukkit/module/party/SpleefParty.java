/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public class SpleefParty extends Party {
    public SpleefParty() {
        super(new Date(2015, 5, 11), "spleef", "Spleef", Version.valueOf("1.0"));
        this.deploy(true);
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Twoim zadaniem jest zabicie jak najwiecej graczy.",
            "Wygrywa gracz który ostatni zostanie w meczu przeciwników."
        };
    }

    @Override
    public void loadParty(File file) {
        
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        if (!player.isObserver()) {
            player.resetPlayerState();
            
            ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
            Arcade.getServer().checkEndMatch();
            this.broadcast(player);
        }
    }
    
    private void broadcast(Player player) {
        String message = player.getDisplayName() + Color.DARK_GREEN + " odpadl/a z gry.";
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            if (!online.equals(player)) {
                online.sendActionMessage(ActionMessageType.SUCCESS, message);
            }
        }
    }
}
