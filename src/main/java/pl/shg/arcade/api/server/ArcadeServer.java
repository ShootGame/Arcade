/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.NotLoadedMap;
import pl.shg.arcade.api.server.status.ServerStatus;
import pl.shg.arcade.api.util.TextFileReader;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeServer {
    private Broadcaster broadcaster;
    private final boolean current;
    private final String name;
    private boolean protect;
    private Rotation rotation;
    private ServerStatus status;
    
    public ArcadeServer(String name, boolean current) throws ArcadeServerRepoNotFoundException {
        Validate.notNull(name, "name can not be null");
        this.current = current;
        this.name = name;
        this.loadServer();
        
        if (current) {
            Arcade.getServers().setCurrentServer(this);
        }
    }
    
    public Broadcaster getBroadcaster() {
        return this.broadcaster;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Rotation getRotation() {
        return this.rotation;
    }
    
    public ServerStatus getStatus() {
        return this.status;
    }
    
    public boolean hasStatus() {
        return this.status != null;
    }
    
    public boolean isProtected() {
        return this.protect;
    }
    
    private void loadFile(TextFileReader file) {
        Validate.notNull(file, "file can not be null");
        try {
            if (this.current) {
                this.broadcaster = new BroadcasterLoader(file.getSetting("broadcaster", "broadcaster.txt"), this).getBroadcaster();
                this.protect = Boolean.valueOf(file.getSetting("protected", "false"));
            }
            this.rotation = new RotationLoader(file.getSetting("rotation", "rotation.txt"), this).getRotation();
        } catch (Throwable ex) {
            Log.noteAdmins("Konfiguracja serwera " + this.getName() + " nie istnieje!",
                    Log.NoteLevel.SEVERE);
            ex.printStackTrace();
        }
    }
    
    private void loadServer() throws ArcadeServerRepoNotFoundException {
        String repo = Arcade.getOptions().getServersRepo();
        if (!repo.endsWith("/")) {
            repo = repo + "/";
        }
        repo = repo + this.getName() + "/settings.txt";
        
        try {
            URL url = new URL(repo);
            Scanner scanner = new Scanner(url.openStream());
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            this.loadFile(new TextFileReader(lines));
        } catch (MalformedURLException ex) {
            Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            throw new ArcadeServerRepoNotFoundException(ex);
        }
    }
    
    public static class BroadcasterLoader {
        public static final String DEFAULT_NAME = "`8[`9Broadcaster`8]";
        public static final String DEFAULT_FORMAT = DEFAULT_NAME + "`7 $message";
        
        private BroadcasterSettings settings;
        private final List<String> broadcasts;
        private final String name;
        private final ArcadeServer owner;
        
        public BroadcasterLoader(String name, ArcadeServer owner) {
            Validate.notNull(name, "name can not be null");
            Validate.notNull(owner, "owner can not be null");
            this.broadcasts = new ArrayList<>();
            this.name = name;
            this.owner = owner;
            this.loadBroadcasts();
        }
        
        public Broadcaster getBroadcaster() {
            Broadcaster broadcaster = new Broadcaster(this.settings);
            broadcaster.setMessages(this.broadcasts);
            return broadcaster;
        }
        
        private void loadBroadcasts() {
            String repo = Arcade.getOptions().getServersRepo();
            if (!repo.endsWith("/")) {
                repo = repo + "/";
            }
            repo = repo + this.owner.getName() + "/" + this.name;
            
            try {
                URL url = new URL(repo);
                Scanner scanner = new Scanner(url.openStream());
                List<String> lines = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
                
                TextFileReader reader = new TextFileReader(lines);
                this.settings = new BroadcasterSettings();
                this.settings.setFormat(reader.getSetting("format", BroadcasterLoader.DEFAULT_FORMAT));
                try {
                    this.settings.setTime(Integer.valueOf(reader.getSetting("time", "120")));
                } catch (NumberFormatException ex) {
                    Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (TextFileReader.Line line : reader.getLines()) {
                    String value = line.getValue();
                    if (!value.contains("=")) {
                        this.broadcasts.add(value);
                    }
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static class RotationLoader {
        private final List<String> maps;
        private final String name;
        private final ArcadeServer owner;
        
        public RotationLoader(String name, ArcadeServer owner) {
            Validate.notNull(name, "name can not be null");
            Validate.notNull(owner, "owner can not be null");
            this.maps = new ArrayList<>();
            this.name = name;
            this.owner = owner;
            this.loadRotation();
        }
        
        public Rotation getRotation() {
            Rotation rotation = new Rotation(this.name, this.owner);
            for (String m : this.maps) {
                Map map = Arcade.getMaps().getMapExact(m);
                if (map == null) {
                    map = new NotLoadedMap(m);
                }
                
                rotation.addMap(map);
            }
            return rotation;
        }
        
        private void loadRotation() {
            String repo = Arcade.getOptions().getServersRepo();
            if (!repo.endsWith("/")) {
                repo = repo + "/";
            }
            repo = repo + this.owner.getName() + "/" + this.name;
            
            try {
                URL url = new URL(repo);
                Scanner scanner = new Scanner(url.openStream());
                List<String> lines = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
                
                TextFileReader reader = new TextFileReader(lines);
                for (TextFileReader.Line line : reader.getLines()) {
                    this.maps.add(line.getValue());
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ArcadeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
