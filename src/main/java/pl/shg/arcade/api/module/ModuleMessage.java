/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.ActionMessageType;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public enum ModuleMessage {
    ALLY, ENEMY, OBSERVER, TEAM;
    
    private Player player;
    private Sound sound;
    private Type type;
    
    public ModuleMessage player(Player player) {
        Validate.notNull(player, "player can not be null");
        this.player = player;
        return this;
    }
    
    public void send(String message, Object... values) {
        Validate.notNull(message, "message can not be null");
        if (values == null) {
            values = new Object[0];
        }
        
        switch (this) {
            case ALLY:
                this.sendAlly(message, values);
                break;
            case ENEMY:
                this.sendEnemy(message, values);
                break;
            case OBSERVER:
                this.sendObserver(message, values);
                break;
            case TEAM:
                this.sendTeam(message, values);
                break;
        }
    }
    
    public ModuleMessage sound(Sound sound) {
        this.sound = sound;
        return this;
    }
    
    public ModuleMessage type(Type type) {
        this.type = type;
        return this;
    }
    
    private void create(String message, Object... values) {
        this.player.sendActionMessage(this.getMessageType(), String.format(message, values));
        
        if (this.sound != null) {
            Arcade.getPlayerManagement().playSound(this.player, this.sound);
        }
    }
    
    private ActionMessageType getMessageType() {
        switch (this.type) {
            case E_OBJECTIVE:
            case E_PIECE:
                return ActionMessageType.ERROR;
            case S_OBJECTIVE:
            case S_PIECE:
                return ActionMessageType.SUCCESS;
        }
        return ActionMessageType.INFO;
    }
    
    private void sendAlly(String message, Object... values) {
        this.create(message, values);
    }
    
    private void sendEnemy(String message, Object... values) {
        this.create(message, values);
    }
    
    private void sendObserver(String message, Object... values) {
        this.create(message, values);
    }
    
    private void sendTeam(String message, Object... values) {
        this.create(message, values);
    }
    
    public static enum Type {
        E_OBJECTIVE,
        E_PIECE,
        
        S_OBJECTIVE,
        S_PIECE,
        ;
    }
}
