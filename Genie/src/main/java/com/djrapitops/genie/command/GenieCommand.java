package com.djrapitops.genie.command;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.command.commands.*;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.StatusCommand;
import com.djrapitops.javaplugin.command.TreeCommand;

/**
 * Genie admin command
 *
 * @author Rsl1122
 */
public class GenieCommand extends TreeCommand<Genie> {

    public GenieCommand(Genie plugin) {
        super(plugin, "genie", CommandType.CONSOLE, "genie.admin", "", "genie");
    }

    @Override
    public void addCommands() {
        commands.add(new GiveLampCommand(plugin));
        commands.add(new TestWishCommand(plugin));
        commands.add(new WishlogCommand(plugin));
        commands.add(new StatusCommand<>(plugin, this.getPermission()));
    }

}
