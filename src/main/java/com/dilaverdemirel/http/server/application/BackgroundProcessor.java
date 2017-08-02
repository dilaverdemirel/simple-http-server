package com.dilaverdemirel.http.server.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dilaverd on 8/2/2017.
 */
public class BackgroundProcessor {
    private static List<BackgroundProcess> backgroundProcesses = new ArrayList<>();
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void init(){
        Runnable task = ()-> {
            synchronized (backgroundProcesses) {
                backgroundProcesses.forEach(item -> item.process());
            }
        };

        executor.scheduleAtFixedRate(task,10,30,TimeUnit.SECONDS);
    }

    public static synchronized void addProcess(BackgroundProcess backgroundProcess){
        backgroundProcesses.add(backgroundProcess);
    }
}
