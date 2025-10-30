package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to be named (e.g. using a name tag). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Nameable(
    /* If true, this entity can be renamed with name tags. */
    @JsonProperty("allow_name_tag_renaming") @Nullable Boolean allowNameTagRenaming,
    /* If true, the name will always be shown. */
    @JsonProperty("always_show") @Nullable Boolean alwaysShow,
    /* Trigger to run when the entity gets named. */
    @JsonProperty("default_trigger") @Nullable Trigger defaultTrigger,
    /* Describes the special names for this entity and the events to call when the entity acquires those names. */
    @JsonProperty("name_actions") @Nullable Object nameActions
) {
}
