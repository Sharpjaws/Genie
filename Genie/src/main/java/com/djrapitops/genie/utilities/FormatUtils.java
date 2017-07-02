/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.genie.utilities;

/**
 *
 * @author Risto
 */
public class FormatUtils {

    public static String[] getProperName(String enumName) {
        String name = enumName.replace("_ITEM", "");
        String replacedWSpace = name.replace("_", " ");
        String replacedWNone = name.replace("_", "");
        return new String[]{replacedWSpace, replacedWNone};
    }
}
