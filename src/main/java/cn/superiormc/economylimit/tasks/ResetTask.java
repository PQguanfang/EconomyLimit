package cn.superiormc.economylimit.tasks;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.Action;
import cn.superiormc.economylimit.configs.DailyReset;
import cn.superiormc.economylimit.database.SQLDatabase;
import cn.superiormc.economylimit.utils.Actions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ResetTask {
    public void StartTask(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (GetSQLTime(player) != null && GetSQLTime(player).isBefore(GetNowingTime())) {
                EconomyLimit.getDataMap.replace(player, GetNowingTime().plusDays(1L).
                        withHour(DailyReset.GetResetHour()).
                        withMinute(DailyReset.GetResetMinute()).
                        withSecond(DailyReset.GetResetSecond()));
                Actions.DoIt(Action.GetPerPlayerActions(), player);
                EconomyLimit.getLimitMap.get(player).ResetPlayerLimit("Vanilla Exp");
                EconomyLimit.getLimitMap.get(player).ResetPlayerLimit("Vanilla Levels");
            }
        }
        if (!EconomyLimit.getIntMap.contains(GetNowingTime()) && GetNowingTime().getHour() == DailyReset.GetResetHour() &&
                GetNowingTime().getMinute() == DailyReset.GetResetMinute() &&
                GetNowingTime().getSecond() == DailyReset.GetResetSecond()) {
            Actions.DoIt(Action.GetConsoleActions());
            EconomyLimit.getIntMap.add(GetNowingTime());
        }
    }

    // 获取执行该方法时的时间
    // 两个时间，一个是现在时间，另外一个是 SQL 的时间
    // 玩家每天进服都会有一个 SQL 的时间
    // 第二天时现在时间就会晚于 SQL 的时间
    public static ZonedDateTime GetNowingTime() {
        return Instant.now().atZone(ZoneId.of(DailyReset.GetTimeZone()));
    }

    public static ZonedDateTime GetSQLTime(Player player) {
        return EconomyLimit.getDataMap.get(player);
    }
}
