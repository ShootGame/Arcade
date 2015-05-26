/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.party;

import java.io.File;

/**
 *
 * @author Aleksander
 */
public interface Partyable {
    String[] getPartyTutorial();
    
    void loadParty(File file);
}
