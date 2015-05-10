/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.NotLoadedMap;
import pl.shg.arcade.api.server.MiniGameServer;
import pl.shg.arcade.api.server.Rotation;
import pl.shg.arcade.api.util.Validate;
import pl.shg.commons.server.ArcadeTarget;
import pl.shg.commons.server.Servers;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class RotationCommand extends Command {
    public RotationCommand() {
        super(new String[] {"rotation", "rot", "rota", "maprot", "maprotation"},
                "Aktualna rotacja na danym/podanym serwerze", "rotation [server...]");
        this.setPermission("arcade.command.rotation");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            MiniGameServer.Online server = MiniGameServer.ONLINE;
            sender.sendMessage(Command.getTitle("Rotacja " + server.getShoot().getName(), null));
            sender.sendMessage(this.rotation(server.getRotation()));
        } else {
            String targetServer = this.getStringFromArgs(0, args);
            int found = 0;
            MiniGameServer rotation = null;
            for (TargetServer server : Servers.getServers()) {
                if (server instanceof ArcadeTarget && server.getName().toLowerCase().contains(targetServer.toLowerCase())) {
                    found++;
                    rotation = MiniGameServer.of((ArcadeTarget) server);
                }
            }
            
            if (found > 1) {
                sender.sendError("Znaleziono wiecej niz jeden serwer. Pokazywanie 1 z " + found + "...");
            }
            
            if (rotation == null) {
                sender.sendError("Nie znaleziono zadnego serwera o podanej kryteriach.");
            } else {
                sender.sendMessage(Command.getTitle("Rotacja " + rotation.getCommons().getName(), null));
                sender.sendMessage(this.rotation(rotation.getRotation()));
                sender.sendMessage(Color.GOLD + "Nie tego szukasz? Uzyj lepszej nazwy lub wcisnij [TAB].");
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private String rotation(Rotation rotation) {
        Validate.notNull(rotation, "rotation");
        
        List<Map> maps = rotation.getMaps();
        StringBuilder builder = new StringBuilder();
        int index = 1;
        for (Map map : maps) {
            if (map.equals(Arcade.getMaps().getCurrentMap())) {
                builder.append(Color.DARK_AQUA).append(index).append(". ");
            } else {
                builder.append(Color.WHITE).append(index).append(". ");
            }
            
            if (map instanceof NotLoadedMap) {
                builder.append(Color.RED).append(Color.ITALIC).append(map.getDisplayName());
                builder.append(Color.DARK_PURPLE ).append(" nie zaladowana");
            } else {
                builder.append(Color.GOLD).append(map.getDisplayName());
                builder.append(Color.DARK_PURPLE).append(" by ").append(map.getAuthorsString(Color.RED, Color.DARK_PURPLE));
            }
            builder.append("\n");
            index++;
        }
        return builder.toString();
    }
}
