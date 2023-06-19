package cn.superiormc.economylimit.commands;

import cn.superiormc.economylimit.configs.Messages;
import cn.superiormc.economylimit.database.SQLDatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage(Messages.GetMessages("error-args"));
            return true;
        }
        if (args.length == 2) {
            if (args[0].equals("reset")) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("economylimit.use.reset")) {
                        sender.sendMessage(Messages.GetMessages("reseted"));
                        SQLDatabase.ResetData((Player) sender);
                        return true;
                    }
                    else {
                        sender.sendMessage(Messages.GetMessages("error-miss-permission"));
                        return true;
                    }
                }
                else {
                    sender.sendMessage(Messages.GetMessages("error-only-in-game"));
                    return true;
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equals("reset")) {
                if (sender.hasPermission("economylimit.admin.reset")) {
                    if (Bukkit.getPlayer(args[2]) == null) {
                        sender.sendMessage(Messages.GetMessages("error-player-not-found"));
                        return true;
                    }
                    else {
                        sender.sendMessage(Messages.GetMessages("reseted"));
                        SQLDatabase.ResetData(Bukkit.getPlayer(args[2]));
                        return true;
                    }
                }
                else {
                    sender.sendMessage(Messages.GetMessages("error-miss-permission"));
                    return true;
                }
            }
        }
        else {
            sender.sendMessage(Messages.GetMessages("error-args"));
            return true;
        }
        return true;
    }
}
