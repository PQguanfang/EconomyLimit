package cn.superiormc.economylimit;

import cn.superiormc.economylimit.commands.MainCommand;
import cn.superiormc.economylimit.commands.MainTab;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.database.SQLDatabase;
import cn.superiormc.economylimit.events.GainExp;
import cn.superiormc.economylimit.events.PlayerJoin;
import cn.superiormc.economylimit.events.PlayerQuit;
import cn.superiormc.economylimit.managers.LimitsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class EconomyLimit extends JavaPlugin {

    public static JavaPlugin instance;
    // 玩家UUID
    public static Set<String> getCreatedPlayer = new HashSet<>();

    public static Map<Player, LimitsManager> getLimitMap = new HashMap<>();

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        Events();
        Commands();
        SQLDatabase.InitSQL();
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        SQLDatabase.CloseSQL();
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyLimit] §fPlugin is disabled. Author: PQguanfang.");
    }

    public void Events() {
        if(VanillaExp.GetVanillaExpEnabled() || VanillaLevels.GetVanillaLevelsEnabled()) {
            Bukkit.getPluginManager().registerEvents(new GainExp(), this);
        }
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
    }

    public void Commands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("economylimit")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("economylimit")).setTabCompleter(new MainTab());
    }
}
