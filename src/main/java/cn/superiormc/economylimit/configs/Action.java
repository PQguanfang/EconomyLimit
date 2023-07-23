package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;

import java.util.List;

public class Action {

    public static List<String> GetPerPlayerActions() {
        return EconomyLimit.instance.getConfig().getStringList("per-player-actions");
    }

    public static List<String> GetConsoleActions() {
        return EconomyLimit.instance.getConfig().getStringList("console-actions");
    }

}
