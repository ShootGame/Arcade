/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.filter;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.location.Location;

/**
 *
 * @author Aleksander
 */
public class ApplyFilter extends Filter {
    private final List<Material> accept;
    private final List<Material> deny;
    
    public ApplyFilter(List<Material> accept, List<Material> deny) {
        if (accept == null) {
            this.accept = new ArrayList<>();
        } else {
            this.accept = accept;
        }
        
        if (deny == null) {
            this.deny = new ArrayList<>();
        } else {
            this.deny = deny;
        }
    }
    
    @Override
    public boolean canBuild(Location location, Material material) {
        if (material == null) {
            return false;
        }
        
        if (!this.getAccept().isEmpty()) {
            return this.getAccept().contains(material);
        } else if (!this.getDeny().isEmpty()) {
            return !this.getDeny().contains(material);
        }
        
        return false;
    }
    
    public List<Material> getAccept() {
        return this.accept;
    }
    
    public List<Material> getDeny() {
        return this.deny;
    }
}
