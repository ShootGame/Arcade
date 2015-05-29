/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.kit;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class PotionOption extends Option {
    private final String id;
    private final int level, time;
    
    public PotionOption(String id, int level, int time) {
        super("potion");
        Validate.notNull(id, "id can not be null");
        Validate.isTrue(level > 0);
        Validate.notNull(time, "time can not be negative");
        this.id = id;
        this.level = level;
        this.time = time;
    }
    
    @Override
    public void apply(Player player) {
        Option.PLAYERS.addPotion(player, this.getID(), this.getLevel(), this.getTime());
    }
    
    public String getID() {
        return this.id;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getTime() {
        return this.time;
    }
}
