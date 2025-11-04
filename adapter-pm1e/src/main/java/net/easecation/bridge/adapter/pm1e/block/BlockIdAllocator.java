package net.easecation.bridge.adapter.pm1e.block;

import java.util.HashSet;
import java.util.Set;

/**
 * Allocates unique Nukkit IDs for custom blocks.
 */
public class BlockIdAllocator {

    private static final int DEFAULT_LOWEST_CUSTOM_BLOCK_ID = 10000;

    private int nextId;
    private final Set<Integer> allocatedIds = new HashSet<>();

    public BlockIdAllocator(int lowestCustomBlockId) {
        this.nextId = lowestCustomBlockId;
    }

    public BlockIdAllocator() {
        this(DEFAULT_LOWEST_CUSTOM_BLOCK_ID);
    }

    public int allocate(String identifier) {
        while (true) {
            int candidateId = nextId++;
            if (!allocatedIds.contains(candidateId)) {
                allocatedIds.add(candidateId);
                return candidateId;
            }
        }
    }

    public boolean markAllocated(int id) {
        return allocatedIds.add(id);
    }

    public boolean isAllocated(int id) {
        return allocatedIds.contains(id);
    }

    public int getAllocatedCount() {
        return allocatedIds.size();
    }

    public void reset() {
        allocatedIds.clear();
    }
}
