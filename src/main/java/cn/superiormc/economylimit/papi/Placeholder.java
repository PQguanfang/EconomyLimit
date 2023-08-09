package cn.superiormc.economylimit.papi;

import cn.superiormc.economylimit.EconomyLimit;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class Placeholder extends PlaceholderExpansion {

    private final EconomyLimit plugin;

    @Override
    public boolean canRegister() {
        return true;
    }

    public Placeholder(EconomyLimit plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "PQguanfang";
    }

    @Override
    public String getIdentifier() {
        return "economylimit";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player.getPlayer() == null) {
            return null;
        }
        else if (params.contains("exp")) {
            return String.valueOf(EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Exp"));
        }
        else if (params.contains("levels")) {
            return String.valueOf(EconomyLimit.getLimitMap.get(player).GetPlayerLimit("Vanilla Levels"));
        }
        return null;
    }

}
