package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;

/* The allow off hand component determines whether the item can be placed in the off hand slot of the inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = AllowOffHand.Deserializer.class)
public sealed interface AllowOffHand {
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record AllowOffHand_Variant0(
        @JsonValue Boolean value
    ) implements AllowOffHand {
        @JsonCreator
        public static AllowOffHand_Variant0 of(Boolean value) {
            return new AllowOffHand_Variant0(value);
        }
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record AllowOffHand_Variant1(
        /* Whether the item can be placed in the off hand slot */
        @JsonProperty("value") Boolean value
    ) implements AllowOffHand {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<AllowOffHand> {
        @Override
        public AllowOffHand deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Boolean value = node.asBoolean();
                    return AllowOffHand_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, AllowOffHand_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize AllowOffHand: no matching variant found");
        }
    }
}
