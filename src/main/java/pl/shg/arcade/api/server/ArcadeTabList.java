/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.team.Team;

/**
 *
 * @author Aleksander
 */
public class ArcadeTabList extends TabList {
    public void update() {
        Map map = Arcade.getMaps().getCurrentMap();
        String header = Color.GOLD + Color.BOLD + map.getDisplayName() + Color.RESET +
                Color.GRAY + " v" + map.getVersionString() + Color.DARK_PURPLE +
                " by " + map.getAuthorsString(Color.GOLD, Color.DARK_PURPLE);
        
        StringBuilder builder = new StringBuilder();
        for (Team team : Arcade.getTeams().getTeams()) {
            builder.append(team.getDisplayName()).append(Color.GOLD).append(" - ")
                    .append(Color.DARK_PURPLE).append(Color.BOLD).append(team.getPlayers().size())
                    .append(Color.RESET).append(Color.GOLD).append("/").append(team.getSlots())
                    .append(Color.GRAY).append(", ");
        }
        String footer = builder.toString().substring(0, builder.toString().length() - 2);
        
        this.setHeader(header);
        this.setFooter(footer);
        
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            player.setTabList(this);
        }
    }
}
