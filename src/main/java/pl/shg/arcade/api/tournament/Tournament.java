/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tournament;

import java.util.logging.Level;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.CycleScheduler;
import pl.shg.arcade.api.server.IServerRole;

/**
 *
 * @author Aleksander
 */
public class Tournament implements IServerRole {
    @Override
    public void onServerEnable() {
        Log.log(Level.INFO, "#################");
        Log.log(Level.INFO, "# -TOURNAMENT-  #");
        Log.log(Level.INFO, "#################");
        
        Arcade.getCommands().registerCommand(new TournamentCommand());
        Event.registerListener(new CancelCycleListener());
        new MessageListeners().register();
        
        BeginScheduler.setDefaultSeconds(120);
        CycleScheduler.setDefaultSeconds(120);
    }
}
