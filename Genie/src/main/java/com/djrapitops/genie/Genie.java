
package com.djrapitops.genie;

import com.djrapitops.javaplugin.RslPlugin;
import com.djrapitops.javaplugin.api.ColorScheme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Main class.
 * @author Rsl1122
 */
public class Genie extends RslPlugin<Genie>{

    @Override
    public void onEnable() {
        super.setLogPrefix("[Genie]");
        super.setColorScheme(new ColorScheme(ChatColor.AQUA, ChatColor.GRAY, ChatColor.DARK_AQUA));
        super.setDebugMode("console");
        super.onEnableDefaultTasks();
        Log.info("Plugin Enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        taskStatus().cancelAllKnownTasks();
        Log.info("Plugin Disabled.");
    }
    
    public static Genie getInstance() {
        return (Genie) getPluginInstance();
    }
}
