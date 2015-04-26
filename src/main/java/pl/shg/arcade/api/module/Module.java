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
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.api.module.docs.ModuleDescription;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class Module implements IModule {
    private final Date date;
    private final Map<DependencyType, List<Class<? extends Module>>> dependencies;
    private boolean deploy = false;
    private final ModuleDescription docs;
    private final List<ConfigurationDoc> examples;
    private final String id;
    private final String version;
    
    public Module(Date date, String id, String version) {
        Validate.notNull(date, "date can not be null");
        Validate.notNull(id, "id can not be null");
        Validate.notNull(version, "version can not be null");
        this.date = date;
        this.dependencies = new HashMap<>();
        this.docs = new ModuleDescription(this);
        this.examples = new ArrayList<>();
        this.id = id.toLowerCase();
        this.version = version;
        
        this.dependencies.put(DependencyType.OPTIONAL, new ArrayList<Class<? extends Module>>());
        this.dependencies.put(DependencyType.STRONG, new ArrayList<Class<? extends Module>>());
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
    
    public String getVersion() {
        return this.version;
    }
    
    public boolean hasObjective() {
        return false;
    }
    
    public boolean isDeployed() {
        return this.deploy;
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
        OPTIONAL, STRONG;
    }
}
