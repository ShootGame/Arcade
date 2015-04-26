/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.CycleScheduler;
import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class BukkitSchedulerManager implements SchedulerManager {
    public static final int[] SECONDS = new int[] {0, 1, 2, 3, 4, 5, 10, 15, 20, 30, 45, 60, 75, 90, 105, 120};
    
    private final BukkitScheduler scheduler;
    private final List<Integer> tasks;
    
    public BukkitSchedulerManager() {
        this.scheduler = Bukkit.getScheduler();
        this.tasks = new ArrayList<>();
    }
    
    @Override
    public void cancel() {
        for (int id : this.tasks) {
            this.scheduler.cancelTask(id);
        }
        this.tasks.clear();
    }
    
    @Override
    public void cancel(int id) {
        this.scheduler.cancelTask(id);
        this.tasks.remove(Integer.valueOf(id));
    }
    
    @Override
    public List<Integer> getIDs() {
        return this.tasks;
    }
    
    @Override
    public boolean isBeginRunning() {
        return this.isRunning(BeginScheduler.getID());
    }
    
    @Override
    public boolean isCycleRunning() {
        return this.isRunning(CycleScheduler.getID());
    }
    
    @Override
    public boolean isRunning(int id) {
        return this.scheduler.isCurrentlyRunning(id);
    }
    
    @Override
    public int run(Runnable scheduler) {
        Validate.notNull(scheduler, "scheduler can not be null");
        int id = this.scheduler.runTaskTimer(ArcadeBukkitPlugin.getPlugin(), scheduler, 1L, 20L).getTaskId();
        this.tasks.add(id);
        return id;
    }
    
    @Override
    public void runTask(int id) {
        this.tasks.add(id);
    }
    
    @Override
    public void runBegin(int seconds) {
        int id = this.run(new BeginScheduler(seconds));
        BeginScheduler.setID(id);
    }
    
    @Override
    public void runCycle(int seconds) {
        int id = this.run(new CycleScheduler(seconds));
        CycleScheduler.setID(id);
    }
    
    @Override
    public int[] seconds() {
        return SECONDS;
    }
}
