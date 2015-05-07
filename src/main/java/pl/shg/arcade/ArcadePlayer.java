/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.ChatMessage;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ArcadeClass;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;
import pl.shg.shootgame.api.server.Servers;
import pl.shg.shootgame.api.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public abstract class ArcadePlayer implements Player {
    private ArcadeClass arcadeClass;
    protected String display;
    private String prefixes;
    private boolean spying;
    private Team team;
    
    @Override
    public void connect(TargetServer server) {
        Validate.notNull(server, "server can not be null");
        Arcade.getProxy().connect(this, server);
    }
    
    @Override
    public void connect(String server) {
        Validate.notNull(server, "server can not be null");
        this.connect(Servers.getServer(server));
    }
    
    @Override
    public void disconnect() {
        this.disconnect((String) null);
    }
    
    @Override
    public void disconnect(String[] reason) {
        Validate.notNull(reason, "reason can not be null");
        StringBuilder builder = new StringBuilder();
        for (String r : reason) {
            builder.append(reason).append("\n");
        }
        this.disconnect(builder.toString());
    }
    
    @Override
    public ArcadeClass getArcadeClass() {
        if (this.arcadeClass != null) {
            return this.arcadeClass;
        } else {
            return Arcade.getMaps().getCurrentMap().getDefaultClass();
        }
    }
    
    @Override
    public void setArcadeClass(ArcadeClass arcadeClass) {
        this.arcadeClass = arcadeClass;
    }
    
    @Override
    public String getChatName() {
        return this.getChatPrefixes() + this.getDisplayName() + Color.RESET;
    }
    
    @Override
    public String getChatPrefixes() {
        if (this.prefixes != null) {
            return this.prefixes + Color.RESET;
        } else {
            return "";
        }
    }
    
    @Override
    public void setChatPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }
    
    @Override
    public String getDisplayName() {
        if (this.display == null) {
            this.display = this.getTeam().getColor() + this.getName();
        }
        return this.display;
    }
    
    @Override
    public void updateDisplayName() {
        this.display = this.getTeam().getColor() + this.getName();
        this.updateTag();
    }
    
    @Override
    public Team getTeam() {
        if (this.team != null) {
            return this.team;
        } else {
            return Arcade.getTeams().getObservers();
        }
    }
    
    @Override
    public boolean isConsole() {
        return false;
    }
    
    @Override
    public boolean isObserver() {
        return this.getTeam().getID().equals(ObserverTeamBuilder.getTeamID());
    }
    
    @Override
    public boolean isPremium() {
        return this.hasPermission("arcade.premium");
    }
    
    @Override
    public boolean isSpying() {
        return this.spying;
    }
    
    @Override
    public boolean isStaff() {
        return this.hasPermission("arcade.staff");
    }
    
    @Override
    public boolean isTeam(Team team) {
        if (team != null) {
            return this.getTeam().getID().equals(team.getID());
        } else {
            return this.getTeam() == null;
        }
    }
    
    @Override
    public void kick() {
        if (Arcade.getServer().bungeeCord()) {
            this.kickToLobby();
        } else {
            this.disconnect();
        }
    }
    
    @Override
    public boolean kickToLobby() {
        return this.kickToLobby(null);
    }
    
    @Override
    public void sendChatMessage(Sender sender, ChatMessage[] messages) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(messages, "messages can not be null");
        
        for (ChatMessage message : messages) {
            this.sendChatMessage(sender, message);
        }
    }
    
    @Override
    public void sendError(String error) {
        Validate.notNull(error, "error can not be null");
        this.sendMessage(Color.RED + error);
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
        this.sendMessage(Color.GREEN + success);
    }
    
    @Override
    public void setSpying(boolean spying) {
        this.spying = spying;
    }
    
    @Override
    public void setTeam(Team team) {
        this.team = team;
        this.updateDisplayName();
    }
}
