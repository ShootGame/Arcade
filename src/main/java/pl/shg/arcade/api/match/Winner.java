/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import pl.shg.arcade.api.chat.Color;

/**
 *
 * @author Aleksander
 */
public interface Winner {
    public static final String DEFAULT_COLOR = Color.GOLD;
    
    String getMessage();
    
    String getName();
}
