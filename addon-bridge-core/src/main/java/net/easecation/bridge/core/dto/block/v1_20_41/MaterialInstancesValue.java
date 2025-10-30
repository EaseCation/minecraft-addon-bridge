package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import javax.annotation.Nullable;

/* The material instance for a block. Maps face or material<i>instance names in a geometry file to an actual material instance. You can assign a material instance object to any of these faces: "up", "down", "north", "south", "east", "west", or "*". You can also give an instance the name of your choosing such as "my</i>instance", and then assign it to a face by doing "north":"my_instance". */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = MaterialInstancesValue.Deserializer.class)
public sealed interface MaterialInstancesValue {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MaterialInstancesValue_Variant0(
        @JsonValue String value
    ) implements MaterialInstancesValue {
        @JsonCreator
        public static MaterialInstancesValue_Variant0 of(String value) {
            return new MaterialInstancesValue_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record MaterialInstancesValue_Variant1(
        /* Should this material have ambient occlusion applied when lighting? If true, shadows will be created around and underneath the block. */
        @JsonProperty("ambient_occlusion") @Nullable Boolean ambientOcclusion,
        /* Should this material be dimmed by the direction it's facing? */
        @JsonProperty("face_dimming") @Nullable Boolean faceDimming,
        /* The render method to use. Must be one of these options: opaque - Used for a regular block texture without an alpha layer. Does not allow for transparency or translucency. double<i>sided - Used for completely disabling backface culling. blend - Used for a block like stained glass. Allows for transparency and translucency (slightly transparent textures). alpha</i>test - Used for a block like the vanilla (unstained) glass. Does not allow for translucency, only fully opaque or fully transparent textures. Also disables backface culling. */
        @JsonProperty("render_method") @Nullable String renderMethod,
        /* Texture name for the material. */
        @JsonProperty("texture") @Nullable String texture
    ) implements MaterialInstancesValue {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<MaterialInstancesValue> {
        @Override
        public MaterialInstancesValue deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    String value = node.asText();
                    return MaterialInstancesValue_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, MaterialInstancesValue_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize MaterialInstancesValue: no matching variant found");
        }
    }
}
