/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.wishes.mob;

import com.djrapitops.genie.wishes.Wish;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 *
 * @author Rsl1122
 */
public class SpawnMobRidingOnWish extends Wish {

    private final EntityType mobToSpawn;
    private final EntityType mobToStack;

    public SpawnMobRidingOnWish(EntityType mobToSpawn, EntityType mobToStack) {
        super(getProperMobname(mobToStack.name()) + ", on, " + getProperMobname(mobToSpawn.name()));
        this.mobToSpawn = mobToSpawn;
        this.mobToStack = mobToStack;
    }

    private static String getProperMobname(String mobName) {
        String replacedWSpace = mobName.replace("_", " ");
        return replacedWSpace;
    }

    @Override
    public boolean fulfillWish(Player p) {
        Location aboveHead = p.getLocation().add(new Location(p.getWorld(), 0, 2, 0));
        Entity spawned = p.getWorld().spawnEntity(aboveHead, mobToSpawn);
        Entity toStack = p.getWorld().spawnEntity(aboveHead, mobToStack);
        ((LivingEntity) spawned).addPassenger(toStack);
        return true;
    }
}
