package com.djrapitops.genie;

import com.djrapitops.genie.listeners.ChatListener;
import com.djrapitops.genie.command.GenieCommand;
import com.djrapitops.genie.file.LampStorage;
import com.djrapitops.genie.file.WishConfigSectionHandler;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.genie.listeners.DeathListener;
import com.djrapitops.genie.listeners.ItemInteractionListener;
import com.djrapitops.genie.wishes.WishManager;
import com.djrapitops.javaplugin.RslPlugin;
import com.djrapitops.javaplugin.api.ColorScheme;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

/**
 * Main class.
 *
 * @author Rsl1122
 */
public class Genie extends RslPlugin<Genie> {

    private LampManager lampManager;
    private WishLog wishLog;
    private WishConfigSectionHandler wishConfigSectionHandler;
    private WishManager wishManager;
    private List<String> blacklistedWorlds;
    private Messages msg;

    @Override
    public void onEnable() {
        super.setInstance(this);
        getConfig().options().copyDefaults(true);
        getConfig().options().header("Genie Config");
        saveConfig();
        super.setLogPrefix("[Genie]");
        super.setUpdateCheckUrl("https://raw.githubusercontent.com/Rsl1122/Genie/master/Genie/src/main/resources/plugin.yml");
        super.setColorScheme(new ColorScheme(ChatColor.DARK_AQUA, ChatColor.GRAY, ChatColor.AQUA));
        super.setDebugMode(getConfig().getString(Settings.DEBUG.getPath()));
        super.onEnableDefaultTasks();
        processStatus().startExecution("onEnable");
        updateBlacklist();
        wishConfigSectionHandler = new WishConfigSectionHandler(this);
        wishLog = new WishLog(this);
        wishManager = new WishManager(this);
        msg = new Messages();
        try {
            LampStorage lampStorage = new LampStorage(this);
            lampManager = new LampManager(this, lampStorage);
        } catch (IOException | InvalidConfigurationException ex) {
            Log.toLog(this.getClass().getName(), ex);
            Log.error("Could not create 'lamps' storage file");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        registerListener(new ChatListener(this));
        registerListener(new DeathListener(this));
        registerListener(new ItemInteractionListener(this));
        registerCommand(new GenieCommand(this));
        processStatus().finishExecution("onEnable");
        Log.info("Plugin Enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        taskStatus().cancelAllKnownTasks();
        Log.info("Plugin Disabled.");
    }

    public static Genie getInstance() {
        return (Genie) getPluginInstance(Genie.class);
    }

    public WishLog getWishLog() {
        return wishLog;
    }

    public LampManager getLampManager() {
        return lampManager;
    }

    public WishConfigSectionHandler getWishConfigSectionHandler() {
        return wishConfigSectionHandler;
    }

    public WishManager getWishManager() {
        return wishManager;
    }

    private void updateBlacklist() {
        blacklistedWorlds = getConfig().getStringList(Settings.WORLD_BLACKLIST.getPath()).stream()
                .map(l -> l.toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean isWorldAllowed(World w) {
        return !blacklistedWorlds.contains(w.getName().toLowerCase());
    }

    public Messages getMsg() {
        return msg;
    }
}
