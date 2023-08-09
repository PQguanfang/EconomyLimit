package cn.superiormc.economylimit.database;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.DailyReset;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.managers.LimitsManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class YamlDatabase {

    public static void InsertData (Player player) {
        File dir = new File(EconomyLimit.instance.getDataFolder()+"/data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        ZonedDateTime dateTime = Instant.now().atZone(ZoneId.of(DailyReset.GetTimeZone())).plusDays(1L).
                withHour(DailyReset.GetResetHour()).
                withMinute(DailyReset.GetResetMinute()).
                withSecond(DailyReset.GetResetSecond());
        Map<String, Object> data = new HashMap<>();
        data.put("vanilla_exp", 0);
        data.put("vanilla_levels", 0);
        data.put("year", dateTime.getYear());
        data.put("month", dateTime.getMonthValue());
        data.put("day", dateTime.getDayOfMonth());
        data.put("hour", dateTime.getHour());
        data.put("minute", dateTime.getMinute());
        data.put("second", dateTime.getSecond());
        File file = new File(dir,player.getUniqueId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        for (String key : data.keySet()) {
            config.set(key, data.get(key));
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CheckData (Player player) {
        File dir = new File(EconomyLimit.instance.getDataFolder()+"/data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir,player.getUniqueId() + ".yml");
        if (!file.exists()) {
            InsertData(player);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ZonedDateTime dateTime = ZonedDateTime.of(config.getInt("year"),
                config.getInt("month"),
                config.getInt("day"),
                config.getInt("hour"),
                config.getInt("minute"),
                config.getInt("second"),
                0,
                ZoneId.of(DailyReset.GetTimeZone()));
        if (EconomyLimit.getDataMap.containsKey(player)) {
            EconomyLimit.getDataMap.replace(player, dateTime);
        } else {
            EconomyLimit.getDataMap.put(player, dateTime);
        }
        Map<String, Integer> limitMap = new HashMap<>();
        if (VanillaExp.GetVanillaExpEnabled()) {
            limitMap.put("Vanilla Exp", config.getInt("vanilla_exp"));
        }
        if (VanillaLevels.GetVanillaLevelsEnabled()) {
            limitMap.put("Vanilla Levels", config.getInt("vanilla_levels"));
        }
        // so on...
        if (EconomyLimit.getLimitMap.containsKey(player)) {
            EconomyLimit.getLimitMap.replace(player, new LimitsManager(player, limitMap));
        } else {
            EconomyLimit.getLimitMap.put(player, new LimitsManager(player, limitMap));
        }
    }

    public static void UpdateData (Player player) {
        File dir = new File(EconomyLimit.instance.getDataFolder()+"/data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, player.getUniqueId() + ".yml");
        if (file.exists()){
            file.delete();
        }
        Map<String, Object> data = new HashMap<>();
        ZonedDateTime dateTime = EconomyLimit.getDataMap.get(player);
        data.put("vanilla_exp", EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Exp"));
        data.put("vanilla_levels", EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Levels"));
        data.put("year", dateTime.getYear());
        data.put("month", dateTime.getMonthValue());
        data.put("day", dateTime.getDayOfMonth());
        data.put("hour", dateTime.getHour());
        data.put("minute", dateTime.getMinute());
        data.put("second", dateTime.getSecond());
        YamlConfiguration config = new YamlConfiguration();
        for (String key : data.keySet()) {
            config.set(key, data.get(key));
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
