package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.Database;
import cn.superiormc.economylimit.configs.Messages;
import cn.superiormc.economylimit.database.SQLDatabase;
import cn.superiormc.economylimit.database.YamlDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void QuitEvent(PlayerQuitEvent event) {
        if (Database.GetDatabaseEnabled()) {
            SQLDatabase.UpdateData(event.getPlayer());
        } else {
            YamlDatabase.UpdateData(event.getPlayer());
        }
        EconomyLimit.getWarnedPlayer.remove(event.getPlayer());
        EconomyLimit.getLimitMap.remove(event.getPlayer());
        EconomyLimit.getDataMap.remove(event.getPlayer());
    }
}
