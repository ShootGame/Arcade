/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.test;

import org.bukkit.EntityEffect;
import org.bukkit.entity.EnderDragon;
import org.bukkit.scheduler.BukkitRunnable;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.development.TestCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class DragonDeathTest extends TestCommand.Test {
    public DragonDeathTest() {
        super("dragon-death", null, false, "animacja smierci smoka");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        Location location = ((Player) sender).getLocation();
        this.handleTest(BukkitLocation.valueOf(location));
    }
    
    private void handleTest(org.bukkit.Location location) {
        final EnderDragon e = location.getWorld().spawn(location, EnderDragon.class);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                e.playEffect(EntityEffect.DEATH);
            }
        }.runTaskLater(ArcadeBukkitPlugin.getPlugin(), 20L);
    }
}
