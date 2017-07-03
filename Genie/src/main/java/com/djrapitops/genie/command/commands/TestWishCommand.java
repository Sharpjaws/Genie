package com.djrapitops.genie.command.commands;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.wishes.Wish;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.CommandUtils;
import com.djrapitops.javaplugin.command.SubCommand;
import com.djrapitops.javaplugin.command.sender.ISender;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import static org.bukkit.Bukkit.getOnlinePlayers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Command used to test a wish.
 *
 * @author Rsl1122
 */
public class TestWishCommand extends SubCommand {

    private final Genie plugin;

    public TestWishCommand(Genie plugin) {
        super("test, check, testwish", CommandType.CONSOLE_WITH_ARGUMENTS, "genie.admin", "Test a wish", "<wish>");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        String wish = getWish(args);
        Wish matchingWish = plugin.getWishManager().getMatchingWish(wish, null);
        ColorScheme color = plugin.getColorScheme();
        if (matchingWish == null) {
            sender.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "Did not match any wish.");
        } else {
            sender.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "Matching wish: " + Arrays.toString(matchingWish.getAliases()));
        }
        return true;
    }

    private String getWish(String[] args) {
        StringBuilder b = new StringBuilder();
        for (String arg : args) {
            b.append(arg).append(" ");
        }
        return b.toString().trim().toLowerCase();
    }
}
