/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.command.commands;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.lamp.LampManager;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.CommandUtils;
import com.djrapitops.javaplugin.command.SubCommand;
import com.djrapitops.javaplugin.command.sender.ISender;
import com.djrapitops.javaplugin.utilities.UUIDFetcher;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import static org.bukkit.Bukkit.getPlayer;

/**
 * Command used to give a player the lamp.
 *
 * @author Rsl1122
 */
public class GiveLampCommand extends SubCommand {

    private final Genie plugin;

    public GiveLampCommand(Genie plugin) {
        super("give, givelamp, lamp, g", CommandType.CONSOLE_WITH_ARGUMENTS, "genie.admin", "Gives the lamp to user or given player", "[player]");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        LampManager lampManager = plugin.getLampManager();
        Player reciever = getReciever(args, sender);
        if (reciever == null) {
            sender.sendMessage(ChatColor.RED + "Player not found. (Is the player online? Name is case sensitive!)");
            return true;
        }
        int wishes = 3;
        for (String arg : args) {
            try {
                wishes = Integer.parseInt(arg);
            } catch (Throwable e) {
                // Use 3 if not found.
            }
        }
        lampManager.dropLamp(reciever.getLocation(), lampManager.newLamp(wishes));
        sender.sendMessage(ChatColor.GREEN + "[Genie] Lamp Dropped!");
        return true;
    }

    private Player getReciever(String[] args, ISender sender) {
        Player reciever;
        if (args.length == 0 && CommandUtils.isPlayer(sender)) {
            reciever = (Player) sender.getSender();
        } else {
            UUID uuid = null;
            try {
                uuid = UUIDFetcher.getUUIDOf(args[0]);
            } catch (Exception ex) {
            }
            if (uuid == null) {
                if (CommandUtils.isPlayer(sender)) {
                    return (Player) sender.getSender();
                }
                return null;
            }
            reciever = getPlayer(uuid);
        }
        return reciever;
    }
}
