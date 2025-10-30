package net.easecation.bridge.core.dto.biome.v1_20_81;

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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ECoordinate.Deserializer.class)
public sealed interface ECoordinate {
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record ECoordinate_Variant0(
        /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
        @JsonValue MolangNumber value
    ) implements ECoordinate {
        @JsonCreator
        public static ECoordinate_Variant0 of(MolangNumber value) {
            return new ECoordinate_Variant0(value);
        }
    }
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record ECoordinate_Variant1(
        /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
        @JsonValue Double value
    ) implements ECoordinate {
        @JsonCreator
        public static ECoordinate_Variant1 of(Double value) {
            return new ECoordinate_Variant1(value);
        }
    }
    /* Distribution for the coordinate (evaluated each iteration). Mutually exclusive with Molang expression above. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record ECoordinate_Variant2(
        /* Type of distribution - uniform random, gaussian (centered in the range), or grid (either fixed-step or jittered). */
        @JsonProperty("distribution") String distribution,
        /* UNDOCUMENTED. */
        @JsonProperty("extent") List<MolangNumber> extent,
        /* When the distribution type is grid, defines the offset along this axis. */
        @JsonProperty("grid_offset") @Nullable Integer gridOffset,
        /* When the distribution type is grid, defines the distance between steps along this axis. */
        @JsonProperty("step_size") @Nullable Integer stepSize
    ) implements ECoordinate {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<ECoordinate> {
        @Override
        public ECoordinate deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws java.io.IOException {
            com.fasterxml.jackson.databind.JsonNode node = p.getCodec().readTree(p);

            // Check if it's a primitive value
            if (node.isBoolean() || node.isNumber() || node.isTextual()) {
                // Try to deserialize as value wrapper variants
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    // Fallback for complex types
                    com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                    nodeParser.nextToken();
                    MolangNumber value = ctxt.readValue(nodeParser, MolangNumber.class);
                                        return ECoordinate_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Double value = node.asDouble();
                    return ECoordinate_Variant1.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, ECoordinate_Variant2.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize ECoordinate: no matching variant found");
        }
    }
}
