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
        
        public static ScoreboardScore getScore(String id) {
            Validate.notNull(id, "id can not be null");
            return scores.get(id.toLowerCase());
        }
        
        public static ScoreboardScore getScore(String id, String name, int score) {
            return getScore(id, name, score, null, null);
        }
        
        public static ScoreboardScore getScore(String id, String name, int score, String prefix, String suffix) {
            Validate.notNull(id, "id can not be null");
            
            ScoreboardScore scoreboardScore = getScore(id.toLowerCase());
            if (scoreboardScore != null) {
                if (name != null) {
                    scoreboardScore.setName(name);
                }
                scoreboardScore.setPrefix(prefix);
                scoreboardScore.setScore(score);
                scoreboardScore.setSuffix(suffix);
                return scoreboardScore;
            } else {
                Validate.notNull(name, "name can not be null");
                org.bukkit.scoreboard.Team team = ScoreboardManager.SCOREBOARD.getTeam(name) == null ?
                        ScoreboardManager.SCOREBOARD.registerNewTeam(name) :
                        ScoreboardManager.SCOREBOARD.getTeam(name);
                if (prefix != null) {
                    team.setPrefix(prefix);
                }
                if (suffix != null) {
                    team.setSuffix(suffix);
                }
                team.add(name);
                ScoreboardScore newScore = new ScoreboardScore(prefix, suffix, name, score);
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
            String name = Arcade.getMaps().getCurrentMap().getDisplayName();
            Scoreboard board = ScoreboardManager.SCOREBOARD;
            
            objective = board.registerNewObjective(Sidebar.ID, "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(name);
            
            updateScoreboard();
        }
        
        public static void updateScoreboard() {
            for (String score : scores.keySet()) {
                ScoreboardScore obj = scores.get(score);
                org.bukkit.scoreboard.Team team = ScoreboardManager.SCOREBOARD.getTeam(obj.getName()) == null ?
                        ScoreboardManager.SCOREBOARD.registerNewTeam(obj.getName()) :
                        ScoreboardManager.SCOREBOARD.getTeam(obj.getName());
                if (obj.getPrefix() != null) {
                    team.setPrefix(obj.getPrefix());
                }
                if (obj.getSuffix() != null) {
                    team.setSuffix(obj.getSuffix());
                }
                team.add(obj.getName());
                if (obj.isNameEdited()) {
                    objective.getScoreboard().resetScores(obj.getOldName());
                }
                objective.getScore(obj.getName()).setScore(obj.getScore());
            }
        }
    }
}
