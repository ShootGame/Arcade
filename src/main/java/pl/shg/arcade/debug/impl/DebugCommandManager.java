/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import pl.shg.arcade.ArcadeCommandManager;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandManager;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.debug.command.ExitCommand;
import pl.shg.arcade.debug.command.HelpCommand;

/**
 *
 * @author Aleksander
 */
public class DebugCommandManager extends ArcadeCommandManager {
    @Override
    public boolean registerServerCommand(Command command) {
        System.out.println("Komenda " + command.getCommands()[0] + " zostala zarejestrowana.");
        return true;
    }
    
    public static void perform(Sender sender, String[] args) {
        String command = args[0].toLowerCase();
        
        String[] arguments = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arguments[i - 1] = args[i];
        }
        
        if (!Arcade.getCommands().performAlias(command, sender, arguments)) {
            sender.sendError(String.format(
                    "Nie znaleziono komendy %s. Uzyj komendy %s, aby wyswietlic pomoc.",
                    
                    "\"" + command + "\"",
                    "\"help\""
            ));
        }
    }
    
    public static void registerDebugCommands() {
        CommandManager commands = Arcade.getCommands();
        commands.registerCommand(new ExitCommand());
        commands.registerCommand(new HelpCommand());
    }
}
