/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import pl.shg.arcade.bukkit.module.*;
import pl.shg.arcade.bukkit.module.blitz.BlitzModule;
import pl.shg.arcade.bukkit.module.deathmatch.DeathMatchModule;
import pl.shg.arcade.bukkit.module.escape.EscapeModule;
import pl.shg.arcade.bukkit.module.lib.Points;
import pl.shg.arcade.bukkit.module.monument.MonumentModule;
import pl.shg.arcade.bukkit.module.paintball.PaintballModule;
import pl.shg.arcade.bukkit.module.wool.WoolModule;

/**
 *
 * @author Aleksander
 */
public abstract class ModuleLoader implements IRegistration {
    public void init() {
        this.features();
        this.libraries();
        this.objectives();
    }
    
    private void features() {
        this.register(AntiGriefModule.class); // ess
        this.register(AutoJoinModule.class); // prefered for the blitz gamemodes
        this.register(AutoRespawnModule.class); // prefered for the capture the sheeps
        this.register(BlockPearlTeleportModule.class);
        this.register(ChatModule.class); // ess
        this.register(DeathMessagesModule.class); // ess
        this.register(DelayedRespawnModule.class);
        this.register(DisableBedsModule.class); // ess
        this.register(DisableMobSpawningModule.class);
        this.register(FeatherFallingModule.class);
        this.register(GameRuleModule.class);
        this.register(JoinWhenRunningCancelModule.class);
        this.register(LivesModule.class);
        this.register(NoDeathDropsModule.class);
        this.register(NoHungerModule.class);
        this.register(NoPvpModule.class);
        this.register(NoRainModule.class);
        this.register(NoThunderModule.class);
        this.register(PlayableAreaModule.class); // ess
        this.register(RageModule.class);
        this.register(StaticChestItemsModule.class);
    }
    
    private void libraries() {
        this.register(Points.class);
    }
    
    private void objectives() {
        this.register(BlitzModule.class);
        this.register(DeathMatchModule.class);
        this.register(EscapeModule.class);
        this.register(MonumentModule.class);
        this.register(PaintballModule.class);
        this.register(WoolModule.class);
    }
}
