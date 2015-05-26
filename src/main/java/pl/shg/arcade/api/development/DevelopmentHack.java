/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.command.def.SetnextCommand;

/**
 *
 * @author Aleksander
 */
public class DevelopmentHack {
    public static void setVariables() {
        try {
            Field field = SetnextCommand.class.getDeclaredField("sameMap");
            field.setAccessible(true);
            field.setBoolean(null, true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(DevelopmentHack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
