/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.command;

import java.util.UUID;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.development.TestCommand;

/**
 *
 * @author Aleksander
 */
public class CommandTest extends TestCommand.Test {
    public CommandTest() {
        super("command", "[-p:<console|name|uuid>] <command>", true, "wykonanie komendy");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        String command = args[1];
        Sender author = sender;
        String[] arguments = this.getCommandArguments(args, 2);
        
        if (args[1].startsWith("-p")) {
            if (args.length == 2) {
                sender.sendError("Nie podano nazwy komendy.");
                return;
            } else if (args[1].length() <= 3) {
                sender.sendError("Nie podano gracza.");
                return;
            }
            
            command = this.getCommandName(args[2]);
            author = this.getCommandAuthor(args[1].substring(3));
            arguments = this.getCommandArguments(args, 3);
        }
        
        if (author != null) {
            this.perform(command.toLowerCase(), author, arguments);
        } else {
            sender.sendError("Nie znaleziono podanego wysylajacego.");
        }
    }
    
    @Override
    public int minArguments() {
        return 2;
    }
    
    public void perform(String command, Sender sender, String[] args) {
        sender.sendSuccess("Wykonywanie komendy \"/" + command + "\"...");
        Arcade.getCommands().performAlias(command, sender, args);
    }
    
    private String[] getCommandArguments(String[] args, int start) {
        String[] arguments = new String[args.length - start];
        for (int i = start; i < args.length; i++) {
            arguments[i - start] = args[i];
        }
        return arguments;
    }
    
    private Sender getCommandAuthor(String string) {
        if (string.equals("console")) {
            return Arcade.getCommands().getConsoleSender();
        } else if (string.length() == 36) { // UUID
            try {
                return Arcade.getServer().getPlayer(UUID.fromString(string));
            } catch (IllegalArgumentException ex) {
                return null;
            }
        } else {
            return Arcade.getServer().getPlayer(string);
        }
    }
    
    private String getCommandName(String name) {
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        return name;
    }
}
