package org.example.faction;

import org.example.factory.Factory;
import org.example.factory.PartType;

public class WednesdayFaction extends Faction {

    public WednesdayFaction(Factory factory) {
        super("Wednesday", factory);
    }

    @Override
    void collectParts() {
        int collect = 0;
        for (PartType partType : PartType.values()) {
            while (collect <= 5 && factory.removePart(partType)) {
                inventory.put(partType, inventory.get(partType) + 1);
                collect++;
            }
        }
    }
}
