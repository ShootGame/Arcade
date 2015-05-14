/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.util.List;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.api.util.SimpleFactory;

/**
 *
 * @author Aleksander
 */
public class DestroyableFactory extends SimpleFactory {
    private final DestroyableModule module;
    
    public DestroyableFactory(DestroyableModule module) {
        this.module = module;
        this.register();
    }
    
    @Override
    public void build() {
        if (this.canBuild()) {
            List blocks = (List) this.get("blocks");
            int objective = (Integer) this.get("objective", 100);
            Region region = (Region) this.get("region");
            ScoreMode scoreMode = (ScoreMode) this.get("score-mode");
            
            Destroyable destroyable = null;
            if (blocks != null) {
                destroyable = this.loadBlocks(blocks);
            } else if (region != null) {
                destroyable = this.loadRegion(region);
            } else {
                
            }
            
            if (destroyable != null) {
                destroyable.appendSetting(Destroyable.Setting.MODE, scoreMode);
                destroyable.appendSetting(Destroyable.Setting.OBJECTIVE, objective);
                
                // TOOD register in the DestroyableModule class
            }
        }
    }
    
    private Destroyable loadBlocks(List blocks) {
        return null;
    }
    
    private Destroyable loadRegion(Region region) {
        return null;
    }
    
    private void register() {
        this.register("blocks", true, List.class);
        this.register("objective", true, Integer.class);
        this.register("region", true, Region.class);
        this.register("score-mode", false, ScoreMode.class);
    }
}
