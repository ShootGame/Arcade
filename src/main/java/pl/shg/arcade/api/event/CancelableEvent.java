/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

/**
 *
 * @author Aleksander
 */
public class CancelableEvent extends Event {
    private boolean cancel = false;
    
    public CancelableEvent(Class<? extends Event> event) {
        super(event);
    }
    
    public boolean isCancel() {
        return this.cancel;
    }
    
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
