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
    // Order by wish replacement, higher replacement number first.
    public int compare(Wish o1, Wish o2) {
        int matchComparison = Double.compare(o1.getMatchPercentage(wish), o2.getMatchPercentage(wish));
        if (matchComparison == 0) {
            return compareReplaceLength(o1, o2);
        } else {
            return matchComparison;
        }
    }

    // Order by length, longer first.
    private int compareReplaceLength(Wish o1, Wish o2) {
        int lengthComparison = Integer.compare(o2.getAliases().length(), o1.getAliases().length());
        if (lengthComparison == 0) {
            return compareFirstMatchingWord(o1, o2);
        } else {
            return lengthComparison;
        }
    }

    // Order by first match, first first.
    private int compareFirstMatchingWord(Wish o1, Wish o2) {
        String[] wishParts = wish.split(" ");
        String wishOne = o1.getAliases().toLowerCase().replace(", ", "");
        String wishTwo = o2.getAliases().toLowerCase().replace(", ", "");
        for (String part : wishParts) {
            int indexOfOne = wishOne.indexOf(part);
            int indexOfTwo = wishTwo.indexOf(part);

            if (indexOfOne == -1 && indexOfTwo == -1 || indexOfOne == indexOfTwo) {
                continue;
            }

            return Integer.compare(indexOfOne, indexOfTwo);
        }
        return 0;
    }
}
