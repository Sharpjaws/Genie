/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.wishes;

import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.wishes.mob.SpawnMobWish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author Rsl1122
 */
public class WishParser {

    private final List<Wish> wishes;
    private final WishLog log;

    /**
     *
     * @param log
     */
    public WishParser(WishLog log) {
        this.log = log;
        wishes = new ArrayList<>();
        addWishes();
    }

    private void addWishes() {
        List<EntityType> prevented = Arrays.asList(new EntityType[]{
            EntityType.AREA_EFFECT_CLOUD, EntityType.ARMOR_STAND, EntityType.COMPLEX_PART,
            EntityType.DRAGON_FIREBALL, EntityType.DROPPED_ITEM, EntityType.EGG,
            EntityType.ENDER_PEARL, EntityType.ENDER_SIGNAL, EntityType.EXPERIENCE_ORB,
            EntityType.FALLING_BLOCK, EntityType.FIREBALL, EntityType.FIREWORK,
            EntityType.FISHING_HOOK, EntityType.EVOKER_FANGS, EntityType.ITEM_FRAME,
            EntityType.LEASH_HITCH, EntityType.LIGHTNING, EntityType.LINGERING_POTION,
            EntityType.LLAMA_SPIT, EntityType.MINECART, EntityType.MINECART_CHEST,
            EntityType.MINECART_COMMAND, EntityType.MINECART_FURNACE, EntityType.MINECART_HOPPER,
            EntityType.MINECART_MOB_SPAWNER, EntityType.MINECART_TNT, EntityType.PAINTING,
            EntityType.PLAYER, EntityType.PRIMED_TNT, EntityType.SHULKER_BULLET,
            EntityType.THROWN_EXP_BOTTLE, EntityType.TIPPED_ARROW, EntityType.UNKNOWN,
            EntityType.WEATHER, EntityType.WITHER_SKULL
        });
        for (EntityType entity : EntityType.values()) {
            if (!prevented.contains(entity)) {
                wishes.add(new SpawnMobWish(entity));
            }
        }
    }

    /**
     *
     * @param p
     * @param wish
     * @return Could the wish be fulfilled?
     */
    public boolean wish(Player p, String wish) {
        log.madeAWish(p, wish);
        return false;
    }

}
