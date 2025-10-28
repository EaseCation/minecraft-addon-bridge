package net.easecation.bridge.runner;

import cn.nukkit.utils.Logger;
import net.easecation.bridge.core.AddonRegistry;
import net.easecation.bridge.core.BridgeLogger;
import net.easecation.bridge.core.NoopRegistry;

public final class AdapterFactory {
    private AdapterFactory() {}

    public static AddonRegistry detectRegistryOrNoop(cn.nukkit.Server server, Logger nukkitLogger) {
        BridgeLogger log = new NukkitLoggerAdapter(nukkitLogger);
        // 仅根据服务端品牌信息进行判定；支持环境变量强制覆盖
        if (server != null) {
            String force = safe(System.getenv("BRIDGE_FORCE_ADAPTER"));
            if (!force.isEmpty()) {
                switch (force.toLowerCase()) {
                    case "easecation":
                        log.info("Force adapter via BRIDGE_FORCE_ADAPTER=easecation");
                        return new net.easecation.bridge.adapter.easecation.EasecationRegistry(log);
                    case "pnx":
                    case "powernukkitx":
                        log.info("Force adapter via BRIDGE_FORCE_ADAPTER=pnx");
                        return new net.easecation.bridge.adapter.pnx.PnxRegistry(log);
                    case "mot":
                        log.info("Force adapter via BRIDGE_FORCE_ADAPTER=mot");
                        return new net.easecation.bridge.adapter.mot.MotRegistry(log);
                    case "pm1e":
                        log.info("Force adapter via BRIDGE_FORCE_ADAPTER=pm1e");
                        return new net.easecation.bridge.adapter.pm1e.Pm1eRegistry(log);
                    case "cloudburst":
                    case "nukkit":
                        log.info("Force adapter via BRIDGE_FORCE_ADAPTER=cloudburst");
                        return new net.easecation.bridge.adapter.cloudburst.CloudburstRegistry(log);
                }
            }

            String name = safe(server.getName());
            String ver = safe(server.getVersion());
            String api = safe(tryGetApiVersion(server));
            String codename = safe(tryGetCodename(server));
            String brand = (name + " " + ver + " " + api + " " + codename).toLowerCase();
            log.info("Server brand: name='" + name + "' version='" + ver + "' api='" + api + "' codename='" + codename + "'");

            // 优先通过 codename 识别 EaseCation（例如 "EC大法好" 或包含 "EaseCation"）
            if (!codename.isEmpty()) {
                String codeLower = codename.toLowerCase();
                if (codeLower.contains("easecation") || codename.contains("EC大法好")) {
                    log.info("Detected EaseCation Nukkit via server codename");
                    return new net.easecation.bridge.adapter.easecation.EasecationRegistry(log);
                }
            }

            if (brand.contains("petterim1") || brand.contains("petteri") || brand.contains("pm1e")) {
                log.info("Detected PM1E via server brand");
                return new net.easecation.bridge.adapter.pm1e.Pm1eRegistry(log);
            }
            if (brand.contains("powernukkitx")) {
                log.info("Detected PowerNukkitX via server brand");
                return new net.easecation.bridge.adapter.pnx.PnxRegistry(log);
            }
            if (brand.contains(" mot") || brand.contains("memoriesoftime")) {
                log.info("Detected Nukkit-MOT via server brand");
                return new net.easecation.bridge.adapter.mot.MotRegistry(log);
            }
            if (brand.contains("easecation")) {
                log.info("Detected EaseCation Nukkit via server brand");
                return new net.easecation.bridge.adapter.easecation.EasecationRegistry(log);
            }
            if (brand.contains("cloudburst") || brand.contains("nukkit")) {
                log.info("Detected Cloudburst/Nukkit via server brand");
                return new net.easecation.bridge.adapter.cloudburst.CloudburstRegistry(log);
            }

            log.warning("Unknown server brand, fallback to NoopRegistry");
            return new NoopRegistry();
        }

        log.warning("Server instance is null, fallback to NoopRegistry");
        return new NoopRegistry();
    }

    public static AddonRegistry detectRegistryOrNoop(Logger nukkitLogger) {
        return detectRegistryOrNoop(null, nukkitLogger);
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private static String tryGetApiVersion(cn.nukkit.Server server) {
        try {
            var m = server.getClass().getMethod("getApiVersion");
            Object v = m.invoke(server);
            return v != null ? v.toString() : "";
        } catch (Throwable ignored) {
            return "";
        }
    }

    private static String tryGetCodename(cn.nukkit.Server server) {
        // 优先尝试实例方法
        try {
            var m = server.getClass().getMethod("getCodename");
            Object v = m.invoke(server);
            if (v != null) return v.toString();
        } catch (Throwable ignored) { }
        // 其次尝试静态 Server.getInstance().getCodename()
        try {
            Class<?> cls = Class.forName("cn.nukkit.Server");
            var getInst = cls.getMethod("getInstance");
            Object inst = getInst.invoke(null);
            if (inst != null) {
                var getCode = cls.getMethod("getCodename");
                Object v = getCode.invoke(inst);
                if (v != null) return v.toString();
            }
        } catch (Throwable ignored) { }
        return "";
    }
}
