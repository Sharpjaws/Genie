package com.djrapitops.genie;

import com.djrapitops.genie.wishes.Wish;
import org.bukkit.entity.Player;

public class API {

    private final Genie plugin;

    public API(Genie plugin) {
        this.plugin = plugin;
    }

    public void addWish(Wish customWish) {
        plugin.getWishManager().addWish(customWish);
    }

    public Wish getMatchingWish(String wish, Player player) {
        return plugin.getWishManager().getMatchingWish(wish, player);
    }

}
