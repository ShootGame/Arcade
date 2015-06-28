/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.util.SimpleFactory;

/**
 *
 * @author Aleksander
 */
public class DestroyableFactory extends SimpleFactory<Destroyable> {
    private final DestroyableModule module;
    
    public DestroyableFactory(DestroyableModule module) {
        this.module = module;
        this.register();
    }
    
    @Override
    public Destroyable build() {
        if (this.canBuild()) {
            List<BlocksDestroyable> blocks = (List) this.get("blocks");
            List<RegionsDestroyable> regions = (List) this.get("regions");
            
            List<DestroyableObject> destroyables = new ArrayList<>();
            if (blocks != null) {
                destroyables.addAll(blocks);
            }
            if (regions != null) {
                destroyables.addAll(regions);
            }
            
            this.module.registerDestroyables(destroyables);
        }
        
        return null;
    }
    
    private void register() {
        this.register("blocks", false, List.class);
        this.register("regions", false, List.class);
    }
}
