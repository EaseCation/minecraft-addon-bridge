package net.easecation.bridge.adapter.mot.block;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Allocator for custom block IDs in MOT Nukkit.
 * MOT requires custom block IDs to be >= 10000 (LOWEST_CUSTOM_BLOCK_ID).
 */
public class BlockIdAllocator {

    /**
     * MOT's minimum custom block ID requirement.
     */
    private static final int LOWEST_CUSTOM_BLOCK_ID = 10000;

    /**
     * Counter for sequential ID allocation.
     */
    private static final AtomicInteger nextId = new AtomicInteger(LOWEST_CUSTOM_BLOCK_ID);

    /**
     * Map to track allocated IDs by identifier.
     * This ensures the same identifier always gets the same ID.
     */
    private static final Map<String, Integer> allocatedIds = new ConcurrentHashMap<>();

    /**
     * Allocate a block ID for the given identifier.
     * If the identifier has already been allocated an ID, return the existing ID.
     *
     * @param identifier The block identifier (e.g., "namespace:block_name")
     * @return The allocated block ID (>= 10000)
     */
    public static int allocate(String identifier) {
        return allocatedIds.computeIfAbsent(identifier, k -> nextId.getAndIncrement());
    }

    /**
     * Get the ID for a given identifier, if it has been allocated.
     *
     * @param identifier The block identifier
     * @return The allocated ID, or null if not allocated
     */
    public static Integer getId(String identifier) {
        return allocatedIds.get(identifier);
    }

    /**
     * Check if an identifier has been allocated an ID.
     *
     * @param identifier The block identifier
     * @return true if the identifier has been allocated an ID
     */
    public static boolean isAllocated(String identifier) {
        return allocatedIds.containsKey(identifier);
    }

    /**
     * Reset the allocator. This is mainly for testing purposes.
     * WARNING: Should not be used in production as it may cause ID conflicts.
     */
    public static void reset() {
        nextId.set(LOWEST_CUSTOM_BLOCK_ID);
        allocatedIds.clear();
    }

    /**
     * Get the next ID that will be allocated.
     *
     * @return The next available ID
     */
    public static int peekNextId() {
        return nextId.get();
    }
}
