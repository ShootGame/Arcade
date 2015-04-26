/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.team.ObserverTeamBuilder;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.module.ScoreboardScore;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ScoreboardManager {
    public static final Scoreboard SCOREBOARD = Bukkit.getScoreboardManager().getMainScoreboard();
    
    public static void setTeams() {
        for (org.bukkit.scoreboard.Team team : ScoreboardManager.SCOREBOARD.getTeams()) {
            team.unregister();
        }
        
        org.bukkit.scoreboard.Team obs = ScoreboardManager.SCOREBOARD.registerNewTeam(ObserverTeamBuilder.getTeamID());
        obs.setPrefix(Arcade.getTeams().getObservers().getColor());
        
        for (Team team : Arcade.getTeams().getTeams()) {
            org.bukkit.scoreboard.Team bukkitTeam = ScoreboardManager.SCOREBOARD.registerNewTeam(team.getID());
            bukkitTeam.setPrefix(team.getColor());
        }
    }
    
    public static void updateSidedar(Player player) {
        if (ScoreboardManager.SCOREBOARD.getObjective("sidebar") != null) {
            ((org.bukkit.entity.Player) player.getPlayer()).setScoreboard(ScoreboardManager.SCOREBOARD);
        }
    }
    
    public static void updateTag(Player player) {
        org.bukkit.scoreboard.Team team = ScoreboardManager.SCOREBOARD.getTeam(player.getTeam().getID());
        team.add(player.getName());
        ((org.bukkit.entity.Player) player.getPlayer()).setScoreboard(ScoreboardManager.SCOREBOARD);
    }
    
    public static class Sidebar {
        public static final String ID = "sidebar";
        private static Objective objective;
        private static List<ScoreboardScore> scores = new ArrayList<>();
        
        public static void clear() {
            scores = new ArrayList<>();
            for (Objective obj : ScoreboardManager.SCOREBOARD.getObjectives()) {
                if (obj.getName().equals(Sidebar.ID)) {
                    System.out.println("odrejestorwywuje " + obj.getName());
                    obj.unregister();
                }
            }
        }
        
        public static ScoreboardScore getScore(String name, int score) {
            Validate.notNull(name, "name can not be null");
            Validate.notNegative(score, "score can not be negative");
            Validate.notNull(score, "score can not be zero");
            
            for (ScoreboardScore boardScore : getScores()) {
                if (boardScore.getName().toLowerCase().equals(name.toLowerCase())) {
                    boardScore.setScore(score);
                    return boardScore;
                }
            }
            
            ScoreboardScore boardScore = new ScoreboardScore(name, score);
            scores.add(boardScore);
            return boardScore;
        }
        
        public static List<ScoreboardScore> getScores() {
            return scores;
        }
        
        public static ScoreboardScore getSeparator() {
            ScoreboardScore scoreBoard = new ScoreboardScore("");
            scores.add(scoreBoard);
            return scoreBoard;
        }
        
        public static void newScoreboard() {
            String name = Color.GREEN + Arcade.getMaps().getCurrentMap().getDisplayName();
            Scoreboard board = ScoreboardManager.SCOREBOARD;
            
            objective = board.registerNewObjective(Sidebar.ID, "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(name);
            
            updateScoreboard();
        }
        
        private static void updateScoreboard() {
            System.out.println("scory: " + getScores());
            for (ScoreboardScore score : getScores()) {
                System.out.println("dodawanie scoru " + score.getName() + ": " + score.getScore());
                objective.getScore(score.getName()).setScore(1);
            }
        }
    }
}
