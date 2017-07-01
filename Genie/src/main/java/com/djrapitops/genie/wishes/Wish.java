package com.djrapitops.genie.wishes;

import com.djrapitops.genie.Log;
import org.bukkit.entity.Player;

/**
 * Abstract class representing a possible wish.
 *
 * @author Rsl1122
 */
public abstract class Wish {

    private final String tags;

    public Wish(String name) {
        this.tags = name;
    }

    public abstract boolean fulfillWish(Player p);

    public String getName() {
        return tags.split(", ")[0];
    }

    public String getAliases() {
        return tags;
    }

    public double getMatchPercentage(String wish) {
        String[] wishParts = wish.split(" ");
        String removed = tags.toLowerCase();
        for (String wishPart : wishParts) {
            removed = removed.replace(wishPart, "");
        }
        double perc = removed.length() * 1.0 / tags.length();        
        return perc;
    }
}
