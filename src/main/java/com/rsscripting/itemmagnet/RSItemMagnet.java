package com.rsscripting.itemmagnet;

import com.rsscripting.itemmagnet.commands.RSPluginCommand;
import com.rsscripting.itemmagnet.commands.RSTabCompleter;
import com.rsscripting.itemmagnet.config.ConfigManager;
import com.rsscripting.itemmagnet.database.DatabaseManager;
import com.rsscripting.itemmagnet.listeners.*;
import com.rsscripting.itemmagnet.tasks.RSErrorTask;
import com.rsscripting.itemmagnet.utils.GitHubUpdateChecker;
import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.tasks.RSItemMagnetTask;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RSItemMagnet
        extends JavaPlugin {

    private static RSItemMagnet instance;

    @Override
    public void onEnable() {

        saveDefaultConfig();


        instance = this;

        /*
        |--------------------------------------------------------------------------
        | CONFIG
        |--------------------------------------------------------------------------
        */

        ConfigManager.setup();

         /*
        |--------------------------------------------------------------------------
        | Database
        |--------------------------------------------------------------------------
        */

        DatabaseManager.initialize(
                this
        );

        /*
        |--------------------------------------------------------------------------
        | LISTENERS
        |--------------------------------------------------------------------------
        */

        /*  Convert */
        RSConvertListener
                conversionListener =
                new RSConvertListener();

        getServer().getPluginManager().registerEvents(

                conversionListener,

                this

        );

        /*  Main Menu */
        getServer().getPluginManager().registerEvents(

                new RSMenuListener(
                        conversionListener
                ),

                this

        );

        /*  Radius */
        RSRadiusListener radiusListener =
                new RSRadiusListener(
                        conversionListener
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        radiusListener,
                        this
                );

        /*  Admin */
        RSAdminListener adminListener =
                new RSAdminListener(
                        conversionListener
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        adminListener,
                        this
                );

        /*  Filter */
        RSFilterListener filterListener =
                new RSFilterListener(
                        conversionListener
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        filterListener,
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new RSBlockBreakListener(),
                        this
                );

        /*
        |--------------------------------------------------------------------------
        | COMMANDS
        |--------------------------------------------------------------------------
        */

        PluginCommand command =
                getCommand(
                        RSConstants.COMMAND_ALIAS
                );

        if (command != null) {

            command.setExecutor(
                    new RSPluginCommand()
            );

            command.setTabCompleter(
                    new RSTabCompleter()
            );

        }

        /*
        |--------------------------------------------------------------------------
        | UPDATE CHECKER
        |--------------------------------------------------------------------------
        */

        GitHubUpdateChecker.checkForUpdates();

        /*
        |--------------------------------------------------------------------------
        | ENABLE MESSAGE
        |--------------------------------------------------------------------------
        */

        getLogger().info(
                RSConstants.PLUGIN_NAME
                        + " Enabled"
        );

        new RSItemMagnetTask().runTaskTimer(

                this,

                20L,

                20L

        );

        new RSErrorTask().runTaskTimer(

                this,

                100L,

                100L

        );

    }

    @Override
    public void onDisable() {

        getLogger().info(
                RSConstants.PLUGIN_NAME
                        + " Disabled"
        );

    }

    /*
    |--------------------------------------------------------------------------
    | GET INSTANCE
    |--------------------------------------------------------------------------
    */

    public static RSItemMagnet getInstance() {

        return instance;

    }

}