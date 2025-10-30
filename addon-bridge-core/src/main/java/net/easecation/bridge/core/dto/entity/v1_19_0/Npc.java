package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* A component that applies a mob effect to entities that get within range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Npc(
    /* The data belonging to this npc. */
    @JsonProperty("npc_data") @Nullable NpcData npcData
) {
    
        /* The data belonging to this npc. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record NpcData(
            /* UNDOCUMENTED. */
            @JsonProperty("portrait_offsets") @Nullable PortraitOffsets portraitOffsets,
            /* UNDOCUMENTED. */
            @JsonProperty("picker_offsets") @Nullable PickerOffsets pickerOffsets,
            /* UNDOCUMENTED. */
            @JsonProperty("skin_list") @Nullable List<Object> skinList
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record PortraitOffsets(
                    /* UNDOCUMENTED. */
                    @JsonProperty("translate") @Nullable CahRangexyz translate,
                    /* UNDOCUMENTED. */
                    @JsonProperty("scale") @Nullable CahRangexyz scale
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record PickerOffsets(
                    /* UNDOCUMENTED. */
                    @JsonProperty("translate") @Nullable CahRangexyz translate,
                    /* UNDOCUMENTED. */
                    @JsonProperty("scale") @Nullable CahRangexyz scale
                ) {
                }
        }
}
