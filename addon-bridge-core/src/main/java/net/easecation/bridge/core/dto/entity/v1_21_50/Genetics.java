package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the way a mob's genes and alleles are passed on to it's offspring, and how those traits manifest in the child. Compatible parent genes are crossed together, the alleles are handed down from the parents to the child, and any matching genetic variants fire off JSON events to modify the child and express the traits. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Genetics(
    /* Chance that an allele will be replaced with a random one instead of the parent's allele during birth. */
    @JsonProperty("mutation_rate") @Nullable Double mutationRate,
    /* The list of genes that this entity has and will cross with a partner during breeding. */
    @JsonProperty("genes") @Nullable List<Object> genes
) {
}
