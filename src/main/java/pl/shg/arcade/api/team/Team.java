/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.permissions.ArcadeTeam;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Team {
    private final TeamBuilder builder;
    private ChatChannel channel;
    private HashMap<KitType, List<Kit>> kits = new HashMap<>();
    
    public Team(TeamBuilder builder) {
        this.builder = builder;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            return ((Team) obj).getID().equals(this.getID());
        }
        return false;
    }
    
    protected TeamBuilder getBuilder() {
        return this.builder;
    }
    
    public ChatChannel getChat() {
        return this.channel;
    }
    
    public void setChat(ChatChannel channel) {
        Validate.notNull(channel, "channel can not be null");
        this.channel = channel;
    }
    
    public String getColor() {
        TeamColor color = this.getTeamColor();
        if (color != null) {
            switch (color) {
                case BLACK: return Color.BLACK;
                case BLUE: return Color.BLUE;
                case GOLD: return Color.GOLD;
                case GRAY: return Color.GRAY;
                case GREEN: return Color.GREEN;
                case PURPLE: return Color.LIGHT_PURPLE;
                case RED: return Color.RED;
                case WHITE: return Color.WHITE;
                case YELLOW: return Color.YELLOW;
            }
        }
        return Color.AQUA;
    }
    
    public String getDisplayName() {
        return this.getColor() + this.getBuilder().getName() + Color.RESET;
    }
    
    public boolean isFrendlyFire() {
        return this.getBuilder().isFrendlyFire();
    }
    
    public String getID() {
        return this.getBuilder().getID();
    }
    
    public HashMap<KitType, List<Kit>> getKits() {
        return this.kits;
    }
    
    public boolean hasKits() {
        return !this.kits.isEmpty();
    }
    
    public void setKits(HashMap<KitType, List<Kit>> kits) {
        if (kits == null) {
            kits = new HashMap<>();
        }
        this.kits = kits;
    }
    
    public int getMinimum() {
        if (this.getBuilder() instanceof PlayableTeamBuilder) {
            return ((PlayableTeamBuilder) this.getBuilder()).getMinimum();
        } else {
            return this.getSlots();
        }
    }
    
    public String getName() {
        return this.getBuilder().getName();
    }
    
    public ArcadeTeam getPermissions() {
        return this.getBuilder().getPermissions();
    }
    
    public List<Player> getPlayers() {
        List<Player> list = new ArrayList<>();
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (player.getTeam().equals(this)) {
                list.add(player);
            }
        }
        return list;
    }
    
    public int getSlots() {
        if (this.getBuilder() instanceof PlayableTeamBuilder) {
            return ((PlayableTeamBuilder) this.getBuilder()).getSlots();
        } else {
            return Integer.MAX_VALUE;
        }
    }
    
    public List<Spawn> getSpawns() {
        return this.getBuilder().getSpawns();
    }
    
    public TeamColor getTeamColor() {
        if (this.getBuilder() instanceof PlayableTeamBuilder) {
            return ((PlayableTeamBuilder) this.getBuilder()).getTeamColor();
        } else {
            return null;
        }
    }
}
