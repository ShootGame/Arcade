/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tournament;

import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class TournamentCommand extends Command {
    public TournamentCommand() {
        super(new String[] {"tournament"},
                "Zarzadzanie zawodami na serwerze", "tournament");
        this.setPermission("arcade.command.tournament");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            this.showHelp(sender);
        } else {
            switch (args[0].toLowerCase()) {
                case "join":
                    this.executeJoin(sender, args);
                    break;
                case "setteam":
                    this.executeSetteam(sender, args);
                    break;
                default:
                    sender.sendError(args[0].toLowerCase() + " nie jest komenda zawodów.");
                    sender.sendError("Uzyj " + this.getUsage() + ", aby wyswietlic pomoc.");
                    break;
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 2;
    }
    
    private void executeJoin(Sender sender, String[] args) {
        
    }
    
    private void executeSetteam(Sender sender, String[] args) {
        
    }
    
    private void showHelp(Sender sender) {
        sender.sendMessage(Command.getTitle("Zawody", null));
    }
}
