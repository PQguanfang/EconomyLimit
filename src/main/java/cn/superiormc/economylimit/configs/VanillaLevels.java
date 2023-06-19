package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;

import java.util.HashMap;
import java.util.Map;

public class VanillaLevels {
    public static boolean GetVanillaLevelsEnabled() {
        return EconomyLimit.instance.getConfig().getBoolean("vanilla-levels.enabled", true);
    }
    public static String GetVanillaLevelsBypassPermission() {
        return EconomyLimit.instance.getConfig().getString("vanilla-levels.bypass-permission", null);
    }
    public static Map<String, Integer> GetVanillaLevelsLimits() {
        Map<String, Integer> limitsMap = new HashMap<>();
        if (EconomyLimit.instance.getConfig().getConfigurationSection("vanilla-levels.limits") == null) {
            return null;
        }
        for (String groupName : EconomyLimit.instance.getConfig().getConfigurationSection("vanilla-levels.limits").getKeys(false)) {
            limitsMap.put(groupName, EconomyLimit.instance.getConfig().getInt("vanilla-levels.limits." + groupName, 0));
        }
        return limitsMap;
    }
}
