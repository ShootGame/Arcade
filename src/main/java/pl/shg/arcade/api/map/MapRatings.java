/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import java.util.UUID;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class MapRatings {
    public int getPercent() {
        return 0 * 10 * 2;
    }
    
    public int getRated() {
        return 0;
    }
    
    public int getGlobalRating() {
        return 0;
    }
    
    public Rating getRating(Player player) {
        Validate.notNull(player, "player can not be null");
        return null;
    }
    
    public Rating getRating(UUID id) {
        Validate.notNull(id, "id can not be null");
        return null;
    }
}
