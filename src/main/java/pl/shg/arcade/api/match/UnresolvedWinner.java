/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class UnresolvedWinner implements Winner {
    @Override
    public String getMessage() {
        return Color.GREEN + Color.BOLD + "        Remis!        ";
    }
    
    @Override
    public String getName() {
        return "Remis";
    }
}
