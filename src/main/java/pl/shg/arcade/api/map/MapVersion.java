/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public class MapVersion extends Version {
    public MapVersion(int major, int minor) {
        super(major, minor);
    }
    
    public MapVersion(int major, int minor, int patch) {
        super(major, minor, patch);
    }
    
    @Override
    public boolean hasMinor() {
        return true;
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public boolean isDevelopment() {
        return this.getMajor() == 0;
    }
    
    public static boolean isValid(String s) {
        return valueOf(s) != null;
    }
    
    public static MapVersion valueOf(String s) {
        Version version = Version.valueOf(s);
        if (version != null) {
            return new MapVersion(version.getMajor(), version.getMinor(), version.getPatch());
        }
        return null;
    }
}
