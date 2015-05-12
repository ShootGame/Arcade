/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.util.List;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BlocksDestroyable implements Destroyable {
    private final List<Monument> monuments;
    private final String name;
    private final Team team;
    
    public BlocksDestroyable(List<Monument> monuments, String name, Team team) {
        this.monuments = monuments;
        this.name = name;
        this.team = team;
    }
    
    @Override
    public boolean canDestroy(Player player, BlockLocation block) {
        return false;
    }
    
    @Override
    public int getPercent() {
        int percents = 0;
        for (Monument monument : this.getMonuments()) {
            if (monument.isDestroyed()) {
                percents++;
            }
        }
        return (100 / this.getMonuments().size()) * percents;
    }
    
    @Override
    public DestroyStatus getStatus() {
        return DestroyStatus.UNTOUCHED;
    }
    
    public void addMonument(BlockLocation block) {
        Validate.notNull(block, "block can not be null");
        this.monuments.add(new Monument(block, this));
    }
    
    public List<Monument> getMonuments() {
        return this.monuments;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Team getTeam() {
        return this.team;
    }
}
