package cn.superiormc.economylimit.configs;

import cn.superiormc.economylimit.EconomyLimit;

public class Database {
    public static String GetDatabaseUrl() {
        return EconomyLimit.instance.getConfig().getString("database.jdbc-url").replace("%plugin_folder%", String.valueOf(EconomyLimit.instance.getDataFolder()));
    }
    public static String GetDatabaseClass() {
        return EconomyLimit.instance.getConfig().getString("database.jdbc-class");
    }
    public static String GetDatabaseUser() {
        return EconomyLimit.instance.getConfig().getString("database.properties.user", null);
    }
    public static String GetDatabasePassword() {
        return EconomyLimit.instance.getConfig().getString("database.properties.password", null);
    }
}
