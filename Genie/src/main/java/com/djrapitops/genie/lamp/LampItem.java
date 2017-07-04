package com.djrapitops.genie.lamp;

import com.djrapitops.genie.Log;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Rsl1122
 */
public class LampItem extends ItemStack {

    private static final String LORE1 = ChatColor.RESET + "" + ChatColor.DARK_AQUA + "Hold to make a wish";
    private static final String LORE2 = ChatColor.RESET + "" + ChatColor.DARK_AQUA + "Rub to summon Genie";
    private static final String LORE3 = ChatColor.RESET + "" + ChatColor.COLOR_CHAR + ":";

    public LampItem(UUID lampID) {
        super(Material.GOLD_INGOT);
        ItemMeta meta = this.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("" + ChatColor.RESET + ChatColor.GOLD + "Genie Lamp");
        StringBuilder lore3 = new StringBuilder();
        lore3.append(LORE3);
        for (char c : lampID.toString().toCharArray()) {
            lore3.append(ChatColor.COLOR_CHAR).append(c);
        }
        meta.setLore(Arrays.asList(new String[]{
            LORE1,
            LORE2,
            lore3.toString()
        }));
        setItemMeta(meta);
        super.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1);
    }

    public static boolean isLampItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore.size() < 3 || !LORE1.equals(lore.get(0)) || !LORE2.equals(lore.get(1)) || lore.get(2) == null) {
            return false;
        }
        if (item.getEnchantments().get(Enchantment.PROTECTION_FALL) == null) {
            return false;
        }
        return true;
    }

    public static UUID getLampUUID(String thirdLoreLine) {
        try {
            String uuids = thirdLoreLine.replace(ChatColor.COLOR_CHAR + "", "").split(":")[1];
            return UUID.fromString(uuids);
        } catch (Exception e) {
            Log.toLog("getLampUUID", e);
        }
        return null;
    }
}
