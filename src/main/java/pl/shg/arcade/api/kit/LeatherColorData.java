/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.kit;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.team.TeamColor;

/**
 *
 * @author Aleksander
 */
public class LeatherColorData extends KitData {
    private final TeamColor color;
    
    public LeatherColorData(TeamColor color) {
        Validate.notNull(color, "color can not be null");
        this.color = color;
    }
    
    public TeamColor getColor() {
        return this.color;
    }
}
