/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

/**
 *
 * @author Aleksander
 */
public class ModuleException extends RuntimeException {
    public ModuleException() {
        super();
    }
    
    public ModuleException(String message) {
        super(message);
    }
    
    public ModuleException(Throwable cause) {
        super(cause);
    }
    
    public ModuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
