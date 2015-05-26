/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.Command.CommandMessage;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.PlayerJoinTeamEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.arcade.api.server.ArcadeTabList;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.RandomTeamComparator;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class JoinCommand extends Command {
    public static final String JOIN_MESSAGE = Color.GRAY + "Dolaczyles/as do %s" + Color.RESET + Color.GRAY + ".";
    
    public JoinCommand() {
        super(new String[] {"join", "play", "dolacz", "graj"},
                "Dolacz do losowej lub wybranej druzyny", "join [-r|obs|<team>]",
                new char[] {'r'});
        this.setOption("-r", "losuj");
        this.setOption("obs", "przejdz do obserwatorów");
        this.setPermission("arcade.command.join");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        MatchStatus status = Arcade.getMatches().getStatus();
        if (sender.isConsole()) {
            this.throwMessage(sender, CommandMessage.PLAYER_NEEDED);
        } else if (status == MatchStatus.ENDING) {
            sender.sendError("Mecz sie zakonczyl! " + Color.GOLD + "Poczekaj na serwer az zaladuje nowa mape.");
        } else if (args.length == 0 || this.hasFlag(args, 'r')) {
            Player player = (Player) sender;
            if (hasTeam(player)) {
                sender.sendError("Juz dolaczyles/as do druzyny! Aby ja zmienic wejdz do druzyny obserwatorów /leave");
            } else {
                random((Player) sender, status);
            }
        } else if (args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("obs")) {
            observer((Player) sender, status);
        } else if (!sender.hasPermission("arcade.command.join.team")) {
            sender.sendError("Dolaczanie do wybranej druzyny dostepne jest tylko dla rangi "
                    + Color.GOLD + Color.BOLD + "VIP" + Color.RESET + Color.RED + ".");
        } else if (hasTeam((Player) sender)) {
            sender.sendError("Juz dolaczyles/as do druzyny! Aby ja zmienic wejdz do druzyny obserwatorów /leave.");
        } else {
            team((Player) sender, args[0], status);
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    public static void checkForSchedule() {
        SchedulerManager manager = Arcade.getServer().getScheduler();
        if (!manager.isBeginRunning()) {
            boolean canBegin = true;
            for (Team team : Arcade.getTeams().getTeams()) {
                if (team.getPlayers().size() < team.getMinimum()) {
                    canBegin = false;
                    break;
                }
            }
            
            if (canBegin) {
                manager.runBegin(BeginScheduler.getDefaultSeconds());
            }
        }
    }
    
    public static boolean hasTeam(Player player) {
        return !player.getTeam().getID().equals(ObserverTeamBuilder.getTeamID());
    }
    
    public static void random(Player player, MatchStatus status) {
        List<Team> teams = Arcade.getTeams().getTeams();
        if (teams.size() == 1) {
            team(player, teams.get(0), Arcade.getMatches().getStatus());
        } else {
            SortedMap<Integer, Team> sorted = new TreeMap<>(new RandomTeamComparator());
            for (Team team : teams) {
                sorted.put(team.getPlayers().size(), team);
            }
            
            Team team = sorted.get(0);
            if (team == null) {
                team = teams.get(0);
            }
            
            team(player, team, status);
        }
    }
    
    public static void team(Player player, String team, MatchStatus status) {
        Team teamObject = Arcade.getTeams().getTeam(team.toLowerCase());
        if (teamObject != null) {
            team(player, teamObject, status);
        } else {
            player.sendError("Podana przez Ciebie druzyna nie zostala odnaleziona! Spróbuj jeszcze raz.");
        }
    }
    
    public static void team(Player player, Team team, MatchStatus status) {
        if (!player.hasPermission("arcade.command.join.team")) {
            player.sendError("Dolaczanie do wybranej druzyny dostepne jest tylko dla rangi "
                    + Color.GOLD + Color.BOLD + "VIP" + Color.RESET + Color.RED + ".");
        } else if (!player.hasPermission("arcade.command.join.full") && team.getPlayers().size() >= team.getSlots()) {
            player.sendError("Druzyna " + team.getName() + " jest zapelniona! Poczekaj na zwolnienie miejsca.");
            return;
        }
        
        PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, team);
        Event.callEvent(event);
        
        if (!event.isCancel()) {
            player.setTeam(team);
            player.sendMessage(String.format(JoinCommand.JOIN_MESSAGE, team.getDisplayName()));
            
            if (status == MatchStatus.PLAYING) {
                Arcade.getPlayerManagement().setAsPlayer(player, KitType.JOIN, true, true, true);
            }
            
            ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
            checkForSchedule();
        }
    }
    
    public static void observer(Player player, MatchStatus status) {
        Team team = Arcade.getTeams().getObservers();
        if (player.getTeam().getID().equals(team.getID())) {
            player.sendError("Juz znajdujesz sie w druzynie obserwatorów!");
        } else {
            PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, team);
            Event.callEvent(event);
            
            if (!event.isCancel()) {
                player.setTeam(team);
                if (status == MatchStatus.PLAYING) {
                    player.setHealth(0.0);
                }
                player.sendMessage(String.format(JoinCommand.JOIN_MESSAGE, team.getDisplayName()));
                
                ((ArcadeTabList) Arcade.getServer().getGlobalTabList()).update();
                Arcade.getServer().checkEndMatch();
            }
        }
    }
}
