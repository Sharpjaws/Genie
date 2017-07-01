package com.djrapitops.genie.wishes;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.file.WishConfigSectionHandler;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.wishes.mob.FarmWish;
import com.djrapitops.genie.wishes.mob.SpawnMobRidingOnWish;
import com.djrapitops.genie.wishes.mob.SpawnMobWish;
import com.djrapitops.javaplugin.task.RslBukkitRunnable;
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
        for (EntityType mob : EntityType.values()) {
            if (!prevented.contains(mob)) {
                toAdd.add(new SpawnMobWish(mob));
            }
            for (EntityType stackMob : EntityType.values()) {
                if (!prevented.contains(mob)) {
                    toAdd.add(new SpawnMobRidingOnWish(mob, stackMob));
                }
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
            EntityType.WEATHER, EntityType.WITHER_SKULL, EntityType.ARROW, EntityType.BOAT, 
            EntityType.SPLASH_POTION, EntityType.SMALL_FIREBALL
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
        Wish match = getMatchingWish(wish);
        if (match != null) {
            new RslBukkitRunnable<Genie>("WishFulfillmentTask") {
                @Override
                public void run() {
                    try {
                        match.fulfillWish(p);
                    } finally {
                        this.cancel();
                    }
                }
            }.runTask();
            return true;
        }
        return false;
    }

    public Wish getMatchingWish(String wish) {
        List<Wish> matches = new ArrayList<>(wishes);
        //remove
        Log.debug("Wish: " + wish);
        String parsedWish = removeCommonWords(wish);
        Log.debug("Parsed wish: " + parsedWish);
        for (Wish match : matches) {
            Log.debug(match.getAliases() + ": " + match.getMatchPercentage(parsedWish));
        }
        Collections.sort(matches, new WishMatchComparator(parsedWish));
        Wish match = matches.get(0);
        double percentage = match.getMatchPercentage(parsedWish); // Lower is better
        Log.debug("Wish: " + wish + " | Match: " + match.getAliases() + " | Remain: " + percentage);
        if (percentage > 0.6) {
            return null;
        }
        return match;
    }

    private String removeCommonWords(String wish) {
        String[] commonWords = new String[]{
            "i", "you", "him", "her", "a", "the", "had", "wish", "get", "set",
            "be", "of", "and", "in", "that", "have", "it", "for", "not", "as", "do",
            "from", "an", "this", "but", "his", "by", "they", "we", "say", "her", "or",
            "will", "my", "all", "would", "their", "there", "what", "so", "up", "out", "if",
            "about", "who", "which", "go", "me", "when", "make", "can", "like", "time", "no",
            "just", "him", "know", "take", "people", "into", "year", "your", "good", "some",
            "could", "them", "see", "other", "than", "then", "now", "look", "only", "come",
            "its", "over", "think", "also", "back", "after", "use", "our", "work", "first",
            "well", "way", "even", "new", "want", "because", "any", "these", "give", "day",
            "most", "us"};
        String parsed = wish.replace("to", "teleportto");
        parsed = parsed.replace("here", "teleporthere");
        for (String word : commonWords) {
            parsed = parsed.replace(" " + word + " ", " ");
            parsed = parsed.replace("" + word + " ", " ");
        }
        return parsed;
    }
}
