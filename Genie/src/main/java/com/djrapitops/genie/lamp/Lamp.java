package com.djrapitops.genie.lamp;

import java.util.UUID;

/**
 * Object that represents a lamp item in an inventory.
 *
 * @author Rsl1122
 */
public class Lamp {

    private final UUID lampID;
    private int wishes;

    public Lamp(UUID lampID) {
        this.lampID = lampID;
        wishes = 3;
    }

    public Lamp(UUID lampID, int wishes) {
        this.lampID = lampID;
        this.wishes = wishes;
    }
    
    public boolean hasWishesLeft() {
        return wishes > 0;
    }
    
    public void useWish() {
        wishes--;
    }

    public int getWishes() {
        return wishes;
    }

    public UUID getLampID() {
        return lampID;
    }
}
