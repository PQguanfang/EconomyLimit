package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.database.SQLDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent event) {
        SQLDatabase.CheckData(event.getPlayer());
    }
}
