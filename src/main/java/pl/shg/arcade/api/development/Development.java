/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import java.util.logging.Level;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.command.CommandManager;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.CycleScheduler;
import pl.shg.arcade.api.server.IServerRole;
import pl.shg.arcade.api.tournament.MessageListeners;

/**
 *
 * @author Aleksander
 */
public class Development implements IServerRole {
    public static final String COMMAND_PREFIX = "(Development) ";
    
    @Override
    public void onServerEnable() {
        Log.log(Level.INFO, "#################");
        Log.log(Level.INFO, "# -DEVELOPMENT- #");
        Log.log(Level.INFO, "#################");
        
        Log.setDebugger(new SimpleDebugger());
//        Event.setListener(new SimpleCallListener());
        
        this.registerCommands();
        DevelopmentHack.setVariables();
        
        // why do not use tournament's extra messages?
        new MessageListeners().register();
        
        BeginScheduler.setDefaultSeconds(10);
        CycleScheduler.setDefaultSeconds(10);
        
//        Arcade.getPermissions().registerDefaultGroup(new TesterGroup());
    }
    
    private void registerCommands() {
        CommandManager manager = Arcade.getCommands();
        manager.registerCommand(new DbeginCommand());
        manager.registerCommand(new DcycleCommand());
        manager.registerCommand(new TestCommand());
    }
}
