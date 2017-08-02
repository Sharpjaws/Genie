package com.djrapitops.genie.file;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.plugin.config.BukkitConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class responsible for saving and loading lamp data.
 *
 * @author Rsl1122
 */
public class UnfulfilledWishStorage extends BukkitConfig {

    private final List<String> wishes;

    public UnfulfilledWishStorage(Genie plugin) throws IOException, InvalidConfigurationException {
        super(getStorageFolder(plugin), "unfulfilled");
        FileConfiguration config = getConfig();
        copyDefaults(config);
        wishes = loadWishes();
        if (wishes.size() >= 250) {
            Log.info("Genie/storage/unfulfilled.yml has "+wishes.size()+" unfulfilled wishes, please send them to the developer");
        }
    }

    private void copyDefaults(FileConfiguration config) throws IOException {
        List<String> defaults = new ArrayList<>();
        defaults.add("Default");
        config.addDefault("UnfulfilledWishes", defaults);
        config.options().copyDefaults(true);
        save();
    }

    private static File getStorageFolder(Genie plugin) {
        File storage = new File(plugin.getDataFolder(), "storage");
        storage.mkdirs();
        return storage;
    }

    public void addWish(String wish) {
        if (wishes.contains(wish)) {
            return;
        }
        wishes.add(wish);
        try {
            saveWishes(wishes);
        } catch (IOException ex) {
            Log.toLog(this.getClass().getName(), ex);
        }
    }

    public final List<String> loadWishes() {
        return getConfig().getStringList("UnfulfilledWishes");
    }

    private void saveWishes(List<String> wishes) throws IOException {
        FileConfiguration config = getConfig();
        config.set("UnfulfilledWishes", wishes);
        save();
    }
}
