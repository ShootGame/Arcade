/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.human;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.match.MatchStatus;

/**
 *
 * @author Aleksander
 */
public class VisibilityFilter {
    // This could be overrided by the super classes
    public boolean canSee(Player player, Player other) {
        return this.canSeeDefault(player, other);
    }
    
    public final boolean canSeeDefault(Player player, Player other) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(other, "other can not be null");
        
        if (Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            return true;
        } else if (!player.isObserver() && other.isObserver()) {
            return false;
        } else {
            return true;
        }
    }
}
