package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;

public class DailyReset {
    public static String GetTimeZone() {
        return EconomyLimit.instance.getConfig().getString("daily-reset.time-zone");
    }

    public static int GetResetHour() {
        return EconomyLimit.instance.getConfig().getInt("daily-reset.reset-time.hour");
    }

    public static int GetResetMinute() {
        return EconomyLimit.instance.getConfig().getInt("daily-reset.reset-time.minute");
    }

    public static int GetResetSecond() {
        return EconomyLimit.instance.getConfig().getInt("daily-reset.reset-time.second");
    }

    public static long GetPeriodTime() {
        return EconomyLimit.instance.getConfig().getLong("daily-reset.period-time");
    }

    public static boolean GetSaveEnabled() {
        return EconomyLimit.instance.getConfig().getBoolean("daily-reset.auto-save");
    }
}
