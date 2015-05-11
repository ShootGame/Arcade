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
import pl.shg.arcade.api.chat.ActionMessageType;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.def.JoinCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class SpleefParty extends Party {
    public SpleefParty() {
        super(new Date(2015, 5, 11), "spleef", "Spleef", "1.0");
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
        if (e.getDeathMessage() == null) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        if (!player.isObserver()) {
            Team observers = Arcade.getTeams().getObservers();
            
            player.setTeam(observers);
            player.sendMessage(String.format(JoinCommand.JOIN_MESSAGE, observers.getDisplayName()));
            player.setHealth(0.0);
            
            if (Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
                this.updateObjectives();
            }
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
