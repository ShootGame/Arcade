/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.Command.CommandMessage;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class MyteamCommand extends Command {
    public MyteamCommand() {
        super(new String[] {"myteam", "mt"},
                "Wyswietla informacje w której druzynie sie znajdujesz", "myteam [-r]",
                new char[] {'r'});
        this.setOption("-r", "surowa informacja");
        this.setPermission("arcade.command.myteam");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (sender.isConsole()) {
            this.throwMessage(sender, CommandMessage.PLAYER_NEEDED);
        } else if (this.hasFlag(args, 'r')) {
            sender.sendMessage(Color.YELLOW + ((Player) sender).getTeam().getName());
        } else {
            sender.sendMessage(Color.GRAY + "Obecnie znajdujesz sie w druzynie " +
                    ((Player) sender).getTeam().getDisplayName() + Color.RESET + Color.GRAY + ".");
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
