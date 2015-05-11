/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.event.CancelableEvent;

/**
 *
 * @author Aleksander
 */
public class DestroyableDestroyEvent extends CancelableEvent {
    private Destroyable destroyable;
    
    public DestroyableDestroyEvent(Destroyable destroyable) {
        super(DestroyableDestroyEvent.class);
        this.setDestroyable(destroyable);
    }
    
    public Destroyable getDestroyable() {
        return this.destroyable;
    }
    
    private void setDestroyable(Destroyable destroyable) {
        this.destroyable = destroyable;
    }
}
