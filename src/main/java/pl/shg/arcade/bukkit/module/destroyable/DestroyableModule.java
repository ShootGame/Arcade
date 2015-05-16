/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class DestroyableModule extends ObjectiveModule {
    private final List<Destroyable> destroyables = new ArrayList<>();
    
    public DestroyableModule() {
        super(new Date(2015, 5, 11), "destroyable", "1.0");
    }
    
    @Override
    public void disable() {
        
    }
    
    @Override
    public void enable() {
        
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        DestroyableFactory factory = new DestroyableFactory(this);
        
        if (config.isSet(this.getConfigPath() + ".blocks")) {
            factory.set("blocks", this.getBlocksList(config));
        } else if (config.isSet(this.getConfigPath() + ".regions")) {
            factory.set("regions", this.getRegionsList(config));
        }
        
        factory.set("objective", Config.getValueInt(config, this, "objective", 100));
        factory.set("score-mode", ScoreMode.valueOf(Config.getValueString(config,
                this, "score-mode", ScoreMode.STATIC_SILENT.name()).toUpperCase()));
        factory.build();
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public Score[] getMatchInfo(Team team) {
        return new Score[] {
            
        };
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Destroy the monument",
                "Zadaniem Twojej druzyny jest zniszczenie wszyskich monumentów druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna która jako pierwsza zlikwiduje wszystkie monumenty przeciwnika.");
    }
    
    @Override
    public void makeScoreboard() {
        
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        return false;
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        return null;
    }
    
    public List<Destroyable> getDestroyables() {
        return this.destroyables;
    }
    
    public void registerDestroyable(Destroyable destroyable) {
        this.destroyables.add(destroyable);
    }
    
    private List<BlocksDestroyable> getBlocksList(FileConfiguration config) {
        List<BlocksDestroyable> list = new ArrayList<>();
        for (String objective : Config.getOptions(config, this, "blocks")) {
            Team team = Arcade.getTeams().getTeam(Config.getValueString(config, this, "blocks." + objective + ".owner"));
            BlocksDestroyable blocks = new BlocksDestroyable(objective, team);
            blocks.setMonuments(this.getBlocksMonuments(config, blocks, objective));
            
            blocks.appendSetting(Destroyable.Setting.MODE, Config.getValueString(config, this,
                    "blocks." + objective + ".mode", ScoreMode.STATIC_SILENT.name()).toUpperCase());
            blocks.appendSetting(Destroyable.Setting.OBJECTIVE, Config.getValueInt(config, this,
                    "blocks." + objective + ".objective", 100));
            list.add(blocks);
        }
        return list;
    }
    
    private List<Monument> getBlocksMonuments(FileConfiguration config, BlocksDestroyable destroyable, String objective) {
        List<Monument> monuments = new ArrayList<>();
        for (String monument : Config.getValueList(config, this, "blocks." + objective + ".monuments")) {
            String[] args = monument.split(";");
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            monuments.add(new Monument(new BlockLocation(x, y, z), destroyable));
        }
        return monuments;
    }
    
    private List<RegionsDestroyable> getRegionsList(FileConfiguration config) {
        return Lists.newArrayList();
    }
}
