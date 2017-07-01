/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.wishes.mob;

import com.djrapitops.genie.wishes.Wish;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author Risto
 */
public class FarmWish extends Wish {

    private final List<Wish> mobWishes;

    public FarmWish() {
        super("Farm, Animals");
        mobWishes = new ArrayList<>();
        addWishes();
    }

    @Override
    public boolean fulfillWish(Player p) {
        for (Wish mobWish : mobWishes) {
            for (int i = 0; i < 2; i++) {
                mobWish.fulfillWish(p);
            }
        }
        return true;
    }

    private void addWishes() {
        EntityType[] e = new EntityType[]{
            EntityType.COW,
            EntityType.CHICKEN,
            EntityType.PIG,
            EntityType.SHEEP};
        for (EntityType farmAnimal : e) {
            mobWishes.add(new SpawnMobWish(farmAnimal));
        }
    }
}
