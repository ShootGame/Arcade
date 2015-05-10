/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class SpyCommand extends Command {
    public SpyCommand() {
        super(new String[] {"spy"},
                "Wlacz lub wylacz podgladanie chatu", "spy [list|off|on]");
        this.setPermission("arcade.command.spy");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                this.handle(sender, !player.isSpying());
            } else {
                this.showList(sender);
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            this.showList(sender);
        } else if (sender.isConsole()) {
            this.throwMessage(sender, CommandMessage.PLAYER_NEEDED);
        } else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
            this.handle(sender, false);
        } else if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) {
            this.handle(sender, true);
        } else {
            sender.sendError(this.getUsage());
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void showList(Sender sender) {
        List<Player> spy = new ArrayList<>();
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (player.isSpying()) {
                spy.add(player);
            }
        }
        
        if (spy.isEmpty()) {
            sender.sendError("Obecnie nikt nie szpieguje chatu.");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Player player : spy) {
                builder.append(player.getDisplayName()).append(Color.GRAY).append(", ");
            }
            
            sender.sendMessage(Command.getTitle("Szpiedzy chatu", Color.GRAY + "(" + spy.size() + ")"));
            sender.sendMessage(builder.toString());
        }
    }
    
    private void handle(Sender sender, boolean spy) {
        ((Player) sender).setSpying(spy);
        
        String message;
        if (spy) {
            message = "wlaczone";
        } else {
            message = "wylaczone";
        }
        sender.sendSuccess("Szpiegowanie zostalo " + Color.UNDERLINE + message + Color.RESET + Color.GREEN + ".");
    }
}
