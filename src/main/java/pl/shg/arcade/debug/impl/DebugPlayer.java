/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import java.util.UUID;
import pl.shg.arcade.ArcadePlayer;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.tablist.TabList;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.BossBarMessage;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.arcade.debug.ArcadeDebug;
import pl.shg.commons.util.ClientSettings;
import pl.themolka.permissions.User;

/**
 *
 * @author Aleksander
 */
public class DebugPlayer extends ArcadePlayer {
    private final String name;
    private final UUID uuid;
    
    public DebugPlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
    
    @Override
    public boolean canSee(Player player) {
        return true;
    }
    
    @Override
    public Object getPlayer() {
        return this;
    }
    
    @Override
    public boolean kickToLobby(String reason) {
        this.disconnect(reason);
        return true;
    }
    
    @Override
    public void resetPlayerState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateTag() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public User getPermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public UUID getUUID() {
        return this.uuid;
    }
    
    @Override
    public void disconnect(String reason) {
        if (reason != null) {
            reason = ": " + reason;
        } else {
            reason = "";
        }
        
        ArcadeDebug.getServer().removePlayer(this.getUUID());
        ArcadeDebug.getConsole().sendMessage(this.getName() + " (UUID: " + this.getUUID() + ") disconnected" + reason + ".");
    }
    
    @Override
    public ClientSettings getClientSettings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void reloadPermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void respawn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Location getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isDead() {
        return true;
    }
    
    @Override
    public void teleport(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void teleport(Spawn spawn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void damage(double amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public double getHealth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setHealth(double health) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int getFeedLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setFeedLevel(int feed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void sendSubtitle(String subtitle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void sendTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setTabList(TabList tabList) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
    
    @Override
    public void sendActionMessage(ActionMessageType type, String message) {
        this.sendMessage("[Action-" + type.name() + "]: " + message);
    }
    
    @Override
    public void sendBossBarMessage(BossBarMessage message) {
        this.sendMessage("[Boss]: " + message.getSource());
    }
    
    @Override
    public void sendChatMessage(Sender sender, ChatMessage message) {
        this.sendMessage("[Chat]: " + message.getSource());
    }
    
    @Override
    public void sendMessage(String message) {
        ArcadeDebug.getConsole().sendMessage("[Do " + this.getName() + "]: " + message);
    }
    
    @Override
    public void updateVisibility() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
