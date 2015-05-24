/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.protocol.Protocol;
import pl.shg.arcade.api.region.RegionManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Map {
    private String[] authors;
    private final List<ArcadeClass> classes;
    private final String displayName;
    private final String name;
    private String objective;
    private Protocol protocol;
    private final MapRatings ratings;
    private final RegionManager regions;
    private boolean switchingClass;
    private final Tutorial tutorial;
    private MapVersion version;
    
    public Map(String[] authors, String name, Protocol protocol, MapVersion version) {
        Validate.notNull(name, "name can not be null");
        this.authors = authors;
        this.classes = new ArrayList<>();
        this.displayName = name.replace("_", " ");
        this.name = name;
        this.protocol = protocol;
        this.ratings = new MapRatings();
        this.regions = new RegionManager(this);
        this.switchingClass = true;
        this.tutorial = new Tutorial();
        this.version = version;
    }
    
//  @Deprecated
//  public boolean exists() {
//      return new File(Arcade.getMaps().getMapsDirectory().getPath() + File.separator + this.getName() + File.separator + Configuration.FILE).exists();
//  }
    
    public boolean exists() {
        return true;
    }
    
    public String[] getAuthors() {
        return this.authors;
    }
    
    public String getAuthorsString(String authorColor, String color) {
        Validate.notNull(authorColor, "authorColor can not be null");
        Validate.notNull(color, "color can not be null");
        if (this.getAuthors() == null) {
            return authorColor + Color.ITALIC + "(nieznani)" + Color.RESET;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.getAuthors().length; i++) {
                if (i != 0) {
                    builder.append(color);
                    if (this.getAuthors().length == (i + 1)) {
                        builder.append(" oraz ");
                    } else {
                        builder.append(", ");
                    }
                }
                builder.append(authorColor).append(this.authors[i]);
            }
            return Color.RESET + builder.toString();
        }
    }
    
    public void setAuthors(String[] authors) {
        Validate.notNull(authors, "authors can not be null");
        this.authors = authors;
    }
    
    public ArcadeClass findClass(String query) {
        Validate.notNull(query, "query can not be null");
        for (ArcadeClass clazz : this.getClasses()) {
            if (clazz.getName().toLowerCase().contains(query.toLowerCase())) {
                return clazz;
            }
        }
        return null;
    }
    
    public ArcadeClass getClassExact(String name) {
        Validate.notNull(name, "name can not be null");
        for (ArcadeClass clazz : this.getClasses()) {
            if (clazz.getName().toLowerCase().equals(name.toLowerCase())) {
                return clazz;
            }
        }
        return null;
    }
    
    public List<ArcadeClass> getClasses() {
        return this.classes;
    }
    
    public ArcadeClass getDefaultClass() {
        if (this.hasClasses()) {
            return this.classes.get(0);
        } else {
            return null;
        }
    }
    
    public boolean hasClasses() {
        return !(this.classes == null || this.classes.isEmpty());
    }
    
    public boolean isSwitchingClassAllowed() {
        return this.switchingClass;
    }
    
    public void registerClass(ArcadeClass arcadeClass) {
        Validate.notNull(arcadeClass, "class can not be null");
        ArcadeClass clazz = this.getClassExact(arcadeClass.getName());
        if (clazz == null) {
            this.classes.add(arcadeClass);
            Log.log(Level.INFO, "Zarejestrowano klase " + arcadeClass.getName() + " - " + arcadeClass.getDescription());
        }
    }
    
    public void setSwitchingClassAllowed(boolean switchingClass) {
        this.switchingClass = switchingClass;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getObjective() {
        return this.objective;
    }
    
    public void setObjective(String objective) {
        Validate.notNull(objective, "objective can not be null");
        this.objective = objective;
    }
    
    public Protocol getMapProto() {
        return this.protocol;
    }
    
    public void setMapProto(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public MapRatings getRatings() {
        return this.ratings;
    }
    
    public RegionManager getRegions() {
        return this.regions;
    }
    
    public Tutorial getTutorial() {
        return this.tutorial;
    }
    
    public MapVersion getVersion() {
        return this.version;
    }
    
    public void setVersion(MapVersion version) {
        this.version = version;
    }
    
    public String getVersionString() {
        if (this.version != null) {
            return this.version.toString();
        } else {
            return "unknown";
        }
    }
}
