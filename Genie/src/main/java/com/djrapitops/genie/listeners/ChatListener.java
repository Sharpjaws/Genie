package com.djrapitops.genie.listeners;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.Settings;
import com.djrapitops.genie.lamp.LampItem;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.api.TimeAmount;
import com.djrapitops.javaplugin.utilities.BenchmarkUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Rsl1122
 */
public class ChatListener implements Listener {

    private final Genie plugin;
    private long configRead;
    private List<String> blacklistedWorlds;

    public ChatListener(Genie plugin) {
        this.plugin = plugin;
        updateBlacklist();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (configRead < BenchmarkUtil.getTime() - TimeAmount.MINUTE.ms() * 10L) {
            updateBlacklist();
        }

        if (blacklistedWorlds.contains(player.getWorld().getName().toLowerCase())) {
            return;
        }
        ItemStack item = getItemInhand(player);
        if (item == null || !LampItem.isLampItem(item)) {
            return;
        }
        String message = event.getMessage().toLowerCase();
        ColorScheme color = plugin.getColorScheme();
        if (!makeAWish(player, message)) {
            player.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "Sorry, I do not know how to fulfill your wish");
        }
    }

    private ItemStack getItemInhand(Player player) {
        ItemStack item = null;
        try {
            item = player.getInventory().getItemInMainHand();
        } catch (Throwable e) {
            try {
                item = player.getInventory().getItemInHand();
            } catch (Throwable e2) {
            }
        }
        return item;
    }

    private void updateBlacklist() {
        blacklistedWorlds = plugin.getConfig().getStringList(Settings.WORLD_BLACKLIST.getPath())
                .stream().map(l -> l.toLowerCase()).collect(Collectors.toList());
        configRead = BenchmarkUtil.getTime();
    }

    private boolean makeAWish(Player player, String message) {
        Log.debug("Recieved a wish: " + player.getName() + " " + message);
        return plugin.getWishParser().wish(player, message);
    }

}