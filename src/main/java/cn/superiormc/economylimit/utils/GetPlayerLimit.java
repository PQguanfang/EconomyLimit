package cn.superiormc.economylimit.utils;

import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GetPlayerLimit {
    public static int GetVanillaExpLimit(Player player) {
        Set<String> groupNameSet = VanillaExp.GetVanillaExpLimits().keySet();
        List<Integer> result = new ArrayList<>();
        for (String groupName : groupNameSet) {
            if (player.hasPermission("economylimit." + groupName)) {
                result.add(VanillaExp.GetVanillaExpLimits().get(groupName));
            }
            else {
                if (VanillaExp.GetVanillaExpLimits().containsKey("default")) {
                    result.add(VanillaExp.GetVanillaExpLimits().get("default"));
                }
            }
        }
        if (result.size() == 0) {
            result.add(0);
        }
        return Collections.max(result);
    }
    public static int GetVanillaLevelsLimit(Player player) {
        Set<String> groupNameSet = VanillaLevels.GetVanillaLevelsLimits().keySet();
        List<Integer> result = new ArrayList<>();
        for (String groupName : groupNameSet) {
            if (player.hasPermission("economylimit." + groupName)) {
                result.add(VanillaExp.GetVanillaExpLimits().get(groupName));
            }
            else {
                if (VanillaExp.GetVanillaExpLimits().containsKey("default")) {
                    result.add(VanillaExp.GetVanillaExpLimits().get("default"));
                }
            }
        }
        if (result.size() == 0) {
            result.add(0);
        }
        return Collections.max(result);
    }
}
