package net.easecation.bridge.core.dto.feature.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = BaRangeorint.Deserializer.class)
public sealed interface BaRangeorint {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record BaRangeorint_Variant0(
        @JsonValue Integer value
    ) implements BaRangeorint {
        @JsonCreator
        public static BaRangeorint_Variant0 of(Integer value) {
            return new BaRangeorint_Variant0(value);
        }
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<BaRangeorint> {
        @Override
        public BaRangeorint deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Integer value = node.asInt();
                    return BaRangeorint_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize BaRangeorint: no matching variant found");
        }
    }
}
