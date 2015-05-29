/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;

/**
 *
 * @author Aleksander
 */
public class ResourceFile {
    public static final String FILE = "/resource.json";
    private JSONResourceFile json;
    
    public JsonReader createReader(Reader reader) {
        Validate.notNull(reader, "reader can not be null");
        return new JsonReader(reader);
    }
    
    public Reader getResourceReader() {
        return this.getResourceReader(ResourceFile.FILE);
    }
    
    public Reader getResourceReader(String name) {
        Validate.notNull(name, "name can not be null");
        InputStream input = Arcade.class.getClassLoader().getResourceAsStream(name);
        return new InputStreamReader(input);
    }
    
    public JSONResourceFile getJSON() {
        return this.json;
    }
    
    public boolean isLoaded() {
        return this.json != null;
    }
    
    public void load(JsonReader reader) throws JsonSyntaxException {
        Validate.notNull(reader, "reader can not be null");
        this.json = new Gson().fromJson(reader, JSONResourceFile.class);
    }
    
    public static ResourceFile newInstance() {
        return new ResourceFile();
    }
}
