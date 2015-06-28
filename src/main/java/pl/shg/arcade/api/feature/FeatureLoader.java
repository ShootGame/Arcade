/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.feature;

import org.jdom2.Element;

/**
 *
 * @author Aleksander
 */
public class FeatureLoader {
    public static Feature load(String name, String location) {
        try {
            Class clazz = Class.forName(location);
            if (clazz != null) {
                return new Feature(name, clazz);
            }
        } catch (ClassNotFoundException ex) {
            
        }
        
        return null;
    }
    
    public static void loadFeatures(Element xml) {
        for (Element element : xml.getChildren("feature")) {
            FeatureBase.addFeature(load(
                    element.getAttributeValue("id"),
                    element.getText()
            ));
        }
    }
}
