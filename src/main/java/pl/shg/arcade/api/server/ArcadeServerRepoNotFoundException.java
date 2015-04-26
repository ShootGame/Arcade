/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

/**
 *
 * @author Aleksander
 */
public class ArcadeServerRepoNotFoundException extends RuntimeException {
    public ArcadeServerRepoNotFoundException() {
        super();
    }
    
    public ArcadeServerRepoNotFoundException(String message) {
        super(message);
    }
    
    public ArcadeServerRepoNotFoundException(Throwable cause) {
        super(cause);
    }
    
    public ArcadeServerRepoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
