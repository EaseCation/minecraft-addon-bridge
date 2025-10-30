package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the way an entity can get into the {@code love} state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Breedable(
    /* If true, entities can breed while sitting. */
    @JsonProperty("allow_sitting") @Nullable Boolean allowSitting,
    /* If true, the entities will blend their attributes in the offspring after they breed. */
    @JsonProperty("blend_attributes") @Nullable Boolean blendAttributes,
    /* Time in seconds before the Entity can breed again. */
    @JsonProperty("breed_cooldown") @Nullable Double breedCooldown,
    /* The list of items that can be used to get the entity into the {@code love} state. */
    @JsonProperty("breed_items") @Nullable Object breedItems,
    /* The list of entity definitions that this entity can breed with. */
    @JsonProperty("breeds_with") @Nullable Object breedsWith,
    /* If true, the entity will become pregnant instead of spawning a baby. */
    @JsonProperty("causes_pregnancy") @Nullable Boolean causesPregnancy,
    /* Determines how likely the baby of parents with the same variant will deny that variant and take a random variant within the given range instead. */
    @JsonProperty("deny_parents_variant") @Nullable DenyParentsVariant denyParentsVariant,
    /* The list of nearby block requirements to get the entity into the {@code love} state. */
    @JsonProperty("environment_requirements") @Nullable Object environmentRequirements,
    /* Chance that up to 16 babies will spawn between 0.0 and 1.0, where 1.0 is 100%. */
    @JsonProperty("extra_baby_chance") @Nullable Double extraBabyChance,
    /* The filters to run when attempting to fall in love. */
    @JsonProperty("love_filters") @Nullable Filters loveFilters,
    /* Determines how likely the babies are to NOT inherit one of their parent's variances. Values are between 0.0 and 1.0, with a higher number meaning more likely to mutate. */
    @JsonProperty("mutation_factor") @Nullable MutationFactor mutationFactor,
    /* Strategy used for mutating variants and extra variants for offspring. Current valid alternatives are 'random' and 'none'. */
    @JsonProperty("mutation_strategy") @Nullable String mutationStrategy,
    /* [EXPERIMENTAL] List of attributes that should benefit from parent centric attribute blending. For example, horses blend their health, movement, and jump_strength in their offspring. */
    @JsonProperty("parent_centric_attribute_blending") @Nullable List<Object> parentCentricAttributeBlending,
    /* Range used to determine random extra variant. */
    @JsonProperty("random_extra_variant_mutation_interval") @Nullable VectorOf2Items randomExtraVariantMutationInterval,
    /* Range used to determine random variant. */
    @JsonProperty("random_variant_mutation_interval") @Nullable VectorOf2Items randomVariantMutationInterval,
    /* If true, the babies will be automatically tamed if its parents are. */
    @JsonProperty("inherit_tamed") @Nullable Boolean inheritTamed,
    /* If true, the entity needs to be at full health before it can breed. */
    @JsonProperty("require_full_health") @Nullable Boolean requireFullHealth,
    /* If true, the entities need to be tamed first before they can breed. */
    @JsonProperty("require_tame") @Nullable Boolean requireTame,
    /* The feed item used will transform to this item upon successful interaction. Format: itemName:auxValue */
    @JsonProperty("transform_to_item") @Nullable String transformToItem
) {
    
        /* Determines how likely the baby of parents with the same variant will deny that variant and take a random variant within the given range instead. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DenyParentsVariant(
            /* The percentage chance of denying the parents` variant. */
            @JsonProperty("chance") @Nullable Double chance,
            /* The inclusive maximum of the variant range. */
            @JsonProperty("max_variant") @Nullable Integer maxVariant,
            /* The inclusive minimum of the variant range. */
            @JsonProperty("min_variant") @Nullable Integer minVariant
        ) {
        }
    
        /* Determines how likely the babies are to NOT inherit one of their parent's variances. Values are between 0.0 and 1.0, with a higher number meaning more likely to mutate. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MutationFactor(
            /* The percentage chance of a mutation on the entity's color. */
            @JsonProperty("color") @Nullable Double color,
            /* The percentage chance of a mutation on the entity's extra variant type. */
            @JsonProperty("extra_variant") @Nullable Double extraVariant,
            /* The percentage chance of a mutation on the entity's variant type. */
            @JsonProperty("variant") @Nullable Double variant
        ) {
        }
}
