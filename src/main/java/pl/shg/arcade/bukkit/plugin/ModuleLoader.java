/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import pl.shg.arcade.bukkit.module.area.PlayableAreaModule;
import pl.shg.arcade.bukkit.module.beds.DisableBedsModule;
import pl.shg.arcade.bukkit.module.blitz.BlitzModule;
import pl.shg.arcade.bukkit.module.boss.BossModule;
import pl.shg.arcade.bukkit.module.chests.StaticChestItemsModule;
import pl.shg.arcade.bukkit.module.damage.FakePlayerDamageModule;
import pl.shg.arcade.bukkit.module.damage.FeatherFallingModule;
import pl.shg.arcade.bukkit.module.deathmatch.DeathMatchModule;
import pl.shg.arcade.bukkit.module.deathmatch.PaintballModule;
import pl.shg.arcade.bukkit.module.deathmessages.DeathMessagesModule;
import pl.shg.arcade.bukkit.module.destroyable.DestroyableFireworksModule;
import pl.shg.arcade.bukkit.module.destroyable.DestroyableModesModule;
import pl.shg.arcade.bukkit.module.destroyable.DestroyableModule;
import pl.shg.arcade.bukkit.module.drops.CancelDropModule;
import pl.shg.arcade.bukkit.module.drops.NoDeathDropsModule;
import pl.shg.arcade.bukkit.module.escape.EscapeModule;
import pl.shg.arcade.bukkit.module.exp.CancelPickupExpModule;
import pl.shg.arcade.bukkit.module.gamerules.GameRuleModule;
import pl.shg.arcade.bukkit.module.grief.AntiGriefModule;
import pl.shg.arcade.bukkit.module.hunger.NoHungerModule;
import pl.shg.arcade.bukkit.module.join.AutoJoinModule;
import pl.shg.arcade.bukkit.module.join.JoinWhenRunningCancelModule;
import pl.shg.arcade.bukkit.module.lib.Points;
import pl.shg.arcade.bukkit.module.mobs.DisableMobSpawningModule;
import pl.shg.arcade.bukkit.module.monument.MonumentModule;
import pl.shg.arcade.bukkit.module.party.Party;
import pl.shg.arcade.bukkit.module.pearls.BlockPearlTeleportModule;
import pl.shg.arcade.bukkit.module.perform.DelayedPerformModule;
import pl.shg.arcade.bukkit.module.pickup.CancelPickupModule;
import pl.shg.arcade.bukkit.module.pvp.NoPvpModule;
import pl.shg.arcade.bukkit.module.rage.RageModule;
import pl.shg.arcade.bukkit.module.respawn.AutoRespawnModule;
import pl.shg.arcade.bukkit.module.respawn.DelayedRespawnModule;
import pl.shg.arcade.bukkit.module.rules.RulesModule;
import pl.shg.arcade.bukkit.module.timer.MatchTimerModule;
import pl.shg.arcade.bukkit.module.weather.NoRainModule;
import pl.shg.arcade.bukkit.module.weather.NoThunderModule;
import pl.shg.arcade.bukkit.module.wool.WoolModule;

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
        this.register(DeathMessagesModule.class); // ess
        this.register(DelayedPerformModule.class);
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
        this.register(RulesModule.class);
        this.register(RageModule.class);
        this.register(StaticChestItemsModule.class);
    }
    
    private void games() {
        this.register(BlitzModule.class);
        this.register(BossModule.class); // TODO
        this.register(DeathMatchModule.class);
        this.register(DestroyableModule.class); // TODO
        this.register(EscapeModule.class); // TODO
        this.register(MonumentModule.class); // deprecated
        this.register(PaintballModule.class);
        this.register(WoolModule.class); // TODO
    }
    
    private void libraries() {
        this.register(Points.class);
    }
}
