package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The minecraft:connection trait provides block states for connecting to adjacent blocks.
 * Used for fence-like blocks, walls, glass panes, etc.
 *
 * When enabled, creates four boolean block states:
 * - north_connection
 * - south_connection
 * - east_connection
 * - west_connection
 */
public record Connection(
    @JsonProperty("enabled_states") @Nullable List<String> enabledStates
) {
}
