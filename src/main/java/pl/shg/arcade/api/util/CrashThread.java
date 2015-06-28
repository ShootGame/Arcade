/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.commons.extended.BooleanUtils;

/**
 *
 * @author Aleksander
 */
public class CrashThread extends Thread {
    private final CrashHandler handler;
    
    public CrashThread(CrashHandler handler) {
        this.setName("Crash Thread - " + handler.hashCode());
        this.handler = handler;
    }
    
    @Override
    public void run() {
        try {
            File file = new File("errors" + File.separator + this.handler.getUnix().getTime() + ".txt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            List<String> crashBody = this.getCrashBody();
            
            for (String body : crashBody) {
                writer.write(body);
                writer.newLine();
            }
            
            writer.close();
            
            for (String body : crashBody) {
                System.out.println(body);
            }
            
            System.out.println("Crash report saved in " + file.getPath());
            System.out.println(" ----- END OF ARCADE SERVER CRASH REPORT ----- ");
            System.out.println();
            System.out.println("Stopping the server in " + (CrashHandler.SLEEP / 1000) + " seconds...");
        } catch (IOException ex) {
            Logger.getLogger(CrashThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<String> getCrashBody() {
        List<String> body = new ArrayList<>();
        
        body.add("Reason: " + this.handler.getCrash());
        body.add("Time: " + this.handler.getUnix().getTime());
        
        Throwable throwable = this.handler.getThrowable();
        if (throwable != null) {
            body.add("Throwable: " + throwable.getClass().toString());
            body.add("    From: " + throwable.toString());
            body.add("    Stack Trace:");
            
            for (StackTraceElement element : throwable.getStackTrace()) {
                String isNative = BooleanUtils.toStringYesNo(element.isNativeMethod());
                body.add("        at " + element.toString() + " Native: " + isNative);
            }
            
            body.add("    End of Stack Trace");
        }
        
        return body;
    }
}
