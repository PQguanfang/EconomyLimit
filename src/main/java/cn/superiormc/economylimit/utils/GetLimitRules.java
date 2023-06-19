package cn.superiormc.economylimit.utils;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;


public class GetLimitRules {
    public static int GetLimitRulesAmount() {
        int i = 0;
        if (VanillaExp.GetVanillaExpEnabled()) {
            i ++;
        }
        else if (VanillaLevels.GetVanillaLevelsEnabled()) {
            i ++;
        }
        return i;
    }
}
