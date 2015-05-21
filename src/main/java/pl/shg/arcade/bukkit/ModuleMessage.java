/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit;

import pl.shg.arcade.api.chat.ActionMessageType;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public enum ModuleMessage {
    ALLY, ENEMY, OBSERVER, TEAM;
    
    public void send(Player player, String message, Object... values) {
        switch (this) {
            case ALLY:
                this.sendAlly(player, message, values);
                break;
            case ENEMY:
                this.sendEnemy(player, message, values);
                break;
            case OBSERVER:
                this.sendObserver(player, message, values);
                break;
            case TEAM:
                this.sendTeam(player, message, values);
                break;
        }
    }
    
    private void create(Player player, String message, Object... values) {
        player.sendActionMessage(ActionMessageType.INFO, String.format(message, values));
    }
    
    private void sendAlly(Player player, String message, Object... values) {
        this.create(player, message, values);
    }
    
    private void sendEnemy(Player player, String message, Object... values) {
        this.create(player, message, values);
    }
    
    private void sendObserver(Player player, String message, Object... values) {
        this.create(player, message, values);
    }
    
    private void sendTeam(Player player, String message, Object... values) {
        this.create(player, message, values);
    }
}
