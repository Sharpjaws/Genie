package com.djrapitops.genie.lamp;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Rsl1122
 */
public class LampItem extends ItemStack {

    public LampItem(UUID lampID) {
        super(Material.GOLD_INGOT);
        ItemMeta meta = this.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("Genie Lamp");
        meta.setLore(Arrays.asList(new String[]{
            "Hold to make a wish", "Rub to summon Genie"
        }));
        setItemMeta(meta);
        super.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1);
    }

    public static boolean isLampItem(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore.size() < 2 || !"Hold to make a wish".equals(lore.get(0)) || !"Rub to summon Genie".equals(lore.get(1))) {
            return false;
        }
        if (item.getEnchantments().get(Enchantment.PROTECTION_FALL) == null) {
            return false;
        }
        return true;
    }
}
