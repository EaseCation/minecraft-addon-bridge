package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import javax.annotation.Nullable;

/* Chance of something happening represented either as a fraction or a percentage. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ChanceInformation.Deserializer.class)
public sealed interface ChanceInformation {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record ChanceInformation_Variant0(
        /* UNDOCUMENTED. */
        @JsonProperty("numerator") @Nullable Double numerator,
        /* UNDOCUMENTED. */
        @JsonProperty("denominator") @Nullable Double denominator
    ) implements ChanceInformation {
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record ChanceInformation_Variant1(
        @JsonValue Double value
    ) implements ChanceInformation {
        @JsonCreator
        public static ChanceInformation_Variant1 of(Double value) {
            return new ChanceInformation_Variant1(value);
        }
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<ChanceInformation> {
        @Override
        public ChanceInformation deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    return ctxt.readValue(nodeParser, ChanceInformation_Variant1.class);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, ChanceInformation_Variant0.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize ChanceInformation: no matching variant found");
        }
    }
}
