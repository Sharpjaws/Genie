package com.djrapitops.genie.file;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.wishes.Wish;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class responsible for generating and generating settings for PluginData
 * objects to the config.
 *
 * @author Rsl1122
 * @since 3.5.0
 */
public class WishConfigSectionHandler {

    private final Genie plugin;

    public WishConfigSectionHandler(Genie plugin) {
        this.plugin = plugin;
    }

    public boolean exists(Wish wish) {
        ConfigurationSection section = getWishesSection();
        String wishName = wish.getName();
        return section.contains(wishName);
    }

    private ConfigurationSection getWishesSection() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Customization.Wishes");
        return section;
    }

    public void createSection(Wish wish) {
        ConfigurationSection section = getWishesSection();
        String wishName = wish.getClass().getSimpleName().replace("Wisg", "") + "-" + wish.getName();
        section.addDefault(wishName, true);
        FileConfiguration config = plugin.getConfig();
        config.set("Customization.Wishes", section);
        plugin.saveConfig();
    }

    public boolean isAllowed(Wish wish) {
        ConfigurationSection section = getWishesSection();
        String wishName = wish.getName();
        return section.getBoolean(wishName);
    }
}
