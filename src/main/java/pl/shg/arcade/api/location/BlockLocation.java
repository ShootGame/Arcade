/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.location;

import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BlockLocation extends Location {
    private final Block block;
    
    public BlockLocation(int x, int y, int z) {
        super(x, y, z);
        this.block = new Block(this);
    }
    
    public BlockLocation(Block block, int x, int y, int z) {
        super(x, y, z);
        Validate.notNull(block, "block can not be null");
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public int getBlockX() {
        return (int) this.getX();
    }
    
    public int getBlockY() {
        return (int) this.getY();
    }
    
    public int getBlockZ() {
        return (int) this.getZ();
    }
}
