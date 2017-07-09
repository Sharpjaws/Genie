package com.djrapitops.genie.command.commands;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.wishes.Wish;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.SubCommand;
import com.djrapitops.javaplugin.command.sender.ISender;
import java.util.Arrays;
import org.bukkit.ChatColor;

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
        ColorScheme color = plugin.getColorScheme();
        final ChatColor mColor = color.getMainColor();
        final ChatColor sColor = color.getSecondColor();
        final String prefix = mColor + "[Genie] " + sColor;

        String wish = getWish(args);
        Wish matchingWish = plugin.getWishManager().getMatchingWish(wish, null);

        if (matchingWish == null) {
            sender.sendMessage(prefix + "Did not match any wish.");
        } else {
            sender.sendMessage(prefix + "Matching wish: " + Arrays.toString(matchingWish.getAliases()));
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
