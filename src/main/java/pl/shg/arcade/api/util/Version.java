/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.util;

/**
 *
 * @author Aleksander
 */
public class Version {
    public static final char SECTION = ' ';
    public static final Version DEFAULT = new Version(1, 0);
    
    private final int major, minor, patch;
    
    public Version(int major) {
        this(major, 0);
    }
    
    public Version(int major, int minor) {
        this(major, minor, 0);
    }
    
    public Version(int major, int minor, int patch) {
        Validate.notNegative(major, "major can not be negative");
        Validate.notNegative(minor, "minor can not be negative");
        Validate.notNegative(patch, "patch can not be negative");
        
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    
    public int getMajor() {
        return this.major;
    }
    
    public int getMinor() {
        return this.minor;
    }
    
    public boolean hasMinor() {
        return this.minor != 0;
    }
    
    public int getPatch() {
        return this.patch;
    }
    
    public boolean hasPatch() {
        return this.patch != 0;
    }
    
    public boolean isEqualTo(Version version) {
        return this.equals(version);
    }
    
    public boolean isNewerThan(Version version) {
        Validate.notNull(version, "version can not be null");
        if (this.equals(version)) {
            return false;
        } else {
            return !this.isNewerThan(version);
        }
    }
    
    public boolean isOlderThan(Version version) {
        Validate.notNull(version, "version can not be null");
        if (this.equals(version)) {
            return false;
        } else {
            return version.getMajor() > this.getMajor() ||
                    (version.getMajor() == this.getMajor() &&
                    version.getMinor() > this.getMinor() ||
                    (version.getMinor() == this.getMinor() &&
                    version.getPatch() > this.getPatch()));
        }
    }
    
    public String toString(boolean full) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getMajor()).append(".").append(this.getMinor());
        if (full || this.hasPatch()) {
            builder.append(SECTION).append(this.getPatch());
        }
        return builder.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        Validate.notNull(obj, "obj can not be null");
        if (obj instanceof Version) {
            Version version = (Version) obj;
            return version.getMajor() == this.getMajor() &&
                    version.getMinor() == this.getMinor() &&
                    version.getPatch() == this.getPatch();
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public static boolean isValid(String s) {
        return valueOf(s) != null;
    }
    
    public static Version valueOf(String s) {
        return valueOf(s, SECTION);
    }
    
    public static Version valueOf(String s, char section) {
        Validate.notNull(s, "s can not be null");
        Validate.notNull(section, "section can not be null");
        
        String[] parts = s.split("\\" + Character.toString(section), 3);
        int major, minor = 0, patch = 0;
        
        try {
            major = Integer.valueOf(parts[0]);
            if (parts.length > 1) {
                minor = Integer.valueOf(parts[1]);
            }
            if (parts.length > 2) {
                patch = Integer.valueOf(parts[2]);
            }
            return new Version(major, minor, patch);
        } catch (Throwable ex) {
            return DEFAULT;
        }
    }
}
