/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie;

/**
 * @author Rsl1122
 */
public enum Settings {
    DEBUG("Settings.Debug"),
    WORLD_BLACKLIST("Settings.BlacklistedWorlds");

    private final String path;

    private Settings(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
