package com.djrapitops.genie.lamp;

import com.djrapitops.genie.file.LampStorage;
import java.util.Map;
import java.util.UUID;

/**
 * Class for each lamp in the world.
 *
 * @author Rsl1122
 */
public class LampManager {

    private final LampStorage store;
    private final Map<UUID, Lamp> lamps;

    public LampManager(LampStorage store) {
        this.store = store;
        lamps = store.loadLamps();
    }
}
