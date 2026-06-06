package com.rsscripting.itemmagnet.utils;

import com.rsscripting.itemmagnet.RSItemMagnet;

public class VersionUtils {

    /*
    |--------------------------------------------------------------------------
    | GET INSTALLED VERSION
    |--------------------------------------------------------------------------
    */

    public static String getVersion() {

        return RSItemMagnet.getInstance()
                .getDescription()
                .getVersion();
    }

}