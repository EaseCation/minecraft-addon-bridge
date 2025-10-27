package net.easecation.bridge.adapter.easecation;

import net.easecation.bridge.core.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * EaseCation Nukkit 分支适配器（Demo）。
 * 目前仅输出日志，占位后续真实注册实现。
 */
public class EasecationRegistry implements AddonRegistry {
    private final Logger log;
    private static final Capabilities CAPS = new Capabilities(true);

    public EasecationRegistry(Logger log) { this.log = log; }

    @Override public void registerItems(List<ItemDef> items) { log.info("[EaseCation] registerItems size=" + items.size()); }
    @Override public void registerBlocks(List<BlockDef> blocks) { log.info("[EaseCation] registerBlocks size=" + blocks.size()); }
    @Override public void registerEntities(List<EntityDef> entities) { log.info("[EaseCation] registerEntities size=" + entities.size()); }
    @Override public void registerRecipes(List<RecipeDef> recipes) { log.info("[EaseCation] registerRecipes size=" + recipes.size()); }
    @Override public Capabilities capabilities() { return CAPS; }
}

