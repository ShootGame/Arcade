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
import pl.shg.arcade.api.util.TextFileReader;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ServersFile {
    private TextFileReader reader;
    
    public ServersFile(String fileName) {
        Validate.notNull(fileName, "fileName can not be null");
        this.loadFile(fileName);
    }
    
    public TextFileReader getReader() {
        return this.reader;
    }
    
    private void loadFile(String fileName) {
        Validate.notNull(fileName, "fileName can not be null");
        String repo = Arcade.getOptions().getServersRepo();
        if (!repo.endsWith("/")) {
            repo = repo + "/";
        }
        repo = repo + fileName;
        
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new URL(repo).openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServersFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServersFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.reader = new TextFileReader(lines);
    }
}
