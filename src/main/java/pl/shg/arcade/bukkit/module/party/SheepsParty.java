/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.team.TeamColor;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.module.perform.CTSPerform;

/**
 *
 * @author Aleksander
 */
public class SheepsParty extends Party {
    private final EventListener[] listeners = new EventListener[] {
        new CTSSpawnListener()
    };
    private final int maxTake = 3;
    private final HashMap<Sheep, GameSheep> sheeps = new HashMap<>();
    
    public SheepsParty() {
        super(new Date(2015, 5, 9), "sheeps", "Capture the sheep", Version.valueOf("1.0"));
    }
    
    @Override
    public void disable() {
        super.disable();
        Event.unregisterListener(this.listeners);
    }
    
    @Override
    public void enable() {
        super.enable();
        Event.registerListener(this.listeners);
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Zanies na swój spawn najwiecej owiec oraz ochron je od przeciwników."
        };
    }
    
    @Override
    public void loadParty(File file) {
        FileConfiguration config = Config.get(file);
        // TODO load
    }
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver()) {
            return;
        }
        
        if (e.getRightClicked() instanceof Sheep) {
            Sheep sheep = (Sheep) e.getRightClicked();
            GameSheep game = this.sheeps.getOrDefault(sheep, null);
            
            if (game != null) {
                if (game.getOwner() == null || !game.getOwner().equals(player.getTeam())) {
                    this.handleTake(player, e.getPlayer(), game);
                    
                    this.handleCapture(player, e.getPlayer()); // tests
                }
            } else {
                // sheep is not a part of the game (was respawned naturally?)
                sheep.setHealth(0.0);
            }
        }
    }
    
    private void handleCapture(Player player, org.bukkit.entity.Player bukkit) {
        Entity passenger = bukkit;
        int amount = 0;
        Team owner = null;
        
        while ((passenger = passenger.getPassenger()) != null) {
            amount++;
            
            GameSheep game = this.sheeps.getOrDefault((Sheep) passenger, null);
            if (game != null) {
                game.capture(player.getTeam());
                
                if (owner == null && game.getOwner() != null) {
                    owner = game.getOwner();
                }
            }
        }
        
        String message = player.getTeam().getDisplayName() + Color.DARK_GREEN + " zdobyla " + amount + " owce";
        if (owner != null) {
            message = message + " " + owner.getDisplayName();
        }
        
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            online.sendActionMessage(ActionMessageType.INFO, message + Color.DARK_GREEN + ".");
        }
        
        this.removePassengers(bukkit);
    }
    
    private void handleTake(Player player, org.bukkit.entity.Player bukkit, GameSheep sheep) {
        if (bukkit.getPassenger() == null) {
            bukkit.setPassenger(sheep.getSheep());
            return;
        }
        
        Entity passenger = bukkit;
        int amount = 0;
        
        while ((passenger = passenger.getPassenger()) != null) {
            if (amount > this.maxTake) {
                player.sendError("Nie mozesz zabrac wiecej niz " + this.maxTake + " owce.");
            } else {
                passenger.setPassenger(sheep.getSheep());
                amount++;
            }
        }
    }
    
    private void removePassengers(org.bukkit.entity.Player bukkit) {
        Entity passenger = bukkit;
        while ((passenger = passenger.getPassenger()) != null) {
            passenger.setPassenger(null);
        }
    }
    
    private class CTSSpawnListener implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return CTSPerform.CTSSpawnEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            Sheep sheep = ((CTSPerform.CTSSpawnEvent) event).getSheep();
            SheepsParty.this.sheeps.put(sheep, new GameSheep(sheep));
        }
    }
    
    private class GameSheep {
        private Team owner;
        private final Sheep sheep;
        
        public GameSheep(Sheep sheep) {
            this.sheep = sheep;
        }
        
        public void capture(Team newOwner) {
            this.owner = newOwner;
            this.updateColor();
        }
        
        public Team getOwner() {
            return this.owner;
        }
        
        public Sheep getSheep() {
            return this.sheep;
        }
        
        private void updateColor() {
            TeamColor color = this.getOwner().getTeamColor();
            org.bukkit.Color bukkitColor = org.bukkit.Color.fromRGB(color.getRGB()[0], color.getRGB()[1], color.getRGB()[2]);
            this.getSheep().setColor(DyeColor.getByColor(bukkitColor));
            
            this.getSheep().setCustomName(color.getColor() + "Owca " + this.getOwner().getDisplayName());
            this.getSheep().setCustomNameVisible(true);
        }
    }
}
