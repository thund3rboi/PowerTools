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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PowerToolsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("powertool.bind")) {
            sender.sendMessage(PowerToolConfig.getString("messages.no-permission")); return false;
        }

        Player player = (Player) sender;

        StringBuilder cmdString = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String argument = args[i] + " "; cmdString.append(argument);
        }
        if (cmdString.toString().startsWith("/")) { cmdString = new StringBuilder(cmdString.substring(1)); }

        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem.getType() == Material.AIR) return false;

        ItemPowerTool itemPowerTool = new ItemPowerTool(heldItem.clone(), cmdString.toString(), player);
        player.getInventory().removeItem(heldItem);
        PowerToolManager.getPowerTools().add(new ItemPowerTool.PowerToolData(itemPowerTool.getItemStack(),
                itemPowerTool.getCommand(),
                itemPowerTool.getOwner().getUniqueId(),
                itemPowerTool.getId()));

        if (heldItem.getItemMeta() != null) {
            if (heldItem.getItemMeta().getLocalizedName().chars().count() == 8) {
                ItemPowerTool.PowerToolData data = PowerTools.getPowerToolManager().getFromId(heldItem.getItemMeta().getLocalizedName());
                if (data != null) {
                    player.sendMessage(PowerTools.PREFIX + PowerToolConfig.getString("messages.power-tool-changed")
                            .replaceAll("%old-command%", data.command)
                            .replaceAll("%command%", cmdString.toString()));
                    PowerToolManager.getPowerTools().remove(data);
                    player.getInventory().addItem(itemPowerTool.build());
                }
                return false;
            }
        }
        player.sendMessage(PowerTools.PREFIX + PowerToolConfig.getString("messages.power-tool-bind")
                .replaceAll("%command%", cmdString.toString()));
        player.getInventory().addItem(itemPowerTool.build());

        return false;
    }

}
