/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class LeaveCommand extends Command {
    public LeaveCommand() {
        super(new String[] {"leave", "quit", "q", "wyjdz", "obs"},
                "Wyjdz z gry dolaczajac do druzyny obserwatorów", "leave");
        this.setPermission("arcade.command.leave");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (sender.isConsole()) {
            this.throwMessage(sender, CommandMessage.PLAYER_NEEDED);
        } else {
            JoinCommand.observer((Player) sender, Arcade.getMatches().getStatus());
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
