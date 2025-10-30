package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = EntityBb.Deserializer.class)
public sealed interface EntityBb {
    /* A minecraft item identifier. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityBb_Variant0(
        /* A minecraft item identifier. */
        @JsonValue String value
    ) implements EntityBb {
        @JsonCreator
        public static EntityBb_Variant0 of(String value) {
            return new EntityBb_Variant0(value);
        }
    }
    /* An object that describes an item. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityBb_Variant1(
        @JsonProperty("item") @Nullable Object item,
        /* [UNDOCUMENTED] A Molang expression ran against item or block to match. */
        @JsonProperty("tags") @Nullable String tags,
        /* [UNDOCUMENTED] A tag to lookup item or block by. */
        @JsonProperty("item_tag") @Nullable String itemTag
    ) implements EntityBb {
    }
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityBb_Variant2(
        @JsonProperty("item") @Nullable Object item
    ) implements EntityBb {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<EntityBb> {
        @Override
        public EntityBb deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    String value = node.asText();
                    return EntityBb_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, EntityBb_Variant1.class);
            } catch (Exception e) {
                // Try next variant
            }
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, EntityBb_Variant2.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize EntityBb: no matching variant found");
        }
    }
}
