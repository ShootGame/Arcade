/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.shg.arcade.ArcadeFactory;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.PluginProperties;
import pl.shg.arcade.api.command.def.VariableCommand;
import pl.shg.arcade.api.map.DynamicMapLoader;
import pl.shg.arcade.api.map.FileMapLoader;
import pl.shg.arcade.api.map.Loader;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.URLMapLoader;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.server.MiniGameServer;
import pl.shg.arcade.api.server.Role;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.BukkitPermissionsManager;
import pl.shg.arcade.bukkit.BukkitPlayer;
import pl.shg.arcade.bukkit.BukkitPlayerManagement;
import pl.shg.arcade.bukkit.BukkitServer;
import pl.shg.arcade.bukkit.BukkitWorldManager;
import pl.shg.arcade.bukkit.cy.CyConfiguration;
import pl.shg.arcade.bukkit.listeners.ArcadeEventListeners;
import pl.shg.arcade.bukkit.listeners.BukkitMenuListener;
import pl.shg.arcade.bukkit.listeners.GameableBlockListeners;
import pl.shg.arcade.bukkit.listeners.InventorySpyListeners;
import pl.shg.arcade.bukkit.listeners.ObserverKitListeners;
import pl.shg.arcade.bukkit.listeners.ObserverListeners;
import pl.shg.arcade.bukkit.listeners.PingDataListeners;
import pl.shg.arcade.bukkit.listeners.PlayerListeners;
import pl.shg.arcade.bukkit.listeners.PlayerMoveListener;
import pl.shg.arcade.bukkit.listeners.RegionListeners;
import pl.shg.arcade.bukkit.listeners.WorldListeners;
import pl.shg.commons.server.ArcadeTarget;
import pl.shg.commons.server.Servers;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public final class ArcadeBukkitPlugin extends JavaPlugin {
    public static final String PLUGIN_NAME = "Arcade";
    public static final String RUN_CMD = "-Xms1024M -Xmx1024M -jar sportbukkit-1.8-R0.1.jar";
    private static pl.shg.arcade.api.Plugin implementation;
    private static BukkitServer server;
    
    @Override
    public void onEnable() {
        PluginProperties properties = this.loadBasics();
        this.registerOnlinePlayers();
        this.loadBukkitListeners();
        this.loadMaps(properties, 2);
        
        Log.log(Level.INFO, "Wczytywanie modulow...");
        new ModuleLoader() {
            
            @Override
            public void register(Class<? extends Module> module) {
                Validate.notNull(module, "module can not be null");
                try {
                    Arcade.getModules().register(module);
                } catch (Exception ex) {
                    String id = module.getCanonicalName();
                    Log.noteAdmins("Napotkano blad w ladowaniu modulu " + id + " - patrz konsole", Log.NoteLevel.SEVERE);
                    Logger.getLogger(ModuleLoader.class.getName()).log(Level.SEVERE, "Napotkano blad podczas ladowania " + id, ex);
                }
            }
        }.init();
        for (Class<? extends Module> module : Arcade.getModules().getModules()) {
            Module object = Arcade.getModules().asObject(module);
            object.loadDependencies();
        }
        
        VariableCommand.setPerformer(new PerformerImpl());
        Arcade.getMaps().setWorlds(new BukkitWorldManager(this.getServer()));
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new EmptyWorldGenerator();
    }
    
    private void checkDependency(String name) {
        if (this.getServer().getPluginManager().getPlugin(name) != null) {
            this.getLogger().log(Level.INFO, "Polaczono z pluginem {0}.", name);
        } else {
            this.getLogger().log(Level.SEVERE, "Nie znaleziono wymaganego pluginu: {0} - wylaczanie...", name);
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    private PluginProperties loadBasics() {
        // Checkout all dependencies first
        for (String dependency : this.getDescription().getDepend()) {
            this.checkDependency(dependency);
        }
        
        // Create Bukkit implementation of the Arcade API server
        server = new BukkitServer(RUN_CMD, Bukkit.getServer());
        
        // Make a new small setup properties to the API
        PluginProperties properties = new PluginProperties();
        properties.setCommands(new BukkitCommandExecutor());
        properties.setConfiguration(new CyConfiguration());
        properties.setMapsDirectory(this.getServer().getWorldContainer().getPath());
        properties.setPermissions(new BukkitPermissionsManager());
        properties.setPlayerManagement(new BukkitPlayerManagement(this.getServer()));
        properties.setProxyServer(new BungeeCordProxy());
        properties.setSettingsDirectory("plugins" + File.pathSeparator + PLUGIN_NAME + File.pathSeparator);
        
        // Setup Arcade API
        implementation = ArcadeFactory.newInstance(getBukkit(), properties);
        Arcade.setPlugin(getAPI());
        
        // Call the server role enable method
        Role role = Arcade.getOptions().getRole();
        Log.log(Level.INFO, "Serwer jest uruchamiany jako \"" + role.getName() + "\".");
        role.getRole().onServerEnable();
        
        BukkitCommandExecutor.createHelpTopic();
        
        return properties;
    }
    
    @Deprecated
    private void loadBukkitCommands(String[] commands) {
        Validate.notNull(commands, "commands can not be null");
        BukkitCommandExecutor executor = new BukkitCommandExecutor();
        for (String cmd : commands) {
            PluginCommand pluginCmd = this.getServer().getPluginCommand(cmd.toLowerCase());
            if (pluginCmd != null) {
                pluginCmd.setExecutor(executor);
            } else {
                Log.log(Level.WARNING, "Nieudana proba rejestracji komendy " + cmd.toLowerCase() + " - komenda nie istnieje.");
            }
        }
    }
    
    private void loadBukkitListeners() {
        if (Arcade.getOptions().isBungeeCordEnabled()) {
            BungeeCordProxy.register(this.getServer());
        }
        
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new BukkitMenuListener(), this);
        manager.registerEvents(new GameableBlockListeners(), this);
        manager.registerEvents(new InventorySpyListeners(), this);
        manager.registerEvents(new ObserverKitListeners(), this);
        manager.registerEvents(new ObserverListeners(), this);
        manager.registerEvents(new PingDataListeners(), this);
        manager.registerEvents(new PlayerListeners(server), this);
        manager.registerEvents(new PlayerMoveListener(), this);
        manager.registerEvents(new RegionListeners(), this);
        manager.registerEvents(new WorldListeners(), this);
        
        ArcadeEventListeners arcadeListeners = new ArcadeEventListeners();
        arcadeListeners.registerListeners();
    }
    
    /**
     * Generating a list of maps and save. This will also invoke a
     * {@link #loadRotations()} method.
     * @param properties PluginProperites to get plugin's configuration
     * @param impl The type of implementation to use. The following values are
     * acceptable:<br />
     * <strong>0</strong> - load a list of specifited maps from the URL (must be
     * maps.txt file).<br />
     * <strong>1</strong> - load a list of specifited maps from flat (must be
     * maps.txt file).<br />
     * <strong>2</strong> - use dynamic loader, also method that loop are maps
     * in the directory and check it's validate of configuration.
     */
    private void loadMaps(PluginProperties properties, int impl) {
        Loader loader = null;
        switch (impl) {
            case 0:
                // getting a list of maps from GitHub
                loader = new URLMapLoader("https://raw.githubusercontent.com/ShootGame/Maps/master/maps.txt");
                break;
            case 1:
                // getting a list of maps from the maps directory
                loader = new FileMapLoader(new File(properties.getMapsDirectory() + File.separator + "maps.txt"));
                break;
            case 2:
                // gettings a list of maps from the directory of maps
                loader = new DynamicMapLoader();
                break;
        }
        
        if (loader == null) {
            return;
        }
        
        loader.loadMapList();
        Arcade.getMaps().setMaps(loader.getMaps());
        
        StringBuilder builder = new StringBuilder();
        for (Map map : Arcade.getMaps().getMaps()) {
            builder.append(map.getDisplayName()).append(", ");
        }
        Log.log(Level.INFO, "Zaladowano mapy: " + builder.toString());
        
        this.loadRotations();
    }
    
    private void loadRotations() {
        MiniGameServer.Online online = MiniGameServer.ONLINE;
        MiniGameServer.loadRotation(Servers.getConfiguration().getString("arcade." + online.getShoot().getID() + ".rotation"), online.getRotation());
        
        for (TargetServer arcade : Servers.getServers()) {
            if (arcade instanceof ArcadeTarget) {
                MiniGameServer miniGame = MiniGameServer.of((ArcadeTarget) arcade);
                MiniGameServer.loadRotation(miniGame.getCommons().getSetting("rotation"), miniGame.getRotation());
            }
        }
    }
    
    private void registerOnlinePlayers() {
        int reg = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            server.addPlayer(new BukkitPlayer(player));
            reg++;
        }
        
        if (reg > 0) {
            Log.log(Level.INFO, "Zarejestrowano " + reg + " graczy online.");
        } else {
            Log.log(Level.INFO, "Nie zarejestrowano zadnych graczy online, poniewaz ich brak.");
        }
    }
    
    public static pl.shg.arcade.api.Plugin getAPI() {
        return ArcadeBukkitPlugin.implementation;
    }
    
    public static Plugin getPlugin() {
        return ArcadeBukkitPlugin.getPlugin(ArcadeBukkitPlugin.class);
    }
    
    public static BukkitServer getBukkit() {
        return server;
    }
}
