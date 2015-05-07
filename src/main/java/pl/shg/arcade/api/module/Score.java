/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module;

import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class Score extends ScoreboardScore {
    public Score(String name) {
        super(name);
    }
    
    public Score(Team team) {
        this(team.getColor(), team.getName());
    }
    
    public Score(String color, String name) {
        super(color, null, name, 0);
    }
    
    public Score(String color, String name, String score) {
        super(color, score, name, 0);
    }
}
