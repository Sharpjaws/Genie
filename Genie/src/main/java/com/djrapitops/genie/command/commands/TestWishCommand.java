package com.djrapitops.genie.command.commands;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.wishes.Wish;
import com.djrapitops.plugin.command.CommandType;
import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.settings.ColorScheme;
import java.util.Arrays;

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
        final String mColor = color.getMainColor();
        final String sColor = color.getSecondaryColor();
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
