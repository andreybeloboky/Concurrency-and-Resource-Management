package org.example.faction;

import org.example.factory.Factory;
import org.example.factory.PartType;

import java.util.HashMap;
import java.util.Map;

public abstract class Faction implements Runnable {

    protected final String name;
    protected final Factory factory;
    protected final Map<PartType, Integer> inventory;
    protected int robotsBuilt = 0;
    private final int nights = 100;

    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        this.inventory = new HashMap<>();
        for (PartType type : PartType.values()) {
            inventory.put(type, 0);
        }
    }

    abstract void collectParts();

    @Override
    public void run() {
        for (int night = 1; night <= nights; night++) {
            collectParts();
            buildRobots();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void buildRobots() {
        while (canBuildRobot()) {
            inventory.put(PartType.HEAD, inventory.get(PartType.HEAD) - 1);
            inventory.put(PartType.TORSO, inventory.get(PartType.TORSO) - 1);
            inventory.put(PartType.FEET, inventory.get(PartType.FEET) - 2);
            inventory.put(PartType.HAND, inventory.get(PartType.HAND) - 2);
            robotsBuilt++;
        }
    }

    private boolean canBuildRobot() {
        return inventory.get(PartType.HEAD) >= 1
                && inventory.get(PartType.TORSO) >= 1
                && inventory.get(PartType.FEET) >= 2
                && inventory.get(PartType.HAND) >= 2;
    }

    public int getRobotsBuilt() {
        return robotsBuilt;
    }
}
