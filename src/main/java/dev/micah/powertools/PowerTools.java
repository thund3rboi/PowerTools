package dev.micah.powertools;

import dev.micah.powertools.commands.CheckToolCommand;
import dev.micah.powertools.commands.PowerToolsCommands;
import dev.micah.powertools.io.FileManager;
import dev.micah.powertools.listeners.InteractListener;
import dev.micah.powertools.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerTools extends JavaPlugin {

    public static String PREFIX;

    private static FileManager fileManager;
    private static PowerToolManager powerToolManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PREFIX = Chat.color(getConfig().getString("prefix") + " &r");

        fileManager = new FileManager(getDataFolder());

        powerToolManager = new PowerToolManager();
        powerToolManager.load();

        getCommand("powertool").setExecutor(new PowerToolsCommands());
        getCommand("checktool").setExecutor(new CheckToolCommand());
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
    }

    @Override
    public void onDisable() {
        powerToolManager.save();
        PowerToolManager.getPowerTools().clear();
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static PowerToolManager getPowerToolManager() {
        return powerToolManager;
    }

}
