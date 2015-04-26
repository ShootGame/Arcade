/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.menu;

import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class MenuListener {
    public void onClick(Player player, int slot) {
        Log.log(Level.SEVERE, "Method onClick(...) is not defined in the MenuListener implementation (should be @Override).");
    }
    
    public void onCreate(Player player) {
        Log.log(Level.SEVERE, "Method onCreate(...) is not defined in the MenuListener implementation (should be @Override).");
    }
}
