package com.djrapitops.genie;

import com.djrapitops.genie.command.GenieCommand;
import com.djrapitops.genie.file.*;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.genie.listeners.*;
import com.djrapitops.genie.wishes.WishManager;
import com.djrapitops.javaplugin.RslPlugin;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.utilities.Verify;
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

    private WishLog wishLog;
    private WishManager wishManager;
    private WishConfigSectionHandler wishConfigSectionHandler;
    private LampManager lampManager;

    private UnfulfilledWishStorage unfulfilledWishStore;

    private List<String> worldBlacklist;
    private Messages messages;

    @Override
    public void onEnable() {
        setInstance(this);

        getConfig().options().copyDefaults(true);
        getConfig().options().header("Genie Config");
        saveConfig();

        super.setLogPrefix("[Genie]");
        super.setUpdateUrl("https://www.spigotmc.org/resources/genie.43260/");
        super.setUpdateCheckUrl("https://raw.githubusercontent.com/Rsl1122/Genie/master/Genie/src/main/resources/plugin.yml");
        super.setColorScheme(new ColorScheme(ChatColor.DARK_AQUA, ChatColor.GRAY, ChatColor.AQUA));
        super.setDebugMode(getConfig().getString(Settings.DEBUG.getPath()));
        super.onEnableDefaultTasks();

        processStatus().startExecution("onEnable");

        updateWorldBlacklist();

        wishConfigSectionHandler = new WishConfigSectionHandler(this);
        wishLog = new WishLog(this);
        wishManager = new WishManager(this);
        messages = new Messages();

        try {
            LampStorage lampStorage = new LampStorage(this);
            unfulfilledWishStore = new UnfulfilledWishStorage(this);
            lampManager = new LampManager(this, lampStorage);
            Verify.nullCheck(wishLog, wishManager, messages, lampManager, worldBlacklist);
        } catch (NullPointerException | IOException | InvalidConfigurationException ex) {
            Log.error("Plugin initialization has failed, disabling plugin.");
            Log.toLog(this.getClass().getName(), ex);
            disablePlugin();
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

    private void updateWorldBlacklist() {
        String path = Settings.WORLD_BLACKLIST.getPath();
        worldBlacklist = getConfig().getStringList(path).stream()
                .map(l -> l.toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean isWorldAllowed(World w) {
        return !worldBlacklist.contains(w.getName().toLowerCase());
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

    public Messages getMsg() {
        return messages;
    }

    public UnfulfilledWishStorage getUnfulfilledWishStore() {
        return unfulfilledWishStore;
    }
}
