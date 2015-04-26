/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.human;

import java.util.UUID;
import pl.shg.arcade.api.command.Sender;
import pl.themolka.permissions.User;

/**
 *
 * @author Aleksander
 */
public interface MinecraftPlayer extends HumanEntity, Sender {
    void close();
    
    User getPermissions();
    
    UUID getUUID();
    
    void disconnect();
    
    void disconnect(String reason);
    
    void disconnect(String[] reason);
    
    void reloadPermissions();
    
    void respawn();
}
