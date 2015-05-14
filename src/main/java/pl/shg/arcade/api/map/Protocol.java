/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public enum Protocol {
    P1_0_0(Version.valueOf("1.0.0")),
    ;
    
    private final Version version;
    
    private Protocol(Version version) {
        Validate.notNull(version, "version can not be null");
        this.version = version;
    }
    
    public Version toVersion() {
        return this.version;
    }
    
    public static Protocol byVersion(Version version) {
        Validate.notNull(version, "version can not be null");
        for (Protocol protocol : Protocol.values()) {
            if (protocol.toVersion().equals(version)) {
                return protocol;
            }
        }
        return null;
    }
    
    public static Protocol getCurrent() {
        return Protocol.P1_0_0;
    }
}
