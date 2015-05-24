/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.protocol;

import java.lang.annotation.Annotation;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public enum Protocol {
    R1_0_0(Version.valueOf("1.0.0"), getAnnotation(R1_0_0.class)),
    ;
    
    private final Version version;
    private final ProtocolInfo info;
    
    private Protocol(Version version, ProtocolInfo info) {
        Validate.notNull(version, "version can not be null");
        Validate.notNull(info, "info can not be null");
        this.version = version;
        this.info = info;
    }
    
    public ProtocolInfo getInfo() {
        return this.info;
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
        return Protocol.R1_0_0;
    }
    
    private static ProtocolInfo getAnnotation(Class<?> clazz) {
        Annotation annotation = clazz.getDeclaredAnnotation(ProtocolInfo.class);
        if (annotation != null) {
            return (ProtocolInfo) annotation;
        } else {
            throw new UnsupportedOperationException(clazz.getSimpleName() + " must be @ProtocolInfo annotated.");
        }
    }
}
