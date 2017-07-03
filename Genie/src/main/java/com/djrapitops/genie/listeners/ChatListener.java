package com.djrapitops.genie.listeners;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Settings;
import com.djrapitops.genie.lamp.Lamp;
import com.djrapitops.genie.lamp.LampItem;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.api.TimeAmount;
import com.djrapitops.javaplugin.task.RslBukkitRunnable;
import com.djrapitops.javaplugin.utilities.BenchmarkUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
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
    

    public ChatListener(Genie plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();        
        if (!plugin.isWorldAllowed(player.getWorld())) {
            return;
        }
        ItemStack item = getItemInhand(player);
        if (item == null || !LampItem.isLampItem(item)) {
            return;
        }
        new RslBukkitRunnable<Genie>("Wish Event") {
            @Override
            public void run() {
                try {
                    ColorScheme color = plugin.getColorScheme();
                    String lampIDLine = item.getItemMeta().getLore().get(2);
                    UUID lampUUID = LampItem.getLampUUID(lampIDLine);
                    LampManager lampManager = plugin.getLampManager();
                    Lamp lamp = lampManager.getLamp(lampUUID);
                    if (!lamp.hasWishesLeft()) {
                        player.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "This lamp has no wishes left.");
                        return;
                    }
                    String message = event.getMessage().toLowerCase();

                    if (makeAWish(player, message)) {
                        lampManager.wish(lamp);
                        player.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor()
                                + "You have " + color.getTertiaryColor() + lamp.getWishes() + color.getSecondColor() + " wishes left.");
                        player.getServer().getOnlinePlayers().forEach(p -> {
                            p.sendMessage(color.getMainColor() + "[Genie] " + ChatColor.GOLD + "Has fulfilled your wish!");
                        });
                    } else {
                        player.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "Sorry, I do not know how to fulfill your wish");
                    }
                } finally {
                    this.cancel();
                }
            }
        }.runTaskAsynchronously();
    }

    private ItemStack getItemInhand(Player player) {
        ItemStack item = null;
        try {
            item = player.getInventory().getItemInMainHand();
        } catch (Throwable e) {
            try {
                item = player.getInventory().getItemInHand(); // Old version support
            } catch (Throwable e2) {
            }
        }
        return item;
    }

    private boolean makeAWish(Player player, String message) {
        return plugin.getWishManager().wish(player, message);
    }
}
