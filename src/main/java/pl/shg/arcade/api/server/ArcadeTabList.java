/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.TabListUpdateEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;
import pl.shg.commons.util.TabCell;

/**
 *
 * @author Aleksander
 */
public class ArcadeTabList extends TabList {
    public void createCustom() {
        List<Team> teams = Arcade.getTeams().getTeams();
        for (int i = 0; i < teams.size(); i++) {
            this.addCell(this.getTeamTitle(i, teams.get(i)));
        }
    }
    
    public String getRawHeader() {
        Map map = Arcade.getMaps().getCurrentMap();
        return Color.AQUA + Color.BOLD + Color.ITALIC + map.getDisplayName() + Color.RESET +
                Color.GRAY + " v" + map.getVersionString() + Color.DARK_PURPLE + " by " +
                map.getAuthorsString(Color.GOLD, Color.DARK_PURPLE);
    }
    
    public String getRawFooter() {
        StringBuilder builder = new StringBuilder();
        for (Team team : Arcade.getTeams().getTeams()) {
            builder.append(team.getDisplayName()).append(Color.GOLD).append(" - ")
                    .append(Color.DARK_PURPLE).append(Color.BOLD).append(team.getPlayers().size())
                    .append(Color.RESET).append(Color.GOLD).append("/").append(team.getSlots())
                    .append(Color.GRAY).append(", ");
        }
        return builder.toString().substring(0, builder.toString().length() - 2);
    }
    
    public TabCell getTeamTitle(int index, Team team) {
        Validate.notNull(team, "team can not be null");
        TabCell cell = this.getCell(index, 0);
        cell.setName(team.getDisplayName());
        return cell;
    }
    
    public void push() {
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            player.setTabList(this);
        }
    }
    
    public void update() {
//        this.createCustom();
        TabListUpdateEvent event = new TabListUpdateEvent(this);
        Event.callEvent(event);
        
        if (!event.isCancel() && event.getTabList() instanceof ArcadeTabList) {
            ArcadeTabList arcadeTabList = (ArcadeTabList) event.getTabList();
            this.setHeader(arcadeTabList.getRawHeader());
            this.setFooter(arcadeTabList.getRawFooter());
            
            this.push();
        }
    }
}
