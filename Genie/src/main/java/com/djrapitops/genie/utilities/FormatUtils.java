/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.utilities;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Risto
 */
public class FormatUtils {

    public static String[] getProperName(String enumName) {
        String name = enumName.replace("_ITEM", "");
        String replacedWSpace = name.replace("_", " ");
        String replacedWNone = name.replace("_", "");
        return new String[]{replacedWSpace, replacedWNone};
    }

    public static String[] getPotionNames(PotionEffectType type) {
        Map<PotionEffectType, String[]> names = new HashMap<>();
        names.put(PotionEffectType.BLINDNESS, new String[]{"Blindness", "Blind"});
        names.put(PotionEffectType.CONFUSION, new String[]{"Confusion", "Confused"});
        names.put(PotionEffectType.DAMAGE_RESISTANCE, new String[]{"Tough", "Damage Resistance", "Damage resistant"});
        names.put(PotionEffectType.FAST_DIGGING, new String[]{"Fast digging", "dig fast"});
        names.put(PotionEffectType.FIRE_RESISTANCE, new String[]{"FIRE RESISTANCE", "Fire resistant"});
        names.put(PotionEffectType.HARM, new String[]{"Harmed", "Hurt", "Harm"});
        names.put(PotionEffectType.HEAL, new String[]{"Healed", "Heal", "Healing"});
        names.put(PotionEffectType.HEALTH_BOOST, new String[]{"More, health", "health, boost"});
        names.put(PotionEffectType.HUNGER, new String[]{"Hungry", "Hunger"});
        names.put(PotionEffectType.INCREASE_DAMAGE, new String[]{"Increase Damage", "Increased Damage", "Strength", "Power", "Strong"});
        names.put(PotionEffectType.INVISIBILITY, new String[]{"Invisibility", "Invisible"});
        names.put(PotionEffectType.JUMP, new String[]{"Jump", "Jump higher"});
        names.put(PotionEffectType.LEVITATION, new String[]{"Levitating", "Floating", "Levitate", "Float", "Levitation"});
        names.put(PotionEffectType.LUCK, new String[]{"Lucky", "Luck"});
        names.put(PotionEffectType.NIGHT_VISION, new String[]{"NIGHT VISION", "See dark"});
        names.put(PotionEffectType.SATURATION, new String[]{"not hungry"});
        names.put(PotionEffectType.SPEED, new String[]{"Speed", "Faster", "fast", "Sanic", "Sonic"});
        names.put(PotionEffectType.WATER_BREATHING, new String[]{"WATER BREATHING", "Breath, underwater"});
        names.put(PotionEffectType.REGENERATION, new String[]{"Regeneration", "Regen, Health", "Regenerating"});
        String[] name = names.get(type);
        if (name == null) {
            name = getProperName(type.getName());
        }
        return name;
    }
    public static String[] getEnchantmentName(Enchantment e, String level) {
        Map<Enchantment, String[]> names = new HashMap<>();
        names.put(Enchantment.ARROW_DAMAGE, new String[]{"Arrow damage", "Power", "Bow Damage"});
        names.put(Enchantment.ARROW_FIRE, new String[]{"Arrow fire", "Flame", "Bow Fire", "Firearrow"});
        names.put(Enchantment.ARROW_KNOCKBACK, new String[]{"Arrow knockback", "Punch"});
        names.put(Enchantment.ARROW_INFINITE, new String[]{"Arrow infinite", "infinity", "Infinite arrows"});
        names.put(Enchantment.DAMAGE_ALL, new String[]{"all damage", "sharpness", "more damage"});
        names.put(Enchantment.DAMAGE_ARTHROPODS, new String[]{"damage arthopods", "bane arthopods", "spider damage"});
        names.put(Enchantment.DAMAGE_UNDEAD, new String[]{"damage undead", "smite", "zombie damage", "skeleton damage"});
        names.put(Enchantment.DURABILITY, new String[]{"durability", "unbreaking"});
        names.put(Enchantment.DIG_SPEED, new String[]{"dig faster", "dig speed", "efficiency"});
        names.put(Enchantment.FIRE_ASPECT, new String[]{"fire aspect", "flame sword"});
        names.put(Enchantment.KNOCKBACK, new String[]{"knockback", "sword punch"});
        names.put(Enchantment.LOOT_BONUS_BLOCKS, new String[]{"fortune", "mining luck"});
        names.put(Enchantment.LOOT_BONUS_MOBS, new String[]{"looting", "loot luck", "more loot"});
        names.put(Enchantment.PROTECTION_EXPLOSIONS, new String[]{"explosion protection", "blast protection"});
        names.put(Enchantment.PROTECTION_ENVIRONMENTAL, new String[]{"protection"});
        names.put(Enchantment.PROTECTION_FALL, new String[]{"fall protection", "feather fall", "feather falling"});
        names.put(Enchantment.PROTECTION_PROJECTILE, new String[]{"arrow protection", "arrows protection", "projectile protection"});
        names.put(Enchantment.PROTECTION_FIRE, new String[]{"fire protection", "fire immunity"});
        names.put(Enchantment.SILK_TOUCH, new String[]{"silk touch"});
        names.put(Enchantment.WATER_WORKER, new String[]{"aqua affinity", "water worker", "work underwater"});
        names.put(Enchantment.DEPTH_STRIDER, new String[]{"water speed", "depth strider", "water walker"});
        String[] name = names.get(e);
        if (name == null) {
            name = getProperName(e.getName());
        }
        for (int i = 0; i < name.length; i++) {
            name[i] = name[i] + " " + level;
        }
        return name;
    }

    public static int getLevel(String level) {
        switch (level) {
            case "X":
                return 10;
            case "IX":
                return 9;
            case "IIX":
            case "VIII":
                return 8;
            case "VII":
                return 7;
            case "VI":
                return 6;
            case "V":
                return 5;
            case "IV":
                return 4;
            case "III":
                return 3;
            case "II":
                return 2;
            case "I":
                return 1;
            default:
                break;
        }
        try {
            int i = Integer.parseInt(level);
            return i;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
