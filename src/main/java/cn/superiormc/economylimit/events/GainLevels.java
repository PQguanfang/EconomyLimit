package cn.superiormc.economylimit.events;

import cn.superiormc.economylimit.EconomyLimit;
import cn.superiormc.economylimit.configs.Messages;
import cn.superiormc.economylimit.configs.VanillaExp;
import cn.superiormc.economylimit.configs.VanillaLevels;
import cn.superiormc.economylimit.utils.GetPlayerLimit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class GainLevels implements Listener {
    @EventHandler
    public void GainLevelsEvent(PlayerLevelChangeEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (VanillaLevels.GetVanillaLevelsBypassPermission() != null && event.getPlayer().hasPermission(VanillaExp.GetVanillaExpBypassPermission())) {
            return;
        } else {
            if (EconomyLimit.getLimitMap.containsKey(event.getPlayer())) {
                if (EconomyLimit.getLimitMap.get(event.getPlayer()).GetPlayerLimit("Vanilla Levels") >= GetPlayerLimit.GetVanillaLevelsLimit(event.getPlayer())) {
                    event.getPlayer().giveExpLevels(-(event.getNewLevel() - event.getOldLevel()));
                    if (!EconomyLimit.getWarnedPlayer.contains(event.getPlayer())) {
                        event.getPlayer().sendMessage(Messages.GetMessages("vanilla-exp-rached"));
                        EconomyLimit.getWarnedPlayer.add(event.getPlayer());
                    }
                }
                else {
                    EconomyLimit.getLimitMap.get(event.getPlayer()).UpdatePlayerLimit("Vanilla Levels", event.getNewLevel() - event.getOldLevel());
                }
            }
        }
    }
}
