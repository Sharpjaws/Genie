package com.djrapitops.genie.listeners;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.MessageType;
import com.djrapitops.genie.Messages;
import com.djrapitops.genie.Settings;
import com.djrapitops.genie.lamp.Lamp;
import com.djrapitops.genie.lamp.LampItem;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.task.runnable.RslRunnable;
import java.util.UUID;
import org.bukkit.ChatColor;
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
        plugin.getRunnableFactory().createNew(new RslRunnable("Wish Event") {
            @Override
            public void run() {
                try {
                    ColorScheme color = plugin.getColorScheme();
                    String lampIDLine = item.getItemMeta().getLore().get(2);
                    UUID lampUUID = LampItem.getLampUUID(lampIDLine);
                    LampManager lampManager = plugin.getLampManager();
                    Lamp lamp = lampManager.getLamp(lampUUID);
                    Messages msg = plugin.getMsg();
                    String prefix = color.getMainColor() + "[Genie] " + color.getSecondColor();
                    if (!lamp.hasWishesLeft()) {
                        player.sendMessage(prefix + msg.getMessage(MessageType.OUT_OF_WISHES));
                        return;
                    }
                    String message = event.getMessage().toLowerCase();

                    if (makeAWish(player, message)) {
                        lampManager.wish(lamp);
                        String wishesLeft = color.getTertiaryColor() + "" + lamp.getWishes() + color.getSecondColor();
                        player.sendMessage(prefix + msg.getMessage(MessageType.WISHES_LEFT).replace("WISHES", wishesLeft));
                        if (plugin.getConfig().getBoolean(Settings.ANNOUNCE_WISH_FULFILL.getPath())) {
                            player.getServer().getOnlinePlayers().forEach(p -> {
                                p.sendMessage(color.getMainColor() + "[Genie] " + ChatColor.GOLD + msg.getMessage(MessageType.FULFILL));
                            });
                        } else {
                            player.sendMessage(color.getMainColor() + "[Genie] " + ChatColor.GOLD + plugin.getMsg().getMessage(MessageType.FULFILL));
                        }
                    } else {
                        player.sendMessage(prefix + plugin.getMsg().getMessage(MessageType.CANNOT_FIND));
                        event.setCancelled(true);
                    }
                } finally {
                    this.cancel();
                }
            }
        }).runTaskAsynchronously();
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
