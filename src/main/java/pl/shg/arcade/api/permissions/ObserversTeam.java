/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.permissions;

import pl.shg.arcade.api.team.ObserverTeamBuilder;

/**
 *
 * @author Aleksander
 */
public class ObserversTeam extends ArcadeTeam {
    public ObserversTeam() {
        super(ObserverTeamBuilder.getTeamID());
    }
}
