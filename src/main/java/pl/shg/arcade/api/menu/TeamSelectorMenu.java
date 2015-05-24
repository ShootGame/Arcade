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
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.def.JoinCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.team.Team;

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
        MatchStatus status = Arcade.getMatches().getStatus();
        if (status == MatchStatus.ENDING) {
            player.sendError("Mecz sie zakonczyl! " + Color.GOLD + "Poczekaj na serwer az zaladuje nowa mape.");
        } else if (slot == 0) {
            JoinCommand.random(player, status);
            this.close(player);
        } else if (teams.containsKey(slot)) {
            JoinCommand.team(player, teams.get(slot), status);
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
            
            Item auto = new Item(new Material(399));
            auto.setName(Color.DARK_AQUA + "Losuj druzyne");
            auto.setDescription(Arrays.asList(
                    Color.GRAY + "Dolacz do druzyny w której jest najmniej graczy",
                    Color.GOLD + "Dostep: " + Color.GREEN + "Wszyscy"));
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
                
                Item item = new Item(new Material(35, team.getTeamColor().getWool().getID()), freeSlots);
                
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
    
    public static Collection<Team> getTeams() {
        return teams.values();
    }
    
    public static void reset() {
        teams = null;
    }
}
