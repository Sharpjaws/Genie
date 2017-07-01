/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.command;

import com.djrapitops.genie.Genie;
import com.djrapitops.javaplugin.command.CommandType;
import com.djrapitops.javaplugin.command.TreeCommand;

/**
 * Genie admin command
 * @author Rsl1122
 */
public class GenieCommand extends TreeCommand<Genie>{

    public GenieCommand(Genie plugin) {
        super(plugin, "genie", CommandType.CONSOLE, "genie.admin", "", "/genie");
    }

    @Override
    public void addCommands() {
        
    }
    
}
