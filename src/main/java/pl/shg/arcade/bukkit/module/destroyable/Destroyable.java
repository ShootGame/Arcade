/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public interface Destroyable {
    boolean canDestroy(Player player);
    
    void destroy(Player player);
    
    int getPercent();
    
    DestroyStatus getStatus();
}
