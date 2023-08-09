package cn.superiormc.economylimit;

import cn.superiormc.economylimit.commands.MainCommand;
import cn.superiormc.economylimit.commands.MainTab;
import cn.superiormc.economylimit.configs.DailyReset;
import cn.superiormc.economylimit.configs.Database;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.database.SQLDatabase;
import cn.superiormc.economylimit.events.GainExp;
import cn.superiormc.economylimit.events.GainLevels;
import cn.superiormc.economylimit.events.PlayerJoin;
import cn.superiormc.economylimit.events.PlayerQuit;
import cn.superiormc.economylimit.managers.LimitsManager;
import cn.superiormc.economylimit.papi.Placeholder;
import cn.superiormc.economylimit.tasks.ResetTask;
import cn.superiormc.economylimit.tasks.SaveTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.ZonedDateTime;
import java.util.*;

public final class EconomyLimit extends JavaPlugin {

    public static JavaPlugin instance;

    private static Placeholder papi = null;

    public static Map<Player, LimitsManager> getLimitMap = new HashMap<>();

    public static Map<Player, ZonedDateTime> getDataMap = new HashMap<>();

    public static List<ZonedDateTime> getIntMap = new ArrayList<>();

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        Events();
        Commands();
        Tasks();
        if (Database.GetDatabaseEnabled()) {
            SQLDatabase.InitSQL();
        }
        if (EconomyLimit.instance.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            papi = new Placeholder(this);
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fHooking into PlaceholderAPI...");
            if (papi.register()){
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fFinished hook!");
            }
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        if (Database.GetDatabaseEnabled()) {
            SQLDatabase.CloseSQL();
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fPlugin is disabled. Author: PQguanfang.");
    }

    public void Events() {
        if(VanillaExp.GetVanillaExpEnabled()) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fEnabled Exp limit.");
            Bukkit.getPluginManager().registerEvents(new GainExp(), this);
        }
        if(VanillaLevels.GetVanillaLevelsEnabled()) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fEnabled Levels limit.");
            Bukkit.getPluginManager().registerEvents(new GainLevels(), this);
        }
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
    }

    public void Commands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("economylimit")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("economylimit")).setTabCompleter(new MainTab());
    }

    public void Tasks() {
        (new BukkitRunnable() {

            @Override
            public void run() {
                ResetTask resetTask = new ResetTask();
                resetTask.StartTask();
                if (DailyReset.GetSaveEnabled()) {
                    SaveTask saveTask = new SaveTask();
                    saveTask.StartTask();
                }
            }

        }).runTaskTimer(EconomyLimit.instance, 0L, DailyReset.GetPeriodTime());
    }
}
