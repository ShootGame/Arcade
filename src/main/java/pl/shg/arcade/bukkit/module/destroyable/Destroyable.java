/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.BlockLocation;

/**
 *
 * @author Aleksander
 */
public interface Destroyable {
    void appendSetting(Setting setting, Object value);
    
    boolean canDestroy(Player player, BlockLocation block);
    
    void destroy(Player player);
    
    int getPercent();
    
    Object getSettingValue(Setting setting);
    
    DestroyStatus getStatus();
    
    enum Setting {
        // Type of scoreboard to show; percents/default/default with the touched option
        MODE,
        // Percents to complete this destroyable, should be 100 if whole, never 0
        OBJECTIVE,
        ;
    }
}
