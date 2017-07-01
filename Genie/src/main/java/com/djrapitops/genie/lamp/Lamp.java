package com.djrapitops.genie.lamp;

import java.util.UUID;

/**
 * Object that represents a lamp item in an inventory.
 *
 * @author Rsl1122
 */
public class Lamp {

    private final UUID owner;
    private final UUID lampID;
    private int wishes;

    public Lamp(UUID owner, UUID lampID) {
        this.owner = owner;
        this.lampID = lampID;
        wishes = 3;
    }

    public Lamp(UUID owner, UUID lampID, int wishes) {
        this.owner = owner;
        this.lampID = lampID;
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

    public UUID getLampID() {
        return lampID;
    }
}
