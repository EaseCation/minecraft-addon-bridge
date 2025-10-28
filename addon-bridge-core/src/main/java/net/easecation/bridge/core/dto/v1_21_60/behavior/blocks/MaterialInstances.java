package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The material instances for a block. Maps face or material<i>instance names in a geometry file to an actual material instance. You can assign a material instance object to any of these faces: "up", "down", "north", "south", "east", "west", or "*". You can also give an instance the name of your choosing such as "my</i>instance", and then assign it to a face by doing "north":"my_instance". */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaterialInstances(
) {
}
