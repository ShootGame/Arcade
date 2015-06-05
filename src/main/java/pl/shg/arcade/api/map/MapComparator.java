/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import java.util.Comparator;

/**
 *
 * @author Aleksander
 */
public class MapComparator implements Comparator<Map> {
    @Override
    public int compare(Map o1, Map o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
