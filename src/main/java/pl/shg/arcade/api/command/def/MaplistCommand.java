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
    private String list;
    
    public MaplistCommand() {
        super(new String[] {"maplist", "maps", "ml"},
                "Pokaz liste zaladowanych map", "maplist");
        this.setPermission("arcade.command.maplist");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (this.list == null) {
            this.list = this.build();
        }
        
        sender.sendMessage(Command.getTitle("Zaladowane mapy", Color.GRAY + "(" + Arcade.getMaps().getMaps().size() + ")"));
        sender.sendMessage(this.list);
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private String build() {
        StringBuilder builder = new StringBuilder();
        for (Map map : Arcade.getMaps().getMaps()) {
            builder.append(Color.GOLD).append(map.getDisplayName()).append(Color.DARK_PURPLE).append(", ");
        }
        return builder.toString().substring(0, builder.toString().length() - 2);
    }
}
