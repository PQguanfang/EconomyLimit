package cn.superiormc.economylimit.commands;

import cn.superiormc.economylimit.EconomyLimit;
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
        if (args.length == 0) {
            sender.sendMessage(Messages.GetMessages("error-args"));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equals("reset")) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("economylimit.use.reset")) {
                        sender.sendMessage(Messages.GetMessages("reseted"));
                        EconomyLimit.getLimitMap.get((Player) sender).ResetPlayerLimit("Vanilla Exp");
                        EconomyLimit.getLimitMap.get((Player) sender).ResetPlayerLimit("Vanilla Levels");
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
            else if (args[0].equals("reload")) {
                if (sender.hasPermission("economylimit.admin.reload")) {
                    EconomyLimit.instance.reloadConfig();
                    sender.sendMessage(Messages.GetMessages("reloaded"));
                    return true;
                }
                else {
                    sender.sendMessage(Messages.GetMessages("error-miss-permission"));
                    return true;
                }
            }
            else if (args[0].equals("help")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Messages.GetMessages("help.main-console"));
                    return true;
                }
                else if (sender.hasPermission("spintowin.admin.help")) {
                    sender.sendMessage(Messages.GetMessages("help.main-admin"));
                    return true;
                } else {
                    sender.sendMessage(Messages.GetMessages("help.main"));
                    return true;
                }
            }
        }
        if (args.length == 2) {
            if (args[0].equals("reset")) {
                if (sender.hasPermission("economylimit.admin.reset")) {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        sender.sendMessage(Messages.GetMessages("error-player-not-found"));
                        return true;
                    }
                    else {
                        sender.sendMessage(Messages.GetMessages("reseted"));
                        EconomyLimit.getLimitMap.get(Bukkit.getPlayer(args[1])).ResetPlayerLimit("Vanilla Exp");
                        EconomyLimit.getLimitMap.get(Bukkit.getPlayer(args[1])).ResetPlayerLimit("Vanilla Levels");
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
