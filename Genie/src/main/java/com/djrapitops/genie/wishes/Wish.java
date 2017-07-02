package com.djrapitops.genie.wishes;

import org.bukkit.entity.Player;

/**
 * Abstract class representing a possible wish.
 *
 * @author Rsl1122
 */
public abstract class Wish {

    private final String[] aliases;

    public Wish(String... name) {
        this.aliases = new String[name.length];
        for (int i = 0; i < name.length; i++) {
            aliases[i] = name[i].toLowerCase().trim();
        }
    }

    public abstract boolean fulfillWish(Player p);

    public String getName() {
        return aliases[0].split(", ")[0];
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getBestMatch(String wish) {
        double perc = 2.0;
        int aliasN = 0;
        for (int i = 0; i < aliases.length; i++) {
            double aliasPerc = getRelativeDiffPercentage(aliases[i], wish);
            if (aliasPerc < perc) {
                aliasN = i;
                perc = aliasPerc;
            }
        }
        return aliases[aliasN];
    }

    @Deprecated
    public double getRelativeDifferencePercentage(String wish) {
        double perc = 2.0;
        for (int i = 0; i < aliases.length; i++) {
            double aliasPerc = getRelativeDiffPercentage(aliases[i], wish);
            if (aliasPerc < perc) {
                perc = aliasPerc;
            }
        }
        return perc;
    }

    public double getRelativeDiffPercentage(String alias, String wish) {
        String[] wishParts = wish.split(" ");
        String removed = alias.toLowerCase().replace(", ", "");
        for (String wishPart : wishParts) {
            removed = removed.replaceFirst(wishPart, "");
        }
        removed = removed.replace(" ", "");
        int remaining = removed.length();
        double aliasPerc = remaining * 1.0 / alias.replace(", ", "").length();
        return aliasPerc;
    }
}
