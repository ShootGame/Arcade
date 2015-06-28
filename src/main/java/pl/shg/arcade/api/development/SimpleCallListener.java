/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.event.CallListener;
import pl.shg.arcade.api.event.Event;

/**
 *
 * @author Aleksander
 */
public class SimpleCallListener implements CallListener {
    @Override
    public void call(Event event) {
        Log.debug("Wykonywanie " + event.getEventName() + " z " + event.getEventClass().getSimpleName());
    }
}
