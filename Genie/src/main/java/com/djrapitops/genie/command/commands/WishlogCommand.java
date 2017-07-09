package com.djrapitops.genie.command.commands;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.Log;
import com.djrapitops.genie.file.WishLog;
import com.djrapitops.genie.utilities.Check;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.SubCommand;
import com.djrapitops.javaplugin.command.sender.ISender;
import com.djrapitops.javaplugin.task.runnable.RslRunnable;
import com.djrapitops.javaplugin.utilities.Verify;
import java.io.IOException;
import java.util.List;
import org.bukkit.ChatColor;

/**
 *
 * @author Rsl1122
 */
public class WishlogCommand extends SubCommand {

    private final Genie plugin;

    public WishlogCommand(Genie plugin) {
        super("wishlog, log, wl", CommandType.CONSOLE_WITH_ARGUMENTS, "genie.admin", "See all wishes made by a player", "<playername>");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        ColorScheme color = plugin.getColorScheme();
        final ChatColor mColor = color.getMainColor();
        final ChatColor sColor = color.getSecondColor();
        final String prefix = mColor + "[Genie] " + sColor;

        if (!Check.isTrue(args.length >= 1, prefix + "Not enough Arguments.", sender)) {
            return true;
        }

        String name = args[0];

        WishLog wishLog = plugin.getWishLog();
        plugin.getRunnableFactory().createNew(new RslRunnable("Wishlog read task") {
            @Override
            public void run() {
                List<String> wishes = null;
                try {
                    wishes = wishLog.getWishesBy(name);
                } catch (IOException ex) {
                    Log.toLog(this.getClass().getName(), ex);
                    sender.sendMessage(ChatColor.RED + "Error occurred: " + ex);
                }
                final boolean notEmpty = !Verify.isEmpty(wishes);

                if (!Check.isTrue(notEmpty, prefix + name + " has 0 wishes logged.", sender)) {
                    return;
                }

                sender.sendMessage(prefix + name + " has " + color.getTertiaryColor() + wishes.size() + sColor + " wishes logged.");
                wishes.stream()
                        .map(logLine -> sColor + logLine)
                        .forEach(msg -> sender.sendMessage(msg));

            }
        }).runTaskAsynchronously();
        return true;
    }
}
