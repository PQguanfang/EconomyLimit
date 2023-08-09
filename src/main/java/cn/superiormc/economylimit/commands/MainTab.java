package cn.superiormc.economylimit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainTab implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> strings = new ArrayList();
            strings.add("help");
            if (sender.hasPermission("economylimit.admin.reset")) {
                strings.add("reset");
            }
            if (sender.hasPermission("economylimit.admin.reload")) {
                strings.add("reload");
            }
            return strings;
        }
        return null;
    }
}
