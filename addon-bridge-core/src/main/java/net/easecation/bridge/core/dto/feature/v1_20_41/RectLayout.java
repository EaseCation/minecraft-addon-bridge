package net.easecation.bridge.core.dto.feature.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/*
 * {@code minecraft:scan_surface} scans the surface of a Chunk, calling place() on the surface of each block column.
 * Succeeds if: A Feature was successfully placed during the scan.
 * Fails if: No Feature was placed during the course of the scan.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RectLayout(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Named reference of feature to be placed. */
    @JsonProperty("scan_surface_feature") String scanSurfaceFeature
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
