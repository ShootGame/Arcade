/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.party;

import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.CycleScheduler;
import pl.shg.arcade.api.server.community.Community;

/**
 *
 * @author Aleksander
 */
public class Party extends Community {
    @Override
    public void onServerEnable() {
        Log.log(Level.INFO, "#################");
        Log.log(Level.INFO, "#    -PARTY-    #");
        Log.log(Level.INFO, "#################");
        
        BeginScheduler.setDefaultSeconds(10);
        CycleScheduler.setDefaultSeconds(5);
        
        this.registerDatabase();
    }
}
