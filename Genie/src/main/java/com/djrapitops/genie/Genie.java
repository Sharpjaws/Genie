package com.djrapitops.genie;

import com.djrapitops.genie.file.LampStorage;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.javaplugin.RslPlugin;
import com.djrapitops.javaplugin.api.ColorScheme;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

/**
 * Main class.
 *
 * @author Rsl1122
 */
public class Genie extends RslPlugin<Genie> {

    private LampManager lampManager;
    private WishLog wishLog;

    @Override
    public void onEnable() {
        super.setLogPrefix("[Genie]");
        super.setColorScheme(new ColorScheme(ChatColor.AQUA, ChatColor.GRAY, ChatColor.DARK_AQUA));
        super.setDebugMode("console");
        super.onEnableDefaultTasks();
        wishLog = new WishLog(this);
        try {
            LampStorage lampStorage = new LampStorage(this);
            lampManager = new LampManager(lampStorage);
        } catch (IOException | InvalidConfigurationException ex) {
            Log.toLog(this.getClass().getName(), ex);
            Log.error("Could not create 'lamps' storage file");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
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

    public WishLog getWishLog() {
        return wishLog;
    }
}
