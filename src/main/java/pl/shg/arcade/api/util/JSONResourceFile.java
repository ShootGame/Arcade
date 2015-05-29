/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.util;

import java.util.Objects;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class JSONResourceFile {
    private String name;
    private String version;
    private String commit;
    private String minecraft;
    private String encoding;
    
    @Override
    public boolean equals(Object obj) {
        Validate.notNull(obj, "obj can not be null");
        if (obj instanceof JSONResourceFile) {
            return ((JSONResourceFile) obj).hashCode() == this.hashCode();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(
                this.getName(),
                this.getVersion(),
                this.getCommit(),
                this.getMinecraft(),
                this.getEncoding()
        );
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getCommit() {
        return this.commit;
    }
    
    public String getMinecraft() {
        return this.minecraft;
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public void setCommit(String commit) {
        this.commit = commit;
    }
    
    public void setMinecraft(String minecraft) {
        this.minecraft = minecraft;
    }
    
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
