/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.text.Icons;

/**
 *
 * @author Aleksander
 */
public abstract class DestroyableObject implements Destroyable {
    public Icons getIcon() {
        switch (this.getStatus()) {
            case DESTROYED: return Icons.NO;
            case TOUCHED: return Icons.NO; // TODO
            case TOUCHED_SILENT: return Icons.YES;
            case UNTOUCHED: return Icons.YES;
        }
        return null;
    }
    
    public boolean isDestroyed() {
        return this.getStatus() == DestroyStatus.DESTROYED;
    }
}
