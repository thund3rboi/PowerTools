package dev.micah.powertools.item;

import dev.micah.powertools.PowerTools;
import dev.micah.powertools.config.PowerToolConfig;
import dev.micah.powertools.id.ItemIdentifier;
import dev.micah.powertools.util.Chat;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemPowerTool {

    private ItemStack itemStack;
    private String command;
    private Player owner;
    private String id;

    public ItemPowerTool(@NotNull ItemStack itemStack, @NotNull String command, Player owner) {
        this.itemStack = itemStack;
        this.command = command;
        this.owner = owner;
        this.id = ItemIdentifier.generateId();
    }

    public static class PowerToolData {

        public String itemStack;
        public String command;
        public UUID owner;
        public String id;

        public PowerToolData(ItemStack itemStack, String command, UUID owner, String id) {
            this.itemStack = itemStack.getType().name().toUpperCase();
            this.command = command;
            this.owner = owner;
            this.id = id;
        }

    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Player getOwner() {
        return owner;
    }

    public String getCommand() {
        return command;
    }

    public String getId() {
        return id;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(getItemStack().getType());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(PowerToolConfig.getString("power-tool.item-name"));
        List<String> lore = new ArrayList<>();
        PowerTools.getPlugin(PowerTools.class).getConfig().getStringList("power-tool.lore").forEach(s -> lore.add(Chat.color(s
                .replaceAll("%command%", getCommand())
                .replaceAll("%id%", getId()))));
        itemMeta.setLore(lore);
        itemMeta.setLocalizedName(id);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
