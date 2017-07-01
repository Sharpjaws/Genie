package com.djrapitops.genie.wishes;

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
        String removed = tags.toLowerCase().replace(", ", "");
        for (String wishPart : wishParts) {
            removed = removed.replaceFirst(wishPart, "");
        }
        removed = removed.replace(" ", "");
        int remaining = removed.length();
        double perc = remaining * 1.0 / tags.length();
        return perc;
    }
}
