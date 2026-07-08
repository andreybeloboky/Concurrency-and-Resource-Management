package org.example.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Factory implements Runnable {

    private final Map<PartType, Integer> inventory;
    private final Random random;
    private final int days = 100;
    private final ReentrantLock lock = new ReentrantLock();

    public Factory() {
        this.inventory = new HashMap<>();
        for (PartType type : PartType.values()) {
            inventory.put(type, 0);
        }
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 1; i <= days; i++) {
            produceParts();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void produceParts() {
        PartType part = getRandomPart();
        addPart(part);
    }

    public boolean removePart(PartType type) {
        lock.lock();
        try {
            int count = inventory.get(type);
            if (count > 0) {
                inventory.put(type, count - 1);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void addPart(PartType type) {
        lock.lock();
        try {
            inventory.put(type, inventory.get(type) + 1);
        } finally {
            lock.unlock();
        }
    }

    private PartType getRandomPart() {
        PartType[] values = PartType.values();
        return values[random.nextInt(values.length)];
    }

    public Map<PartType, Integer> getInventory() {
        return inventory;
    }
}
