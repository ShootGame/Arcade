/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.documentation.ConfigurationDoc;
import pl.shg.arcade.api.documentation.ModuleDescription;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public abstract class Module implements IModule {
    private Date date;
    private final Map<DependencyType, List<Class<? extends Module>>> dependencies;
    private boolean deploy = false;
    private final ModuleDescription docs;
    private final List<ConfigurationDoc> examples;
    private String id;
    private Version version;
    
    public Module(Date date, String id, Version version) {
        this.setDate(date);
        this.setID(id);
        this.setVersion(version);
        
        this.dependencies = new HashMap<>();
        this.docs = new ModuleDescription(this);
        this.examples = new ArrayList<>();
        
        for (DependencyType type : DependencyType.values()) {
            this.dependencies.put(type, new ArrayList<Class<? extends Module>>());
        }
    }
    
    public void addDependency(DependencyType type, Class<? extends Module> dependency) {
        Validate.notNull(type, "type can not be null");
        Validate.notNull(dependency, "dependency can not be null");
        this.dependencies.get(type).add(dependency);
    }
    
    public void addExample(ConfigurationDoc example) {
        Validate.notNull(example, "example can not be null");
        this.examples.add(example);
    }
    
    public void clearExamples() {
        this.examples.clear();
    }
    
    public void deploy(boolean deploy) {
        this.deploy = deploy;
    }
    
    public String getConfigPath() {
        return "modules." + this.getID();
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public List<Class<? extends Module>> getDependencies(DependencyType type) {
        Validate.notNull(type, "type can not be null");
        return this.dependencies.get(type);
    }
    
    public ModuleDescription getDocs() {
        return this.docs;
    }
    
    public List<ConfigurationDoc> getExamples() {
        return this.examples;
    }
    
    public final String getID() {
        return this.id;
    }
    
    public Version getVersion() {
        return this.version;
    }
    
    public boolean hasObjective() {
        return false;
    }
    
    public boolean isDeployed() {
        return this.deploy;
    }
    
    public void loadDependencies() {}
    
    public final void setDate(Date date) {
        Validate.notNull(date, "date can not be null");
        this.date = date;
    }
    
    public final void setID(String id) {
        Validate.notNull(id, "id can not be null");
        this.id = id.toLowerCase();
    }
    
    public final void setVersion(Version version) {
        Validate.notNull(version, "version can not be null");
        this.version = version;
    }
    
    public static Module of(Class<? extends Module> module) {
        for (Module active : Arcade.getModules().getActiveModules()) {
            if (active.getClass().equals(module)) {
                return active;
            }
        }
        return null;
    }
    
    public static enum DependencyType {
        OPTIONAL, PARTY, STRONG;
    }
}
