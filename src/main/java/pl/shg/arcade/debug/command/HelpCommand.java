/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.command;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class HelpCommand extends Command {
    public HelpCommand() {
        super(new String[] {"help", "?"},
                "Wyświetl pomoc dla tej implementacji Arcade", "help [command]");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            sender.sendMessage(Command.getTitle("Pomoc", null));
            sender.sendMessage(Color.YELLOW + "Poniżej znajduje się lista komend do których posiadasz dostęp");
            for (Command command : Arcade.getCommands().getCommands()) {
                sender.sendMessage(Color.WHITE + command.getUsage().substring(1) + Color.YELLOW + " - " + command.getDescription());
            }
        } else {
            
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
