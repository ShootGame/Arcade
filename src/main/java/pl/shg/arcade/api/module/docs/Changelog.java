/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module.docs;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Changelog {
    private final List<Version> versions = new ArrayList<>();
    
    public void addVersion(Version version) {
        Validate.notNull(version, "version can not be null");
        this.versions.add(version);
    }
    
    public void append(String version, String... changes) {
        Validate.notNull(version, "version can not be null");
        Validate.notNull(changes, "changes can not be null");
        this.addVersion(new Version(version, changes));
    }
    
    @Docs
    public List<Version> getVersions() {
        return this.versions;
    }
    
    public class Version {
        private final String name;
        private final String[] changes;
        
        public Version(String name, String[] changes) {
            Validate.notNull(name, "name can not be null");
            Validate.notNull(changes, "changes can not be null");
            this.name = name;
            this.changes = changes;
        }
        
        @Docs
        public String getName() {
            return this.name;
        }
        
        @Docs
        public String[] getChanges() {
            return this.changes;
        }
    }
}
