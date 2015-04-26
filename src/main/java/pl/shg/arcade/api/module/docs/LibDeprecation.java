/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module.docs;

/**
 *
 * @author Aleksander
 */
public class LibDeprecation implements IDeprectation {
    @Override
    public String getHTML() {
        return "Ten moduł jest biblioteką - plugin Arcade załaduje go automatycznie, gdy będzie taka potrzeba.";
    }
}
