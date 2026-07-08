package org.example.faction;

import org.example.factory.Factory;
import org.example.factory.PartType;

import java.util.Random;

public class WorldFaction extends Faction {

    private final Random random = new Random();

    public WorldFaction(Factory factory) {
        super("World", factory);
    }

    @Override
    void collectParts() {
        int collect = 0;
        while (collect <= 5){
            PartType type = PartType.values()[random.nextInt(PartType.values().length)];
            if (factory.removePart(type)) {
                inventory.put(type, inventory.get(type) + 1);
                collect++;
            } else {
                break;
            }
        }
    }
}
