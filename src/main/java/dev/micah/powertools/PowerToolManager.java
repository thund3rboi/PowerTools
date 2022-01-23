package dev.micah.powertools;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dev.micah.powertools.item.ItemPowerTool;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PowerToolManager {

    private PowerTools plugin;
    private File file;

    public PowerToolManager() {
        this.plugin = PowerTools.getPlugin(PowerTools.class);
        powerTools = new ArrayList<>();
        file = new File(plugin.getDataFolder(), "item-data.json");
        if (!file.exists()) {
            try {
                Bukkit.getLogger().info("[PowerTools] Created item-data.json");
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        Bukkit.getLogger().info("[PowerTools] Power items saved!");
        Type listType = new TypeToken<List<ItemPowerTool.PowerToolData>>() {}.getType();
        PowerTools.getFileManager().writeList(file, listType, getPowerTools());
    }

    public void load() {
        Bukkit.getLogger().info("[PowerTools] Power items loaded!");
        Type listType = new TypeToken<List<ItemPowerTool.PowerToolData>>() {}.getType();
        List<ItemPowerTool.PowerToolData> loaded = PowerTools.getFileManager().readList(file, listType);
        powerTools = loaded;
    }

    public ItemPowerTool.PowerToolData getFromId(String id) {
        for (ItemPowerTool.PowerToolData data : getPowerTools()) {
            if (data.id.equals(id)) {
                return data;
            }
        }
        return null;
    }

    private static List<ItemPowerTool.PowerToolData> powerTools; // get all active power tools

    public static List<ItemPowerTool.PowerToolData> getPowerTools() {
        return powerTools;
    }
}
