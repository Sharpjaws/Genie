package com.djrapitops.genie.lamp;

import java.util.UUID;

/**
 * Object that represents a lamp item in an inventory.
 *
 * @author Rsl1122
 */
public class Lamp {

    private final UUID owner;
    private int wishes;

    public Lamp(UUID owner) {
        this.owner = owner;
        wishes = 3;
    }

    public Lamp(UUID owner, int wishes) {
        this.owner = owner;
        this.wishes = wishes;
    }
    
    public boolean hasWishesLeft() {
        return wishes > 0;
    }
    
    public void useWish() {
        wishes--;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getWishes() {
        return wishes;
    }
}
