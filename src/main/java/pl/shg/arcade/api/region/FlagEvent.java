/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.region;

import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Block;

/**
 *
 * @author Aleksander
 */
public class FlagEvent {
    public boolean canBreak(Player player, Block block) {
        return true;
    }
    
    public boolean canInteract(Player player, Material item) {
        return true;
    }
    
    public boolean canMove(Player player) {
        return true;
    }
    
    public boolean canPlace(Player player, Block block) {
        return true;
    }
}
