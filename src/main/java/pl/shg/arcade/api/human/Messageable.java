/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.human;

import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.BossBarMessage;
import pl.shg.arcade.api.text.ChatMessage;

/**
 *
 * @author Aleksander
 */
public interface Messageable {
    void sendActionMessage(ActionMessageType type, String message);
    
    void sendBossBarMessage(BossBarMessage message);
    
    void sendChatMessage(Sender sender, ChatMessage message);
    
    void sendChatMessage(Sender sender, ChatMessage[] messages);
    
    void sendError(String error);
    
    void sendMessage(String message);
    
    void sendMessage(String[] messages);
    
    void sendSuccess(String success);
}
