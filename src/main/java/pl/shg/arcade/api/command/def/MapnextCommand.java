/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.NotLoadedMap;
import pl.shg.arcade.api.server.MiniGameServer;
import pl.shg.arcade.api.server.Rotation;

/**
 *
 * @author Aleksander
 */
public class MapnextCommand extends Command {
    public MapnextCommand() {
        super(new String[] {"mapnext", "nextmap", "mn", "nm", "next"},
                "Informacje o nastepnej mapie w kolejce", "mapnext");
        this.setPermission("arcade.command.mapnext");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        Map next = this.getPlugin().getMaps().getNextMap();
        if (next != null) {
            MapinfoCommand.show(sender, next);
        } else {
            Rotation rotation = MiniGameServer.ONLINE.getRotation();
            for (Map map : rotation.getMaps()) {
                if (!(map instanceof NotLoadedMap)) {
                    sender.sendMessage(Color.RED + "Wczesniej serwer zostanie zrestartowany!");
                    MapinfoCommand.show(sender, map);
                    return;
                }
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
