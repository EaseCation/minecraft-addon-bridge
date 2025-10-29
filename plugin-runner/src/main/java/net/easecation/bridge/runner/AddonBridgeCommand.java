package net.easecation.bridge.runner;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.easecation.bridge.core.BridgeConfig;

/**
 * AddonBridge 管理命令
 * 提供配置重载、状态查看等功能
 */
public class AddonBridgeCommand extends PluginCommand<BridgeBootstrap> {

    public AddonBridgeCommand(BridgeBootstrap owner) {
        super("addonbridge", owner);
        setAliases(new String[]{"adb", "bridge"});
        setPermission("addonbridge.admin");
        setDescription("AddonBridge management command");
        setUsage("/addonbridge <reload|status|help>");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload" -> {
                return handleReload(sender);
            }
            case "status" -> {
                return handleStatus(sender);
            }
            default -> {
                sender.sendMessage("§cUnknown subcommand: " + subCommand);
                sendHelp(sender);
                return false;
            }
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§e=== AddonBridge Commands ===");
        sender.sendMessage("§a/addonbridge reload §7- Reload configuration");
        sender.sendMessage("§a/addonbridge status §7- Show plugin status");
        sender.sendMessage("§a/addonbridge help §7- Show this help message");
    }

    private boolean handleReload(CommandSender sender) {
        try {
            sender.sendMessage("§eReloading AddonBridge configuration...");

            // Reload Nukkit config
            this.getPlugin().reloadConfig();

            // The config will be reloaded on next server restart
            // For now, just inform the user
            sender.sendMessage("§aConfiguration file reloaded!");
            sender.sendMessage("§eNote: Some changes may require a server restart to take effect.");
            sender.sendMessage("§eResource pack pushing settings will be applied on next player join.");

            return true;
        } catch (Exception e) {
            sender.sendMessage("§cFailed to reload configuration: " + e.getMessage());
            this.getPlugin().getLogger().error("Failed to reload config", e);
            return false;
        }
    }

    private boolean handleStatus(CommandSender sender) {
        try {
            BridgeConfig config = this.getPlugin().getBridgeConfig();

            sender.sendMessage("§e=== AddonBridge Status ===");
            sender.sendMessage("§7Plugin Version: §f" + this.getPlugin().getDescription().getVersion());
            sender.sendMessage("");
            sender.sendMessage("§7Resource Pack Settings:");
            sender.sendMessage("  §7Push resource packs: " + formatBoolean(config.isPushResourcePacks()));
            sender.sendMessage("  §7Push behavior packs: " + formatBoolean(config.isPushBehaviorPacks()));
            sender.sendMessage("  §7Force accept: " + formatBoolean(config.isForceAccept()));
            sender.sendMessage("  §7Push priority: §f" + config.getPushPriority());
            sender.sendMessage("  §7Base URL: §f" + (config.getBaseUrl().isEmpty() ? "§7(local files)" : config.getBaseUrl()));
            sender.sendMessage("");
            sender.sendMessage("§7Plugin Behavior:");
            sender.sendMessage("  §7Auto scan: " + formatBoolean(config.isAutoScan()));
            sender.sendMessage("  §7Verbose logging: " + formatBoolean(config.isVerboseLogging()));

            return true;
        } catch (Exception e) {
            sender.sendMessage("§cFailed to get status: " + e.getMessage());
            this.getPlugin().getLogger().error("Failed to get status", e);
            return false;
        }
    }

    private String formatBoolean(boolean value) {
        return value ? "§a✓ Enabled" : "§c✗ Disabled";
    }
}
