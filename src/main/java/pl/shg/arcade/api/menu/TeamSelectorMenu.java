/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.menu;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.bukkit.ChatColor;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.map.team.TeamColor;

/**
 *
 * @author Aleksander
 */
public class TeamSelectorMenu extends Menu {
    private static Map map;
    private static SortedMap<Integer, Team> teams;
    
    public TeamSelectorMenu() {
        super(Color.DARK_BLUE + "Wybierz swoja druzyne", 1);
        this.register();
    }
    
    @Override
    public void onClick(Player player, int slot) {
        if (slot == 0) {
            Arcade.getCommands().perform("join", player, new String[] {"-r"});
            this.close(player);
        } else if (teams.containsKey(slot)) {
            Arcade.getCommands().perform("join", player, new String[] {teams.get(slot).getName()});
            this.close(player);
        }
    }
    
    @Override
    public void onCreate(Player player) {
        Map current = Arcade.getMaps().getCurrentMap();
        if (teams == null || !map.equals(current)) {
            this.clear();
            map = current;
            teams = new TreeMap<>();
            
            Item auto = new Item(new Material(399), Color.Wool.ORANGE.getID());
            auto.setName(Color.DARK_AQUA + "Losuj druzyne");
            auto.setDescription(Arrays.asList(
                    Color.GRAY + "Dolacz do druzyny w której jest najmniej graczy",
                    ChatColor.GOLD + "Dostep: " + Color.GREEN + "Wszyscy"));
            this.addItem(auto, 0);
            
            List<Team> teamList = Arcade.getTeams().getTeams();
            for (int i = 0; i < teamList.size(); i++) {
                Team team = teamList.get(i);
                int slot = i + 1;
                teams.put(slot, team);
                
                int freeSlots = team.getSlots() - team.getPlayers().size();
                if (freeSlots < 0) {
                    freeSlots = 0;
                }
                
                Item item = new Item(new Material(35, this.teamToWool(team.getTeamColor()).getID()), freeSlots);
                
                item.setName(Color.DARK_AQUA + "Dolacz do " + team.getDisplayName());
                item.setDescription(Arrays.asList(
                        Color.GOLD + "Stan graczy: " + Color.DARK_RED + Color.BOLD + team.getPlayers().size()
                                + Color.RESET + Color.RED + "/" + team.getSlots(),
                        Color.GRAY + "Dolacz do wybranej przez Ciebie druzyny",
                        Color.GOLD + "Dostep: " + Color.RED + "Ranga VIP"));
                this.addItem(item, slot);
            }
        }
    }
    
    private Color.Wool teamToWool(TeamColor color) {
        switch (color) {
            case BLACK: return Color.Wool.BLACK;
            case BLUE: return Color.Wool.BLUE;
            case GOLD: return Color.Wool.ORANGE;
            case GRAY: return Color.Wool.GRAY;
            case GREEN: return Color.Wool.GREEN;
            case PURPLE: return Color.Wool.PURPLE;
            case RED: return Color.Wool.RED;
            case WHITE: return Color.Wool.WHITE;
            case YELLOW: return Color.Wool.YELLOW;
            default: return Color.Wool.WHITE;
        }
    }
    
    public static Collection<Team> getTeams() {
        return teams.values();
    }
    
    public static void reset() {
        teams = null;
    }
}
