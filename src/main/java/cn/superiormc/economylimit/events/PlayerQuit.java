package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.database.SQLDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void QuitEvent(PlayerQuitEvent event) {
        SQLDatabase.UpdateData(event.getPlayer());
    }
}
