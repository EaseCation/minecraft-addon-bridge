package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;

/* Describes the destructible by mining properties for this block. If set to true, the block will take the default number of seconds to destroy. If set to false, this block is indestructible by mining. If the component is omitted, the block will take the default number of seconds to destroy. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DestructibleByMining.Deserializer.class)
public sealed interface DestructibleByMining {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByMining_Variant0(
        @JsonValue Boolean value
    ) implements DestructibleByMining {
        @JsonCreator
        public static DestructibleByMining_Variant0 of(Boolean value) {
            return new DestructibleByMining_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByMining_Variant1(
        /* Sets the number of seconds it takes to destroy the block with base equipment. Greater numbers result in greater mining times. */
        @JsonProperty("seconds_to_destroy") @Nullable Double secondsToDestroy,
        /* Optional array of objects to describe item-specific block destroy speeds. */
        @JsonProperty("item_specific_speeds") @Nullable List<Object> itemSpecificSpeeds
    ) implements DestructibleByMining {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<DestructibleByMining> {
        @Override
        public DestructibleByMining deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Boolean value = node.asBoolean();
                    return DestructibleByMining_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, DestructibleByMining_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize DestructibleByMining: no matching variant found");
        }
    }
}
