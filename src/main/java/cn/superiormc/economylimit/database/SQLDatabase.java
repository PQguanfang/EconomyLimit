package cn.superiormc.economylimit.database;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import cc.carm.lib.easysql.api.enums.IndexType;
import cc.carm.lib.easysql.hikari.HikariConfig;
import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.DailyReset;
import cn.superiormc.economylimit.configs.Database;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.managers.LimitsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SQLDatabase {
    public static SQLManager sqlManager;

    public static void InitSQL() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fTrying connect to SQL database...");
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(Database.GetDatabaseClass());
        config.setJdbcUrl(Database.GetDatabaseUrl());
        if ((Database.GetDatabaseUser() != null && Database.GetDatabasePassword() != null)) {
            config.setUsername(Database.GetDatabaseUser());
            config.setPassword(Database.GetDatabasePassword());
        }
        sqlManager = EasySQL.createManager(config);
        try {
            if (!sqlManager.getConnection().isValid(5)) {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §cFailed connect to SQL database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CreateTable();
    }

    public static void CloseSQL() {
        if (Objects.nonNull(sqlManager)) {
            EasySQL.shutdownManager(sqlManager);
            sqlManager = null;
        }
    }

    public static void CreateTable() {
        sqlManager.createTable("economylimit")
                .addColumn("uuid", "VARCHAR(36)")
                .addColumn("vanilla_exp", "INT")
                .addColumn("vanilla_levels", "INT")
                .addColumn("custom", "INT")
                .addColumn("year", "INT")
                .addColumn("month", "INT")
                .addColumn("day", "INT")
                .addColumn("hour", "INT")
                .addColumn("minute", "INT")
                .addColumn("second", "INT")
                .setIndex(IndexType.PRIMARY_KEY, null, "uuid")
                .build().execute(null);
    }

    public static void InsertData(Player player) {
        if (EconomyLimit.getLimitMap.containsKey(player) || EconomyLimit.getDataMap.containsKey(player)) {
            return;
        }
        ZonedDateTime dateTime = Instant.now().atZone(ZoneId.of(DailyReset.GetTimeZone())).plusDays(1L).
                withHour(DailyReset.GetResetHour()).
                withMinute(DailyReset.GetResetMinute()).
                withSecond(DailyReset.GetResetSecond());
        sqlManager.createInsert("economylimit")
                .setColumnNames("uuid", "vanilla_exp", "vanilla_levels", "year", "month", "day", "hour", "minute", "second")
                .setParams(player.getUniqueId().toString(), 0, 0, dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond())
                .executeAsync();
        CheckData(player);
    }

    public static void CheckData(Player player) {
        QueryAction queryAction = sqlManager.createQuery()
                .inTable("economylimit")
                .selectColumns("uuid", "vanilla_exp", "vanilla_levels", "year", "month", "day", "hour", "minute", "second")
                .addCondition("uuid = '" + player.getUniqueId().toString() + "'")
                .build();
        queryAction.executeAsync((result) ->
        {
            if (result.getResultSet().next()) {
                ZonedDateTime dateTime = ZonedDateTime.of(result.getResultSet().getInt("year"),
                        result.getResultSet().getInt("month"),
                        result.getResultSet().getInt("day"),
                        result.getResultSet().getInt("hour"),
                        result.getResultSet().getInt("minute"),
                        result.getResultSet().getInt("second"),
                        0,
                        ZoneId.of(DailyReset.GetTimeZone()));
                if (EconomyLimit.getDataMap.containsKey(player)) {
                    EconomyLimit.getDataMap.replace(player, dateTime);
                } else {
                    EconomyLimit.getDataMap.put(player, dateTime);
                }
                Map<String, Integer> limitMap = new HashMap<>();
                if (VanillaExp.GetVanillaExpEnabled()) {
                    limitMap.put("Vanilla Exp", result.getResultSet().getInt("vanilla_exp"));
                }
                if (VanillaLevels.GetVanillaLevelsEnabled()) {
                    limitMap.put("Vanilla Levels", result.getResultSet().getInt("vanilla_levels"));
                }
                // so on...
                if (EconomyLimit.getLimitMap.containsKey(player)) {
                    EconomyLimit.getLimitMap.replace(player, new LimitsManager(player, limitMap));
                } else {
                    EconomyLimit.getLimitMap.put(player, new LimitsManager(player, limitMap));
                }
                return;
            }
            InsertData(player);
        });
    }

    public static void UpdateData(Player player) {
        ZonedDateTime dateTime = EconomyLimit.getDataMap.get(player);
        sqlManager.createReplace("economylimit")
                .setColumnNames("uuid", "vanilla_exp", "vanilla_levels", "year", "month", "day", "hour", "minute", "second")
                .setParams(player.getUniqueId().toString(),
                        EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Exp"),
                        EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Levels"),
                        dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond())
                .executeAsync();
    }

}
