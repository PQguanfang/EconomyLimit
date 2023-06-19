package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;

import java.util.HashMap;
import java.util.Map;

public class VanillaExp {
    public static boolean GetVanillaExpEnabled() {
        return EconomyLimit.instance.getConfig().getBoolean("vanilla-exp.enabled", true);
    }
    public static String GetVanillaExpBypassPermission() {
        return EconomyLimit.instance.getConfig().getString("vanilla-exp.bypass-permission", null);
    }
    public static Map<String, Integer> GetVanillaExpLimits() {
        Map<String, Integer> limitsMap = new HashMap<>();
        if (EconomyLimit.instance.getConfig().getConfigurationSection("vanilla-exp.limits") == null) {
            return null;
        }
        for (String groupName : EconomyLimit.instance.getConfig().getConfigurationSection("vanilla-exp.limits").getKeys(false)) {
            limitsMap.put(groupName, EconomyLimit.instance.getConfig().getInt("vanilla-exp.limits." + groupName, 0));
        }
        return limitsMap;
    }
}
