package org.example.simulation;

import org.example.factory.Factory;
import org.example.faction.WednesdayFaction;
import org.example.faction.WorldFaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Simulation {
    public static void main(String[] args) {
        Factory factory = new Factory();
        WorldFaction world = new WorldFaction(factory);
        WednesdayFaction wednesday = new WednesdayFaction(factory);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(factory);
        executorService.submit(world);
        executorService.submit(wednesday);
        executorService.shutdown();

        try {
            executorService.awaitTermination(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Factory inventory: " + factory.getInventory());
        System.out.println("World built robots: " + world.getRobotsBuilt());
        System.out.println("Wednesday built robots: " + wednesday.getRobotsBuilt());

        if (world.getRobotsBuilt() > wednesday.getRobotsBuilt()) {
            System.out.println("Winner: World!");
        } else if (wednesday.getRobotsBuilt() > world.getRobotsBuilt()) {
            System.out.println("Winner: Wednesday!");
        } else {
            System.out.println("Draw!");
        }
    }
}
