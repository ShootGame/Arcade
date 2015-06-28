/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.feature;

import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class FeatureBase {
    private static final HashMap<String, Feature> features = new HashMap<>();
    
    public static void addFeature(Feature feature) {
        Validate.notNull(feature);
        features.put(feature.getID().toLowerCase(), feature);
    }
    
    public static Feature getFeature(String id) {
        Validate.notNull(id);
        return features.get(id.toLowerCase());
    }
    
    public static Collection<Feature> getFeatures() {
        return features.values();
    }
}
