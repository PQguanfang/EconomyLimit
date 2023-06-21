package cn.superiormc.economylimit.database;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import cc.carm.lib.easysql.api.enums.IndexType;
import cc.carm.lib.easysql.hikari.HikariConfig;
import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.Database;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.managers.LimitsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
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
                .setIndex(IndexType.PRIMARY_KEY, null, "uuid")
                .build().execute(null);
    }

    public static void InsertData(Player player) {
        if (EconomyLimit.getLimitMap.containsKey(player)) {
            return;
        }
        sqlManager.createInsert("economylimit")
                .setColumnNames("uuid", "vanilla_exp", "vanilla_levels")
                .setParams(player.getUniqueId().toString(), 0, 0)
                .executeAsync();
        CheckData(player);
    }

    public static void CheckData(Player player) {
        QueryAction queryAction = sqlManager.createQuery()
                .inTable("economylimit")
                .selectColumns("uuid", "vanilla_exp", "vanilla_levels")
                .addCondition("uuid = '" + player.getUniqueId().toString() + "'")
                .build();
        queryAction.executeAsync((result) ->
        {
            if (result.getResultSet().next()) {
                EconomyLimit.getCreatedPlayer.add(result.getResultSet().getString("uuid"));
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
        sqlManager.createReplace("economylimit")
                .setColumnNames("uuid", "vanilla_exp", "vanilla_levels")
                .setParams(player.getUniqueId().toString(),
                        EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Exp"),
                        EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Levels"))
                .executeAsync();
    }

    public static void ResetData(Player player) {
        sqlManager.createReplace("economylimit")
                .setColumnNames("uuid", "vanilla_exp", "vanilla_levels")
                .setParams(player.getUniqueId().toString(), 0, 0)
                .executeAsync();
    }

}
