package dev.micah.powertools.listeners;

import dev.micah.powertools.PowerTools;
import dev.micah.powertools.item.ItemPowerTool;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteractPlayer(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR
                || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName().chars().count() == 8) {
            ItemPowerTool.PowerToolData data = PowerTools.getPowerToolManager().getFromId(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
            if (data != null) {
                if (event.getRightClicked() instanceof Player) {
                    event.getPlayer().performCommand(data.command.replaceAll("%player%", event.getRightClicked().getName()));
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getItemMeta() == null) return;
        if (event.getItem().getItemMeta().getLocalizedName().chars().count() == 8) {
            ItemPowerTool.PowerToolData data = PowerTools.getPowerToolManager().getFromId(event.getItem().getItemMeta().getLocalizedName());
            if (data != null) {
                event.getPlayer().performCommand(data.command);
            }
        }
    }

}
