/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.docs.NotUsableDeprecation;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class AntiGriefModule extends Module {
    private final ProtectionElement[] protect = new ProtectionElement[] {
            new CraftingProtect()
    };
    
    public AntiGriefModule() {
        super(new Date(2015, 4, 18), "anti-grief", Version.valueOf("1.0"));
        this.getDocs().setDeprecation(new NotUsableDeprecation(NotUsableDeprecation.Reason.AUTO_LOAD));
        this.getDocs().setDescription("Ten moduł jest ochroną przez grieferami " +
                "w drużynie. Moduł ten na czas obecny oferuje tylko jeden sposób " +
                "zabiepieczenia workbencha. Moduł ten nie jest wymagany oraz " +
                "nie jest rdzeniem pluginu Arcade, twórca mapy może go wykorzystać " +
                "dobrowolnie.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this.protect);
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this.protect);
    }
    
    private class CraftingProtect extends ProtectionElement {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent e) {
            if (!e.isCancelled() && e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) {
                Block block = e.getClickedBlock();
                if (block != null && block.getType() == Material.WORKBENCH) {
                    e.setCancelled(true);
                    e.getPlayer().openWorkbench(null, true);
                }
            }
        }
    }
    
    private static class ProtectionElement implements BListener {}
}
