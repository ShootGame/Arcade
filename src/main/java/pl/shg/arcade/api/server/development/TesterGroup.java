/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.development;

import java.util.Arrays;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.themolka.permissions.Group;

/**
 *
 * @author Aleksander
 */
public class TesterGroup extends Group {
    public static final String NAME = PermissionsManager.SERVER_INTERNAL + "tester";
    
    private static final String[] permissions = new String[] {
            //"arcade.command.changelog",
            "arcade.command.class",
            "arcade.command.classes",
            "arcade.command.global",
            "arcade.command.join",
            "arcade.command.join.full",
            "arcade.command.join.team",
            "arcade.command.leave",
            "arcade.command.mapinfo",
            "arcade.command.maplist",
            "arcade.command.mapnext",
            "arcade.command.matchinfo",
            "arcade.command.myteam",
            //"arcade.command.rate",
            //"arcade.command.rating",
            "arcade.command.rotation",
            "arcade.command.teams",
            "arcade.command.tutorial"
    };
    
    public TesterGroup() {
        super(NAME);
        this.setPrefix(null);
        this.setPermissions(Arrays.asList(permissions));
    }
}
