package com.djrapitops.genie.file;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.lamp.Lamp;
import com.djrapitops.javaplugin.config.ConfigFile;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class responsible for saving and loading lamp data.
 *
 * @author Rsl1122
 */
public class LampStorage extends ConfigFile {

    public LampStorage(Genie plugin) throws IOException, InvalidConfigurationException {
        super(getStorageFolder(plugin), "lamps");
        FileConfiguration config = getConfig();
        HashMap<String, Serializable> map = new HashMap<>();
        map.put(UUID.randomUUID().toString(), new HashMap<>());
        config.addDefault("Lamps", map);
        config.options().copyDefaults(true);
        save();
    }

    private static File getStorageFolder(Genie plugin) {
        File storage = new File(plugin.getDataFolder(), "storage");
        storage.mkdirs();
        return storage;
    }

    public void addLamp(Lamp lamp) {
        try {
            FileConfiguration config = getConfig();
            ConfigurationSection lampsC = config.getConfigurationSection("Lamps");
            Map<String, Serializable> values = new HashMap<>();
            values.put("wishes", lamp.getWishes());
            String lampIDString = lamp.getLampID().toString();
            if (lampsC == null) {
                config.set("Lamps." + lampIDString, values);
            } else {
                lampsC.set(lampIDString, values);
                config.set("Lamps", lampsC);
            }
            save();
        } catch (IOException ex) {
            Log.toLog(this.getClass().getName(), ex);
        }
    }

    public Map<UUID, Lamp> loadLamps() {
        FileConfiguration config = getConfig();
        ConfigurationSection lampsC = config.getConfigurationSection("Lamps");
        Map<UUID, Lamp> lamps = new HashMap<>();
        if (lampsC != null) {
            Set<String> keys = lampsC.getKeys(false);
            for (String key : keys) {
                int wishes = lampsC.getInt(key + ".wishes");
                UUID lampId = UUID.fromString(key);
                lamps.put(lampId, new Lamp(lampId, wishes));
            }
        }
        return lamps;
    }

    public void wishUsed(Lamp lamp) throws IOException {
        FileConfiguration config = getConfig();
        config.set("Lamps." + lamp.getLampID().toString() + ".wishes", lamp.getWishes());
        save();
    }

    public void genieFreed(Lamp lamp) throws IOException {
        while (lamp.hasWishesLeft()) {
            lamp.useWish();
        }
        lamp.useWish(); // TO -1
        wishUsed(lamp);
    }
}
