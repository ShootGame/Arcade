/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import pl.shg.arcade.bukkit.module.*;
import pl.shg.arcade.bukkit.module.blitz.*;
import pl.shg.arcade.bukkit.module.boss.*;
import pl.shg.arcade.bukkit.module.deathmatch.*;
import pl.shg.arcade.bukkit.module.destroyable.*;
import pl.shg.arcade.bukkit.module.escape.*;
import pl.shg.arcade.bukkit.module.lib.*;
import pl.shg.arcade.bukkit.module.monument.*;
import pl.shg.arcade.bukkit.module.party.*;
import pl.shg.arcade.bukkit.module.wool.*;

/**
 *
 * @author Aleksander
 */
public abstract class ModuleLoader implements IRegistration {
    public void init() {
        this.features();
        this.games();
        this.libraries();
        Party.registerPartyModules(this);
    }
    
    private void features() {
        this.register(AntiGriefModule.class); // ess
        this.register(AutoJoinModule.class); // prefered for the blitz gamemodes
        this.register(AutoRespawnModule.class); // prefered for the blitz gamemodes
        this.register(BlockPearlTeleportModule.class);
        this.register(CancelDropModule.class);
        this.register(CancelPickupExpModule.class);
        this.register(CancelPickupModule.class);
        this.register(ChatModule.class); // ess
        this.register(DeathMessagesModule.class); // ess
        this.register(DelayedRespawnModule.class);
        this.register(DestroyableFireworksModule.class); // prefered for the 'destroyable' objective-module
        this.register(DestroyableModesModule.class); // prefered for the 'destroyable' objective-module
        this.register(DisableBedsModule.class); // ess
        this.register(DisableMobSpawningModule.class);
        this.register(FakePlayerDamageModule.class);
        this.register(FeatherFallingModule.class);
        this.register(GameRuleModule.class);
        this.register(JoinWhenRunningCancelModule.class); // prefered for the blitz gamemodes
        this.register(MatchTimerModule.class);
        this.register(NoDeathDropsModule.class);
        this.register(NoHungerModule.class);
        this.register(NoPvpModule.class);
        this.register(NoRainModule.class);
        this.register(NoThunderModule.class);
        this.register(PlayableAreaModule.class); // ess
        this.register(RageModule.class);
        this.register(StaticChestItemsModule.class);
    }
    
    private void games() {
        this.register(BlitzModule.class);
        this.register(BossModule.class);
        this.register(DeathMatchModule.class);
        this.register(DestroyableModule.class);
        this.register(EscapeModule.class);
        this.register(MonumentModule.class);
        this.register(PaintballModule.class);
        this.register(WoolModule.class);
    }
    
    private void libraries() {
        this.register(Points.class);
    }
}
