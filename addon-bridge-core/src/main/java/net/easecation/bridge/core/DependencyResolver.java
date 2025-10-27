package net.easecation.bridge.core;

import java.util.ArrayList;
import java.util.List;

/** Minimal dependency resolver that keeps input order. */
public class DependencyResolver {
    public List<AddonPack> resolveOrder(List<AddonPack> packs) {
        return new ArrayList<>(packs);
    }
}

