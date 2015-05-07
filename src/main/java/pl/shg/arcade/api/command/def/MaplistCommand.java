/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapManager;

/**
 *
 * @author Aleksander
 */
public class MaplistCommand extends Command {
    public MaplistCommand() {
        super(new String[] {"maplist", "maps", "ml"},
                "Pokaz liste zaladowanych map", "maplist");
        this.setPermission("arcade.command.maplist");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        MapManager maps = Arcade.getMaps();
        StringBuilder builder = new StringBuilder();
        for (Map map : maps.getMaps()) {
            builder.append(Color.GOLD).append(map.getDisplayName()).append(Color.DARK_PURPLE).append(", ");
        }
        sender.sendMessage(Command.getTitle("Zaladowane mapy", Color.GRAY + "(" + maps.getMaps().size() + ")"));
        sender.sendMessage(builder.toString().substring(0, builder.toString().length() - 1));
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
