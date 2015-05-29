/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.location;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class GameableBlock {
    private static List<GameableBlock> blocks = new ArrayList<>();
    
    private final Block block;
    
    public GameableBlock(Block block) {
        Validate.notNull(block, "block can not be null");
        this.block = block;
    }
    
    public boolean canBreak(Player player) {
        return true;
    }
    
    public boolean canInteract(Player player, Material item) {
        return true;
    }
    
    public boolean canPlace(Player player, Material block) {
        return true;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public static GameableBlock getBlock(Location location) {
        Validate.notNull(location, "location can not be null");
        for (GameableBlock block : getBlocks()) {
            if (block.getBlock().getLocation().equals(location)) {
                return block;
            }
        }
        return null;
    }
    
    public static List<GameableBlock> getBlocks() {
        return blocks;
    }
    
    public static void reset() {
        blocks = new ArrayList<>();
    }
    
    public static void register(GameableBlock block) {
        Validate.notNull(block, "block can not be null");
        blocks.add(block);
    }
}
