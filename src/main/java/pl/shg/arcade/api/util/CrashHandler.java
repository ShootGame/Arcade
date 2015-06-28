/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.util;

import pl.shg.commons.time.UnixTime;

/**
 *
 * @author Aleksander
 */
public class CrashHandler {
    public static final long SLEEP = 15 * 1000L;
    
    private String crash;
    private Throwable throwable;
    private UnixTime unix;
    
    public CrashHandler() {
        this(null, null);
    }
    
    public CrashHandler(String crash) {
        this(crash, null);
    }
    
    public CrashHandler(Throwable throwable) {
        this(null, throwable);
    }
    
    public CrashHandler(String crash, Throwable throwable) {
        this.crash = crash;
        this.throwable = throwable;
        this.unix = UnixTime.now();
    }
    
    public void crash() {
        try {
            System.out.println(" ----- ARCADE SERVER CRASH REPORT ----- ");
            System.out.println("Saving crash file, may take a while...");
            
            new CrashThread(this).start();
            Thread.sleep(SLEEP);
        } catch (Throwable ex) {
            
        }
        
        System.exit(0);
    }
    
    public String getCrash() {
        return this.crash;
    }
    
    public UnixTime getUnix() {
        return this.unix;
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    public void setCrash(String crash) {
        this.crash = crash;
    }
}
