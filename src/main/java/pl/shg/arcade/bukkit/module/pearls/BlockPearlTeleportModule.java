/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.pearls;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.api.region.RegionManager;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class BlockPearlTeleportModule extends Module implements BListener {
    private RegionManager manager;
    private final List<String> regions = new ArrayList<>();
    
    public BlockPearlTeleportModule() {
        super(new Date(2015, 4, 19), "block-pearl-teleport", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł umożliwia blokowanie teleportowanie " +
                "się perłą kresu do określone regiony. Przykładowym regionem " +
                "może być spawn drużyny przeciwnej.");
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.manager = Arcade.getMaps().getCurrentMap().getRegions();
        FileConfiguration config = Config.get(file);
        
        for (String region : Config.getValueList(config, this, "disallowed-regions")) {
            this.regions.add(region.toLowerCase());
        }
        
        if (!this.regions.isEmpty()) {
            Listeners.register(this);
        }
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        
        Location location = new Location(e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
        List<Region> regionsHere = this.manager.getRegions(location);
        
        if (!regionsHere.isEmpty()) {
            for (Region region : regionsHere) {
                if (this.regions.contains(region.getPath().toLowerCase())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Color.RED + "Nie mozesz sie teleportowac w to miejsce perla.");
                    break;
                }
            }
        }
    }
}
