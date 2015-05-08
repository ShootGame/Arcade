/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Score extends ScoreboardScore {
    private static final List<Score> scores = new ArrayList<>();
    private String id;
    
    public Score(String id, String name) {
        super(name);
        this.setID(id);
    }
    
    public Score(Team team) {
        this(team.getID(), team.getColor(), team.getName());
    }
    
    public Score(String id, String color, String name) {
        super(color, null, name, 0);
        this.setID(id);
    }
    
    public Score(String id, String color, String name, String score) {
        super(color, score, name, 0);
        this.setID(id);
    }
    
    @Override
    public boolean equals(Object obj) {
        Validate.notNull(obj, "obj can not be null");
        if (obj instanceof Score) {
            return ((Score) obj).getID().equals(this.getID());
        }
        return false;
    }
    
    public String getID() {
        return this.id;
    }
    
    @Deprecated
    public final void setID(String id) {
        Validate.notNull(id, "id can not be null");
        this.id = id;
    }
    
    public static Score byID(String id) {
        return byID(id, null);
    }
    
    public static Score byID(String id, Score def) {
        Validate.notNull(id, "id can not be null");
        for (Score score : scores) {
            if (score.getID().equals(id.toLowerCase())) {
                return score;
            }
        }
        
        if (def != null) {
            def.id = id;
            register(def);
        }
        return null;
    }
    
    public static Score byID(Team team, String id) {
        Validate.notNull(team, "team can not be null");
        Validate.notNull(id, "id can not be null");
        return byID(team, id, null);
    }
    
    public static Score byID(Team team, String id, Score def) {
        Validate.notNull(team, "team can not be null");
        Validate.notNull(id, "id can not be null");
        return byID(team.getID() + "-" + id, def);
    }
    
    public static int clear() {
        int result = scores.size();
        scores.clear();
        return result;
    }
    
    public static List<Score> getScores() {
        return scores;
    }
    
    public static void register(Score score) {
        Validate.notNull(score, "score can not be null");
        scores.add(score);
    }
}
