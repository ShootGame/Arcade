/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.wool;

import pl.shg.arcade.api.location.Block;

/**
 *
 * @author Aleksander
 */
public class WoolMonument {
    private final Block block;
    private final WoolModule module;
    private final Wool wool;
    
    public WoolMonument(Block block, WoolModule module, Wool wool) {
        this.block = block;
        this.module = module;
        this.wool = wool;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public WoolModule getModule() {
        return this.module;
    }
    
    public Wool getWool() {
        return this.wool;
    }
}
