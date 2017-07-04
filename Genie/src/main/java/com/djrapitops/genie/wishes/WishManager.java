package com.djrapitops.genie.wishes;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.file.WishConfigSectionHandler;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.wishes.item.*;
import com.djrapitops.genie.wishes.mob.*;
import com.djrapitops.genie.wishes.other.FlyingWish;
import com.djrapitops.genie.wishes.potion.PotionEffectWish;
import com.djrapitops.genie.wishes.teleport.TeleportHereWish;
import com.djrapitops.genie.wishes.teleport.TeleportToBedWish;
import com.djrapitops.genie.wishes.teleport.TeleportToWish;
import com.djrapitops.genie.wishes.world.*;
import com.djrapitops.javaplugin.task.RslBukkitRunnable;
import com.djrapitops.javaplugin.task.RslRunnable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Rsl1122
 */
public class WishManager {

    private final Genie plugin;
    private final List<Wish> wishes;
    private final WishLog log;
    private final WishConfigSectionHandler configSection;

    /**
     *
     * @param plugin
     */
    public WishManager(Genie plugin) {
        this.plugin = plugin;
        this.log = plugin.getWishLog();
        this.configSection = plugin.getWishConfigSectionHandler();
        wishes = new ArrayList<>();
        addWishes();
    }

    private void addWishes() {
        List<Wish> toAdd = new ArrayList<>();
        addMobWishes(toAdd);
        addItemWishes(toAdd);
        addPotionWishes(toAdd);
        // addEnchantWishes(toAdd); // Can't combine unsafe books in an anvil
        toAdd.add(new AnimalWish());
        toAdd.add(new FarmWish());
        toAdd.add(new FoodWish());
        toAdd.add(new ArmorWish("CHAINMAIL"));
        toAdd.add(new ArmorWish("LEATHER"));
        toAdd.add(new ArmorWish("DIAMOND"));
        toAdd.add(new ArmorWish("IRON"));
        toAdd.add(new ArmorWish("GOLD"));
        toAdd.add(new DogWish());
        toAdd.add(new DogTreatWish());
        toAdd.add(new CatWish());
        toAdd.add(new CatTreatWish());
        toAdd.add(new SunnyWish());
        toAdd.add(new ThunderWish());
        toAdd.add(new ExplosionWish());
        toAdd.add(new TeleportHereWish());
        toAdd.add(new TeleportToBedWish());
        toAdd.add(new TeleportToWish());
        toAdd.add(new AssasinWish());
        toAdd.add(new FlyingWish());
        toAdd.add(new DayWish());
        toAdd.add(new NightWish());
        Collections.sort(toAdd, new WishComparator());
        for (Wish wish : toAdd) {
            addWish(wish);
        }
        Log.info("Initialized with " + wishes.size() + " wishes");
        plugin.processStatus().setStatus("Wishes", wishes.size() + "");
    }

    // Can't combine unsafe books in an anvil
    private void addEnchantWishes(List<Wish> toAdd) {
        for (Enchantment enchant : Enchantment.values()) {
            for (int i = 1; i <= 10; i++) {
                toAdd.add(new EnchantmentWish(enchant, "" + i));
            }
            for (String romanLevel : "I,II,III,IV,V,VI,VII,VIII,IIX,IX,X".split(",")) {
                toAdd.add(new EnchantmentWish(enchant, romanLevel));
            }
        }
    }

    private void addPotionWishes(List<Wish> toAdd) {
        List<PotionEffectType> prevented = getPreventedPotions();
        for (PotionEffectType type : PotionEffectType.values()) {
            if (prevented.contains(type)) {
                continue;
            }
            toAdd.add(new PotionEffectWish(type));
        }
    }

    private void addItemWishes(List<Wish> toAdd) {
        List<Material> preventedMats = getPreventedItems();
        for (Material material : Material.values()) {
            if (!preventedMats.contains(material)) {
                toAdd.add(new ItemWish(material));
            }
        }
    }

    private List<Wish> addMobWishes(List<Wish> toAdd) {
        List<EntityType> prevented = getPreventedEntities();
        for (EntityType mob : EntityType.values()) {
            if (!prevented.contains(mob)) {
                toAdd.add(new SpawnMobWish(mob));
            }
            for (EntityType stackMob : EntityType.values()) {
                if (!prevented.contains(mob) && !prevented.contains(stackMob)) {
                    toAdd.add(new SpawnMobRidingOnWish(mob, stackMob));
                }
            }
        }
        return toAdd;
    }

    // TODO Older version support
    public List<PotionEffectType> getPreventedPotions() {
        List<PotionEffectType> prevented = Arrays.asList(new PotionEffectType[]{
            PotionEffectType.WITHER, PotionEffectType.HEAL,
            null
        });
        return prevented;
    }

    // TODO Older version support
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
            EntityType.SPLASH_POTION, EntityType.SMALL_FIREBALL, EntityType.ENDER_DRAGON,
            null
        });
        return prevented;
    }

    // TODO Older version support
    public List<Material> getPreventedItems() {
        List<Material> prevented = Arrays.asList(new Material[]{
            Material.ACACIA_DOOR, Material.BEDROCK, Material.AIR,
            Material.BED_BLOCK, Material.BEETROOT_BLOCK, Material.BIRCH_DOOR,
            Material.BREWING_STAND, Material.BURNING_FURNACE, Material.CAKE_BLOCK,
            Material.CARROT, Material.COMMAND, Material.CAULDRON,
            Material.COMMAND, Material.COMMAND_CHAIN, Material.COMMAND_REPEATING,
            Material.COMMAND_MINECART, Material.DIODE_BLOCK_ON, Material.DARK_OAK_DOOR,
            Material.DAYLIGHT_DETECTOR_INVERTED, Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF,
            Material.DOUBLE_PLANT, Material.DOUBLE_STONE_SLAB2, Material.EMPTY_MAP,
            Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME, Material.FIREWORK_CHARGE,
            Material.FLOWER_POT, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2,
            Material.IRON_DOOR_BLOCK, Material.JUNGLE_DOOR, Material.KNOWLEDGE_BOOK,
            Material.MONSTER_EGG, Material.MONSTER_EGGS, Material.PISTON_BASE,
            Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE, Material.PISTON_STICKY_BASE,
            Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_LAMP_ON, Material.REDSTONE_TORCH_OFF,
            Material.SIGN_POST, Material.SKULL, Material.SKULL_ITEM,
            Material.SNOW, Material.SPRUCE_DOOR, Material.SUGAR_CANE_BLOCK,
            Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.STANDING_BANNER,
            Material.STATIONARY_WATER, Material.STATIONARY_LAVA, Material.TIPPED_ARROW,
            Material.TRIPWIRE, Material.WALL_SIGN, Material.WATER, Material.WATER_LILY,
            Material.LAVA, Material.WRITTEN_BOOK, Material.WALL_BANNER, Material.POTATO,
            null});
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
        String[] parts = wish.split(" with ");
        Set<Wish> matches = new HashSet<>();
        int i = 0;
        for (String part : parts) {
            if (i >= 2) {
                break;
            }
            Wish match = getMatchingWish(part.trim(), p);
            if (match != null) {
                matches.add(match);
            }
            i++;
        }
        if (!matches.isEmpty()) {
            plugin.getRunnableFactory().createNew(new RslRunnable("Wish Fulfillment Task") {
                @Override
                public void run() {
                    try {
                        for (Wish wish : matches) {
                            wish.fulfillWish(p);
                        }
                    } finally {
                        this.cancel();
                    }
                }
            }).runTask();
            return true;
        }
        return false;
    }

    public Wish getMatchingWish(String wish, Player p) {
        List<Wish> matches = new ArrayList<>(wishes);

        String parsedWish = removeCommonWords(wish);
        Collections.sort(matches, new WishMatchComparator(parsedWish));

        sendDebugMessages(wish, parsedWish, matches);

        Wish match = matches.get(0);
        String bestMatch = match.getBestMatch(parsedWish);

        // Places UUID of target to the storage of the wish.
        if (match instanceof PlayerSpecificWish) {
            PlayerSpecificWish pSpecMatch = (PlayerSpecificWish) match;
            if (p != null) {
                pSpecMatch.placeInStore(p.getUniqueId(), pSpecMatch.getUUIDForPlayerInMatch(match.getAliases(), bestMatch));
            }
        }

        double percentage = match.getRelativeDiffPercentage(bestMatch, parsedWish); // Lower is better
        if (percentage > 0.6) {
            return null;
        }
        return match;
    }

    private void sendDebugMessages(String wish, String parsedWish, List<Wish> matches) {
        Log.debug("Wish: " + wish + " | Parsed: " + parsedWish + " | Top 5:");
        int i = 0;
        for (Wish match : matches) {
            if (i > 4) {
                break;
            }
            String bestMatch = match.getBestMatch(parsedWish);
            Log.debug(bestMatch + ": " + match.getRelativeDiffPercentage(bestMatch, parsedWish));
            i++;
        }
    }

    private String removeCommonWords(String wish) {
        String[] commonWords = new String[]{
            "i", "you", "him", "her", "a", "the", "had", "wish", "get", "set",
            "be", "of", "and", "in", "that", "have", "it", "for", "as", "do",
            "from", "an", "this", "but", "his", "by", "they", "we", "say", "her", "or",
            "will", "my", "all", "would", "their", "there", "what", "so", "up", "out", "if",
            "about", "who", "which", "go", "me", "when", "make", "can", "like", "time", "no",
            "just", "him", "know", "take", "people", "into", "year", "your", "good", "some",
            "could", "them", "see", "other", "than", "then", "now", "look", "only", /*"come",*/
            "its", "over", "think", "also", "back", "after", "use", "our", "work", "first",
            "well", "way", "even", "new", "want", "because", "any", "these", "give", "was",
            "most", "us"};
        String parsed = wish;
        for (String word : commonWords) {
            parsed = parsed.replace(" " + word + " ", " ");
            // Remove if first word
            if (parsed.charAt(0) == word.charAt(0)) {
                parsed = parsed.replace(word + " ", " ");
            }
            // Remove if last word
            if (parsed.charAt(parsed.length() - 1) == word.charAt(word.length() - 1)) {
                parsed = parsed.replace(" " + word, " ");
            }
        }
        return parsed.trim();
    }
}
