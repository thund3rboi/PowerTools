package dev.micah.powertools.config;

import dev.micah.powertools.PowerTools;
import dev.micah.powertools.util.Chat;

public class PowerToolConfig {

    public static String getString(String path) {
        return Chat.color(PowerTools.getProvidingPlugin(PowerTools.class).getConfig().getString(path));
    }

}
