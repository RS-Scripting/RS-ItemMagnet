package com.rsscripting.itemmagnet.config;

import com.rsscripting.itemmagnet.RSItemMagnet;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    /*
    |--------------------------------------------------------------------------
    | SETUP
    |--------------------------------------------------------------------------
    */

    public static void setup() {

        RSItemMagnet.getInstance()
                .saveDefaultConfig();

        config =
                RSItemMagnet.getInstance()
                        .getConfig();

    }

    /*
    |--------------------------------------------------------------------------
    | RELOAD
    |--------------------------------------------------------------------------
    */

    public static void reload() {

        RSItemMagnet.getInstance()
                .reloadConfig();

        config =
                RSItemMagnet.getInstance()
                        .getConfig();

    }

    /*
    |--------------------------------------------------------------------------
    | GET CONFIG
    |--------------------------------------------------------------------------
    */

    public static FileConfiguration get() {

        return config;

    }

/*
|--------------------------------------------------------------------------
| SETTINGS
|--------------------------------------------------------------------------
*/

    public static int getDefaultRadius() {

        return config.getInt(
                "default-radius"
        );

    }

    public static int getConversionTimeout() {

        return config.getInt(
                "conversion-timeout"
        );

    }

}