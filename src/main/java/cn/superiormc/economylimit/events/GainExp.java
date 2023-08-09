package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.utils.GetPlayerLimit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class GainExp implements Listener {
    @EventHandler
    public void GainExpEvent(PlayerExpChangeEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (VanillaExp.GetVanillaExpBypassPermission() != null && event.getPlayer().hasPermission(VanillaExp.GetVanillaExpBypassPermission())) {
            return;
        } else {
            if (EconomyLimit.getLimitMap.containsKey(event.getPlayer())) {
                if (EconomyLimit.getLimitMap.get(event.getPlayer()).GetPlayerLimit("Vanilla Exp") >= GetPlayerLimit.GetVanillaExpLimit(event.getPlayer())) {
                    event.setAmount(0);
                }
                else {
                    EconomyLimit.getLimitMap.get(event.getPlayer()).UpdatePlayerLimit("Vanilla Exp", event.getAmount());
                }
            }
        }
    }
}
