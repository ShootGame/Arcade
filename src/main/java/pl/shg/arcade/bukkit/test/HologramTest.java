/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.test;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.development.TestCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.hologram.Hologram;
import pl.shg.arcade.bukkit.hologram.Holograms;

/**
 *
 * @author Aleksander
 */
public class HologramTest extends TestCommand.Test {
    public HologramTest() {
        super("hologram", "[-k] <hologram>", false, "stwórz pseudo-hologram");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        Player player = (Player) sender;
        
        if (args[0].equals("-k")) {
            int i = 0;
            Iterator<Entity> it = new ArrayList<>(BukkitLocation.getWorld().getEntities()).iterator();
            
            while (it.hasNext()) {
                Entity entity = it.next();
                
                if (entity instanceof ArmorStand && this.matchLocation(entity.getLocation(), player.getLocation())) {
                    entity.remove();
                    i++;
                }
            }
            
            if (i == 0) {
                player.sendError("Brak hologramów w tej lokalizacji.");
            } else {
                player.sendSuccess("Usunieto " + i + " hologramów.");
            }
        } else {
            String value = Color.translate(StringUtils.join(args, ' ')) + Color.RESET;
            
            Hologram hologram = Holograms.create((player).getLocation());
            hologram.setHologram(value);
            hologram.update();
            
            player.sendSuccess("Stworzono hologram \"" + value + Color.GREEN + "\".");
        }
    }
    
    @Override
    public int minArguments() {
        return 1;
    }
    
    private boolean matchLocation(org.bukkit.Location entity, Location player) {
        return entity.getBlockX() == (int) player.getX() &&
                entity.getBlockY() == (int) player.getY() &&
                entity.getBlockZ() == (int) player.getZ();
    }
}
