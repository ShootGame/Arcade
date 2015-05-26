/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.ChatChannel;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class GlobalCommand extends Command {
    public GlobalCommand() {
        super(new String[] {"global", "g", "all"},
                "Wyslij wiadomosc na chacie ogólnym", "global [-f] <message...>",
                new char[] {'f'});
        this.setOption("-f", "wyslij jako konsola");
        this.setPermission("arcade.command.global");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        String message = this.getStringFromArgs(0, args);
        ChatChannel channel = Arcade.getTeams().getGlobalChannel();
        if (this.hasFlag(args, 'f')) {
            if (!this.canNot(sender, "arcade.command.global.fake")) {
                if (args.length > 1) {
                    this.sendAsConsole(channel, message);
                } else {
                    sender.sendError("Podaj tresc wiadomosci do wyslania!");
                    sender.sendError("/global -f <message...>");
                }
            }
        } else if (sender instanceof Player) {
            this.sendAsPlayer(channel, (Player) sender, message);
        } else {
            this.sendAsConsole(channel, message);
        }
    }
    
    @Override
    public int minArguments() {
        return 1;
    }
    
    private void sendAsConsole(ChatChannel channel, String message) {
        channel.sendServerMessage(Arcade.getCommands().getConsoleSender(), channel.getFormat(new String[] {
            Color.DARK_RED + Color.BOLD + Color.ITALIC + "*Console", message}));
    }
    
    private void sendAsPlayer(ChatChannel channel, Player player, String message) {
        channel.sendMessage(player, message);
    }
}
