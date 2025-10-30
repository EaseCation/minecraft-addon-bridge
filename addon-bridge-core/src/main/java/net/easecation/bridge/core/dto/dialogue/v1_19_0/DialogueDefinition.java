package net.easecation.bridge.core.dto.dialogue.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Specifies the dialogue scenes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DialogueDefinition(
    @JsonProperty("format_version") String formatVersion,
    /* Specifies the dialogue of an npc. */
    @JsonProperty("minecraft:npc_dialogue") Minecraft_npcDialogue minecraft_npcDialogue
) {
    
        /* Specifies the dialogue of an npc. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Minecraft_npcDialogue(
            /* The different scenes. */
            @JsonProperty("scenes") @Nullable List<Object> scenes
        ) {
        }
}
