/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.documentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.module.Module;

/**
 *
 * @author Aleksander
 */
public class ModuleDescription {
    private final Module module;
    
    private final Changelog changelog;
    private final List<IDeprectation> deprecation;
    private String description;
    
    public ModuleDescription(Module module) {
        Validate.notNull(module, "module can not be null");
        this.module = module;
        
        this.changelog = new Changelog();
        this.deprecation = new ArrayList<>();
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDeprecation(IDeprectation deprecation) {
        Validate.notNull(deprecation, "description can not be null");
        this.deprecation.add(deprecation);
    }
    
    @Docs
    public Changelog getChangelog() {
        return this.changelog;
    }
    
    @Docs
    public String getConfigurationPath() {
        return this.getModule().getConfigPath();
    }
    
    @Docs
    public List<Class<? extends Module>> getOptionalDependencies() {
        return this.getModule().getDependencies(Module.DependencyType.OPTIONAL);
    }
    
    @Docs
    public List<Class<? extends Module>> getStrongDependencies() {
        return this.getModule().getDependencies(Module.DependencyType.STRONG);
    }
    
    @Docs
    public String getDescription() {
        return this.description;
    }
    
    @Docs
    public List<IDeprectation> getDeprecation() {
        return this.deprecation;
    }
    
    @Docs
    public List<ConfigurationDoc> getExamples() {
        return this.getModule().getExamples();
    }
    
    @Docs
    public String getID() {
        return this.getModule().getID();
    }
    
    @Docs
    public Date getReleaseDate() {
        return this.getModule().getDate();
    }
    
    @Docs
    public String getLastVersion() {
        return this.getModule().getVersion().toString();
    }
    
    @Docs
    public boolean isDeployed() {
        return this.getModule().isDeployed();
    }
}
