/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command;

import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.BossBarMessage;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ConsoleSender implements Sender {
    @Override
    public String getName() {
        return getConsoleName();
    }
    
    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
    
    @Override
    public boolean isConsole() {
        return true;
    }
    
    @Override
    public void sendActionMessage(ActionMessageType type, String message) {
        Validate.notNull(type, "type can not be null");
        Validate.notNull(message, "message can not be null");
        
        this.sendMessage("[Action-" + type.toString() + "]" + message); // send to the console as the classic message
    }
    
    @Override
    public void sendBossBarMessage(BossBarMessage message) {
        throw new UnsupportedOperationException("Could not send " + message.getClass().getSimpleName() + " to the console");
    }
    
    @Override
    public void sendChatMessage(Sender sender, ChatMessage message) {
        Validate.notNull(message, "message can not be null");
        this.sendMessage(message.getSource());
    }
    
    @Override
    public void sendChatMessage(Sender sender, ChatMessage[] messages) {
        Validate.notNull(messages, "messages can not be null");
        for (ChatMessage message : messages) {
            this.sendChatMessage(sender, message);
        }
    }
    
    @Override
    public void sendError(String error) {
        Validate.notNull(error, "error can not be null");
        this.sendMessage(error);
    }
    
    @Override
    public void sendMessage(String message) {
        Validate.notNull(message, "message can not be null");
        Log.log(Level.INFO, message);
    }
    
    @Override
    public void sendMessage(String[] messages) {
        Validate.notNull(messages, "messages can not be null");
        for (String message : messages) {
            this.sendMessage(message);
        }
    }
    
    @Override
    public void sendSuccess(String success) {
        Validate.notNull(success, "success can not be null");
        this.sendMessage(success);
    }
    
    public static String getConsoleName() {
        return "Console";
    }
}
