/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.file;

import com.djrapitops.genie.Genie;
import com.djrapitops.javaplugin.utilities.BenchmarkUtil;
import com.djrapitops.javaplugin.utilities.FormattingUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;

/**
 *
 * @author Risto
 */
public class WishLog {

    private final Genie plugin;

    public WishLog(Genie plugin) {
        this.plugin = plugin;
    }

    public void madeAWish(Player p, String wish) {
        String timeStamp = "[" + FormattingUtils.formatTimeStampSecond(BenchmarkUtil.getTime()) + "] ";
        plugin.getPluginLogger().toLog("Wishlog.txt", timeStamp + p.getName() + wish);
    }
    
    public List<String> getWishesBy(String playername) throws IOException {
        List<String> wishes = Files.lines(new File(plugin.getDataFolder(), "Wishlog.txt").toPath())
                .filter(l -> l.toLowerCase().contains(playername.toLowerCase()))
                .collect(Collectors.toList());
        return wishes;
    } 
}
