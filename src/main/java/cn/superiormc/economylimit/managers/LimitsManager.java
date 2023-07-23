package cn.superiormc.economylimit.managers;

import org.bukkit.entity.Player;

import java.util.Map;

public class LimitsManager {
    private Player player;
    private Map<String, Integer> limitMap;

    public LimitsManager(Player player, Map<String, Integer> limitMap) {
        this.player = player;
        this.limitMap = limitMap;
    }

    public int GetPlayerLimit(String economyType) {
        if (limitMap.get(economyType) == null) {
            return 0;
        }
        return limitMap.get(economyType);
    }

    public void UpdatePlayerLimit(String economyType, int value) {
        if (limitMap.containsKey(economyType)) {
            limitMap.replace(economyType, limitMap.get(economyType) + value);
        }
    }
}
