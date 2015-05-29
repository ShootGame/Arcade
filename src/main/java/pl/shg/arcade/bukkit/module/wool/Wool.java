/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.wool;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class Wool extends Item {
    private Team captured;
    private final Color.Wool color;
    private WoolMonument monument;
    private final Team owner;
    
    public Wool(Color.Wool color, Team owner) {
        super(new Material(Color.Wool.WOOL.getID()));
        this.setType(new Material(Color.Wool.WOOL.getID(), color.getID()));
        this.color = color;
        this.owner = owner;
    }
    
    public void capture(Player player) {
        this.setCaptured(player.getTeam());
        this.getMonument().getModule().updateObjectives();
        
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            if (online.getTeam().equals(player.getTeam())) {
                // send to capturers team
                online.sendActionMessage(ActionMessageType.SUCCESS,
                        this.getCaptured().getDisplayName() + " zdobyla " + Color.DARK_AQUA +
                        this.getDisplayName() + Color.DARK_GREEN + " druzyny " +
                        this.getOwner().getDisplayName() + Color.DARK_GREEN + ".");
            } else if (online.getTeam().equals(this.getOwner())) {
                // send to owner of this wool
                online.sendActionMessage(ActionMessageType.ERROR, "Twój " +
                        Color.DARK_AQUA + this.getDisplayName() + Color.DARK_RED +
                        " zostal zdobyty przez " + this.getCaptured()+ Color.DARK_RED + ".");
            } else {
                // send to other players like other teams (if 3 or more is available) and observers
                online.sendActionMessage(ActionMessageType.INFO,
                        this.getCaptured().getDisplayName() + " zdobyla " + Color.DARK_AQUA +
                        this.getDisplayName() + Color.YELLOW + " druzyny " +
                        this.getOwner().getDisplayName() + Color.YELLOW + ".");
            }
        }
    }
    
    public Team getCaptured() {
        return this.captured;
    }
    
    public Color.Wool getColor() {
        return this.color;
    }
    
    public String getDisplayName() {
        return "jakas welna"; // TODO fix...
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    public WoolMonument getMonument() {
        return this.monument;
    }
    
    public boolean isCaptured() {
        return this.captured != null;
    }
    
    public void setCaptured(Team captured) {
        this.captured = captured;
    }
    
    public void setMonument(WoolMonument monument) {
        this.monument = monument;
    }
}
