/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module;

import pl.shg.arcade.api.event.CancelableEvent;
import pl.shg.arcade.api.event.Event;

/**
 *
 * @author Aleksander
 */
public abstract class ModuleBaseEvent extends CancelableEvent {
    private final Module module;
    
    public ModuleBaseEvent(Class<? extends Event> event, Module module) {
        super(event);
        this.module = module;
    }
    
    public Module getModule() {
        return this.module;
    }
}
