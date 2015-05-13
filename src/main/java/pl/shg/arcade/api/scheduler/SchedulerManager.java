/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.scheduler;

import java.util.List;

/**
 *
 * @author Aleksander
 */
public interface SchedulerManager {
    void cancel();
    
    void cancel(int id);
    
    List<Integer> getIDs();
    
    boolean isBeginRunning();
    
    boolean isCycleRunning();
    
    boolean isRunning(int id);
    
    int runSync(Runnable scheduler);
    
    int runSync(Runnable scheduler, long update);
    
    void runTask(int id);
    
    void runBegin(int seconds);
    
    void runCycle(int seconds);
    
    int[] seconds();
}
