/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        super(new File(plugin.getDataFolder(), "storage"), "lamps");
        FileConfiguration config = getConfig();
        config.addDefault("Lamps", new HashMap<>());
        save();
    }

    public void addLamp(Lamp lamp) {
        try {
            FileConfiguration config = getConfig();
            ConfigurationSection lampsC = config.getConfigurationSection("Lamps");
            Map<String, Serializable> values = new HashMap<>();
            values.put("wishes", lamp.getWishes());
            values.put("lampID", lamp.getLampID().toString());
            lampsC.set(lamp.getOwner().toString(), values);
            config.set("Lamps", lampsC);
            save();
        } catch (IOException ex) {
            Log.toLog(this.getClass().getName(), ex);
        }
    }

    public Map<UUID, Lamp> loadLamps() {
        FileConfiguration config = getConfig();
        ConfigurationSection lampsC = config.getConfigurationSection("Lamps");
        Map<UUID, Lamp> lamps = new HashMap<>();
        Set<String> keys = lampsC.getKeys(false);
        for (String key : keys) {
            UUID uuid = UUID.fromString(key);
            int wishes = lampsC.getInt(key + ".wishes");
            String lampIdS = lampsC.getString(key + ".lampID");
            UUID lampId = UUID.fromString(lampIdS);
            lamps.put(uuid, new Lamp(uuid, lampId, wishes));
        }
        return lamps;
    }

}
