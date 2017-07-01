package com.djrapitops.genie.lamp;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.file.LampStorage;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;

/**
 * Class for each lamp in the world.
 *
 * @author Rsl1122
 */
public class LampManager {

    private final Genie plugin;
    private final LampStorage store;
    private final Map<UUID, Lamp> lamps;

    public LampManager(Genie plugin, LampStorage store) {
        this.plugin = plugin;
        this.store = store;
        lamps = store.loadLamps();
    }

    private UUID newLampID() {
        UUID uuid = UUID.randomUUID();
        if (lamps.containsKey(uuid)) {
            uuid = newLampID();
        }        
        return uuid;
    }
    
    public void dropNewLamp(Location loc, UUID owner) {
        loc.getWorld().dropItem(loc, plugin.getLampManager().newLamp(owner));
    }
    
    public LampItem newLamp(UUID owner) {
        UUID lampID = newLampID();
        Lamp lamp = new Lamp(owner, lampID);
        addNewLamp(lamp);
        return new LampItem(owner, lampID);
    }
    
    public Lamp getLamp(UUID lampID) {
        return lamps.get(lampID);
    }

    private void addNewLamp(Lamp lamp) {
        lamps.put(lamp.getLampID(), lamp);
        store.addLamp(lamp);
    }
    
    public void wish(Lamp lamp) {
        store.wishUsed(lamp);
    }
}
