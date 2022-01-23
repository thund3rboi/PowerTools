package dev.micah.powertools.commands;

import dev.micah.powertools.PowerToolManager;
import dev.micah.powertools.PowerTools;
import dev.micah.powertools.config.PowerToolConfig;
import dev.micah.powertools.item.ItemPowerTool;
import dev.micah.powertools.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CheckToolCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("powertool.check")) {
            sender.sendMessage(PowerTools.PREFIX + PowerToolConfig.getString("messages.no-permission")); return false;
        }

        Player player = (Player) sender;
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() != Material.AIR || heldItem.getItemMeta() == null) {
            ItemPowerTool.PowerToolData data = PowerTools.getPowerToolManager().getFromId(heldItem.getItemMeta().getLocalizedName());
            if (data != null) {
                player.sendMessage(PowerTools.PREFIX + PowerToolConfig.getString("messages.power-tool-check")
                        .replaceAll("%command%", data.command));
            } else {
                player.sendMessage(Chat.color("&a[PowerTools] The power tool you are holding doesn't have any data!"));
            }
        } else {
            player.sendMessage(Chat.color("&a[PowerTools] The item in your hand is not a valid power tool!"));
        }

        return false;
    }

}
