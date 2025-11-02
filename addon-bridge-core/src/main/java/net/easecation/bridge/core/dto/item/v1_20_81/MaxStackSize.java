package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/* The max stack size component determines how many of the item can be stacked together. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = MaxStackSize.Deserializer.class)
public record MaxStackSize(
    /* How many of the item that can be stacked. */
    @JsonProperty("value") Double value
) {
    @JsonCreator
    public static MaxStackSize of(Double value) {
        return new MaxStackSize(value);
    }
    
    // Custom deserializer to handle both number and object formats
    public static class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<MaxStackSize> {
        @Override
        public MaxStackSize deserialize(com.fasterxml.jackson.core.JsonParser p, 
                com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);
            
            // Handle direct number format: "minecraft:max_stack_size": 1
            if (node.isNumber()) {
                return MaxStackSize.of(node.asDouble());
            }
            
            // Handle object format: "minecraft:max_stack_size": {"value": 1}
            if (node.isObject() && node.has("value")) {
                return MaxStackSize.of(node.get("value").asDouble());
            }
            
            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize MaxStackSize: expected number or object with 'value' field");
        }
    }
}
