package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.configs.Database;
import cn.superiormc.economylimit.database.SQLDatabase;
import cn.superiormc.economylimit.database.YamlDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void JoinEvent(PlayerLoginEvent event) {
        if (Database.GetDatabaseEnabled()) {
            SQLDatabase.CheckData(event.getPlayer());
        } else {
            YamlDatabase.CheckData(event.getPlayer());
        }
    }
}
