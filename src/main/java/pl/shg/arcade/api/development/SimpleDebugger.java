/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import java.util.logging.Level;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Debugger;
import pl.shg.arcade.api.Log;

/**
 *
 * @author Aleksander
 */
public class SimpleDebugger implements Debugger {
    @Override
    public void debug(Level level, String message) {
        Validate.notNull(message);
        
        if (level == null) {
            level = Level.INFO;
        }
        
        Log.log(level, message);
    }
}
