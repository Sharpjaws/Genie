package com.djrapitops.genie.wishes;

import java.util.Comparator;

/**
 * Sorts the wishes with the lowest remaining characters after wish words were
 * removed.
 *
 * @author Rsl1122
 */
public class WishMatchComparator implements Comparator<Wish> {

    private final String wish;

    public WishMatchComparator(String wish) {
        this.wish = wish;
    }

    @Override
    public int compare(Wish o1, Wish o2) {
        return Double.compare(o1.getMatchPercentage(wish), o2.getMatchPercentage(wish));
    }
}
