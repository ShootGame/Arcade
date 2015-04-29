/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    
    public static void updateTag(Player player) {
        org.bukkit.scoreboard.Team team = ScoreboardManager.SCOREBOARD.getTeam(player.getTeam().getID());
        team.add(player.getName());
        ((org.bukkit.entity.Player) player.getPlayer()).setScoreboard(ScoreboardManager.SCOREBOARD);
    }
    
    public static class Sidebar {
        public static final String ID = "sidebar";
        private static Objective objective;
        private static final Random random = new Random();
        private static Map<String, ScoreboardScore> scores = new HashMap<>();
        
        public static void clear() {
            scores = new HashMap<>();
            for (Objective obj : ScoreboardManager.SCOREBOARD.getObjectives()) {
                if (obj.getName().equals(Sidebar.ID)) {
                    obj.unregister();
                }
            }
        }
        
        public static ScoreboardScore getScore(String id, String name, int score) {
            Validate.notNull(id, "id can not be null");
            
            ScoreboardScore scoreboardScore = scores.get(id.toLowerCase());
            if (scoreboardScore != null) {
                if (name != null) {
                    scoreboardScore.setName(name);
                }
                
                scoreboardScore.setScore(score);
                return scoreboardScore;
            } else {
                Validate.notNull(name, "name can not be null");
                
                ScoreboardScore newScore = new ScoreboardScore(name, score);
                scores.put(id.toLowerCase(), newScore);
                return newScore;
            }
        }
        
        public static Collection<ScoreboardScore> getScores() {
            return scores.values();
        }
        
        public static ScoreboardScore getSeparator() {
            ScoreboardScore scoreBoard = new ScoreboardScore("");
            scores.put("sep-" + random.nextInt(1000), scoreBoard);
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
        
        public static void updateScoreboard() {
            for (String score : scores.keySet()) {
                objective.getScore(score).setScore(scores.get(score).getScore());
            }
        }
    }
}
