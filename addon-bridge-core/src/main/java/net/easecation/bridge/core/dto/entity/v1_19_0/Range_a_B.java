package net.easecation.bridge.core.dto.entity.v1_19_0;

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

/* A described range. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = Range_a_B.Deserializer.class)
public sealed interface Range_a_B {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_BVariant0(
        @JsonValue Double value
    ) implements Range_a_B {
        @JsonCreator
        public static Range_a_BVariant0 of(Double value) {
            return new Range_a_BVariant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_BVariant1(
        List<Double> value
    ) implements Range_a_B {
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_BVariant2(
        /* The minimum value of the range. */
        @JsonProperty("range_min") @Nullable Double rangeMin,
        /* The maximum value of the range. */
        @JsonProperty("range_max") @Nullable Double rangeMax
    ) implements Range_a_B {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<Range_a_B> {
        @Override
        public Range_a_B deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Double value = node.asDouble();
                    return Range_a_BVariant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, Range_a_BVariant1.class);
            } catch (Exception e) {
                // Try next variant
            }
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, Range_a_BVariant2.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize Range_a_B: no matching variant found");
        }
    }
}
