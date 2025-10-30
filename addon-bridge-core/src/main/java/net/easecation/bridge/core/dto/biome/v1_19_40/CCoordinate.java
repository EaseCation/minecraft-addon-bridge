package net.easecation.bridge.core.dto.biome.v1_19_40;

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
@JsonDeserialize(using = CCoordinate.Deserializer.class)
public sealed interface CCoordinate {
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record CCoordinate_Variant0(
        /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
        @JsonValue MolangNumber value
    ) implements CCoordinate {
        @JsonCreator
        public static CCoordinate_Variant0 of(MolangNumber value) {
            return new CCoordinate_Variant0(value);
        }
    }
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record CCoordinate_Variant1(
        /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
        @JsonValue Double value
    ) implements CCoordinate {
        @JsonCreator
        public static CCoordinate_Variant1 of(Double value) {
            return new CCoordinate_Variant1(value);
        }
    }
    /* Distribution for the coordinate (evaluated each iteration). Mutually exclusive with Molang expression above. */
    @JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class) @JsonIgnoreProperties(ignoreUnknown = true) 
    record CCoordinate_Variant2(
        /* Type of distribution - uniform random, gaussian (centered in the range), or grid (either fixed-step or jittered). */
        @JsonProperty("distribution") String distribution,
        /* UNDOCUMENTED. */
        @JsonProperty("extent") List<MolangNumber> extent,
        /* When the distribution type is grid, defines the offset along this axis. */
        @JsonProperty("grid_offset") @Nullable Integer gridOffset,
        /* When the distribution type is grid, defines the distance between steps along this axis. */
        @JsonProperty("step_size") @Nullable Integer stepSize
    ) implements CCoordinate {
    }

    /* Custom deserializer to handle oneOf with primitive values */
    class Deserializer extends com.fasterxml.jackson.databind.JsonDeserializer<CCoordinate> {
        @Override
        public CCoordinate deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt)
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
                                        return CCoordinate_Variant0.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
                try {
                    // For value wrapper, directly read the primitive value and call factory method
                    Double value = node.asDouble();
                    return CCoordinate_Variant1.of(value);
                } catch (Exception e) {
                    // Try next variant
                }
            }

            // It's an object, try each variant
            try {
                com.fasterxml.jackson.core.JsonParser nodeParser = node.traverse(p.getCodec());
                nodeParser.nextToken();
                return ctxt.readValue(nodeParser, CCoordinate_Variant2.class);
            } catch (Exception e) {
                // Try next variant
            }

            throw new com.fasterxml.jackson.databind.JsonMappingException(p,
                "Cannot deserialize CCoordinate: no matching variant found");
        }
    }
}
