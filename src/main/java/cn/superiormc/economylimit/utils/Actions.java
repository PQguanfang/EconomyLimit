package cn.superiormc.economylimit.utils;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Actions {
    public static void DoIt(List<String> action, Player player){
        for(String singleAction : action) {
            if (singleAction.startsWith("none")) {
                return;
            } else if (singleAction.startsWith("message: ")) {
                player.sendMessage(Messages.GetActionMessages(singleAction.substring(9)));
            } else if (singleAction.startsWith("console_command: ")) {
                Bukkit.getScheduler().runTask(EconomyLimit.instance, () -> {
                    DispatchCommand.DoIt(singleAction.substring(17).replace("%player%", player.getName()));
                });
            } else if (singleAction.startsWith("player_command: ")) {
                Bukkit.getScheduler().runTask(EconomyLimit.instance, () -> {
                    DispatchCommand.DoIt(player, singleAction.substring(16));
                });
            }
        }
    }

    public static void DoIt(List<String> action){
        for(String singleAction : action) {
            if (singleAction.startsWith("none")) {
                return;
            } else if (singleAction.startsWith("message: ")) {
                Bukkit.getConsoleSender().sendMessage(Messages.GetActionMessages(singleAction.substring(9)));
            } else if (singleAction.startsWith("console_command: ")) {
                Bukkit.getScheduler().runTask(EconomyLimit.instance, () -> {
                    DispatchCommand.DoIt(singleAction.substring(17));
                });
            }
        }
    }
}
