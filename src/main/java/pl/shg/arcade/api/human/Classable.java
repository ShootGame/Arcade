/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.human;

import pl.shg.arcade.api.classes.ArcadeClass;

/**
 *
 * @author Aleksander
 */
public interface Classable {
    ArcadeClass getArcadeClass();
    
    void setArcadeClass(ArcadeClass arcadeClass);
}
