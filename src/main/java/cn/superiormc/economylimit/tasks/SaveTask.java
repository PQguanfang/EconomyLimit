package cn.superiormc.economylimit.tasks;

import cn.superiormc.economylimit.database.SQLDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SaveTask {

    public void StartTask(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            SQLDatabase.UpdateData(player);
        }
    }
}
