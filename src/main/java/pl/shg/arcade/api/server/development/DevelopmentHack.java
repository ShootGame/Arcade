/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.development;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.CycleScheduler;

/**
 *
 * @author Aleksander
 */
@Deprecated
public class DevelopmentHack {
    public static void setFinalVariables() {
        try {
            Field field = BeginScheduler.class.getField("DEFAULT_SECONDS");
            field.setAccessible(true);
            field.setInt(null, 60);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(DevelopmentHack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Field field = CycleScheduler.class.getField("DEFAULT_SECONDS");
            field.setAccessible(true);
            field.setInt(null, 60);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(DevelopmentHack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
