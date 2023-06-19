package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.utils.GetPlayerLimit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class GainExp implements Listener {
    @EventHandler
    public void GainExpEvent(PlayerExpChangeEvent event) {
        if(event.getPlayer() == null) {
            return;
        }
        if((VanillaExp.GetVanillaExpBypassPermission() != null && event.getPlayer().hasPermission(VanillaExp.GetVanillaExpBypassPermission()))) {
            return;
        } else {
            if (EconomyLimit.getLimitMap.containsKey(event.getPlayer())) {
                EconomyLimit.getLimitMap.get(event.getPlayer()).UpdatePlayerLimit("Vanilla Exp", event.getAmount());
                if (EconomyLimit.getLimitMap.get(event.getPlayer()).GetPlayerLimit("Vanilla Exp") >= GetPlayerLimit.GetVanillaExpLimit(event.getPlayer())) {
                    event.getPlayer().giveExp(-event.getAmount());
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("" + EconomyLimit.getLimitMap.get(event.getPlayer()).GetPlayerLimit("Vanilla Exp"));
    }
}
