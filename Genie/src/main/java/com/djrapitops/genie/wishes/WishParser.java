package com.djrapitops.genie.wishes;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.file.WishConfigSectionHandler;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.wishes.mob.FarmWish;
import com.djrapitops.genie.wishes.mob.SpawnMobWish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author Rsl1122
 */
public class WishParser {

    private final Genie plugin;
    private final List<Wish> wishes;
    private final WishLog log;
    private final WishConfigSectionHandler configSection;

    /**
     *
     * @param plugin
     */
    public WishParser(Genie plugin) {
        this.plugin = plugin;
        this.log = plugin.getWishLog();
        this.configSection = plugin.getWishConfigSectionHandler();
        wishes = new ArrayList<>();
        addWishes();
    }

    private void addWishes() {
        List<Wish> toAdd = new ArrayList<>();
        List<EntityType> prevented = getPreventedEntities();
        for (EntityType entity : EntityType.values()) {
            if (!prevented.contains(entity)) {
                toAdd.add(new SpawnMobWish(entity));
            }
        }
        toAdd.add(new FarmWish());
        Collections.sort(toAdd, new WishComparator());
        for (Wish wish : toAdd) {
            addWish(wish);
        }
        Log.info("Initialized with " + wishes.size() + " wishes");
        plugin.processStatus().setStatus("Wishes", wishes.size() + "");
    }

    public List<EntityType> getPreventedEntities() {
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
            EntityType.WEATHER, EntityType.WITHER_SKULL, EntityType.ARROW, EntityType.BOAT
        });
        return prevented;
    }

    private void addWish(Wish wish) {
        if (!configSection.exists(wish)) {
            configSection.createSection(wish);
        }
        if (configSection.isAllowed(wish)) {
            wishes.add(wish);
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
