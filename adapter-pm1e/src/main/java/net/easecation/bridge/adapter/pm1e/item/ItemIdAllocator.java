package net.easecation.bridge.adapter.pm1e.item;

import java.util.HashSet;
import java.util.Set;

/**
 * Allocates unique Nukkit IDs for custom items.
 * PM1E requires IDs in the range [5000, 65534].
 */
public class ItemIdAllocator {

    private static final int LOWEST_CUSTOM_ITEM_ID = 5000;
    private static final int HIGHEST_CUSTOM_ITEM_ID = 65534;

    private int nextId = LOWEST_CUSTOM_ITEM_ID;
    private final Set<Integer> allocatedIds = new HashSet<>();

    public int allocate(String identifier) {
        while (nextId <= HIGHEST_CUSTOM_ITEM_ID) {
            int candidateId = nextId++;
            if (!allocatedIds.contains(candidateId)) {
                allocatedIds.add(candidateId);
                return candidateId;
            }
        }

        throw new RuntimeException("No more custom item IDs available for: " + identifier);
    }

    public boolean markAllocated(int id) {
        if (id < LOWEST_CUSTOM_ITEM_ID || id > HIGHEST_CUSTOM_ITEM_ID) {
            throw new IllegalArgumentException("Item ID out of range [" + LOWEST_CUSTOM_ITEM_ID + ", " + HIGHEST_CUSTOM_ITEM_ID + "]: " + id);
        }

        return allocatedIds.add(id);
    }

    public boolean isAllocated(int id) {
        return allocatedIds.contains(id);
    }

    public int getAllocatedCount() {
        return allocatedIds.size();
    }

    public void reset() {
        nextId = LOWEST_CUSTOM_ITEM_ID;
        allocatedIds.clear();
    }
}
