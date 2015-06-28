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
public class EventException extends RuntimeException {
    public EventException() {
        super();
    }
    
    public EventException(String message) {
        super(message);
    }
    
    public EventException(Throwable cause) {
        super(cause);
    }
    
    public EventException(String message, Throwable cause) {
        super(message, cause);
    }
}
