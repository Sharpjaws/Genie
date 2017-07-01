/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.wishes.mob;

import com.djrapitops.genie.wishes.Wish;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Risto
 */
public class ItemWish extends Wish {

    private final Material material;

    public ItemWish(Material material) {
        super(getProperMobname(material.name()));
        this.material = material;
    }

    @Override
    public boolean fulfillWish(Player p) {
        try {
            p.getInventory().addItem(new ItemStack(material, 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getProperMobname(String mobName) {
        String replacedWSpace = mobName.replace("_", " ");
        return replacedWSpace;
    }
}
