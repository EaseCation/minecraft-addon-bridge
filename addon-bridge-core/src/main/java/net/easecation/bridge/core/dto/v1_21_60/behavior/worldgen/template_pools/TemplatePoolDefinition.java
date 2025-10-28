package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.template_pools;

import com.fasterxml.jackson.annotation.*;

/* Used to pair block rules with Structure Templates and to randomly place Structure Templates using a weighted list. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TemplatePoolDefinition(
    @JsonProperty("format_version") FormatVersion formatVersion,
    @JsonProperty("minecraft:template_pool") TemplatePool minecraft_templatePool
) {
}
