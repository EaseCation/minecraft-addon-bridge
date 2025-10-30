package net.easecation.bridge.core.dto.spawn_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.List;

/* This component allows an entity to not spawn on a particular block. It includes a string or array of strings for the block they may not spawn on. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SpawnsOnBlockPreventedFilter.Deserializer.class)
public sealed interface SpawnsOnBlockPreventedFilter {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockPreventedFilter_Variant0(
        @JsonValue String value
    ) implements SpawnsOnBlockPreventedFilter {
        @JsonCreator
        public static SpawnsOnBlockPreventedFilter_Variant0 of(String value) {
            return new SpawnsOnBlockPreventedFilter_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockPreventedFilter_Variant1(
        List<String> value
    ) implements SpawnsOnBlockPreventedFilter {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<SpawnsOnBlockPreventedFilter> {
        @Override
        public SpawnsOnBlockPreventedFilter deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    String value = node.asText();
                    return SpawnsOnBlockPreventedFilter_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, SpawnsOnBlockPreventedFilter_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize SpawnsOnBlockPreventedFilter: no matching variant found");
        }
    }
}
