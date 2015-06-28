/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.abilities;

import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.classes.ArcadeClass;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class AbilityClass extends ArcadeClass {
    public AbilityClass(String name, String description) {
        this(name, description, null, null);
    }
    
    public AbilityClass(String name, String description, String fullDescription, Material icon) {
        super(name, description + Color.ITALIC + " (magiczna)" + Color.RESET, fullDescription, icon);
    }
}
