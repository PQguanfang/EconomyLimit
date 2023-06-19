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
        return limitMap.get(economyType);
    }

    public void UpdatePlayerLimit(String economyType, int value) {
        if (limitMap.containsKey(economyType)) {
            limitMap.replace(economyType, limitMap.get(player) + value);
        }
    }
}
