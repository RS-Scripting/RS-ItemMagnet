package com.rsscripting.itemmagnet.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class DatabaseManager {

    private static JavaPlugin plugin;

    private static String databaseUrl;

    public static void initialize(
            JavaPlugin pluginInstance
    ) {

        plugin = pluginInstance;

        createDatabase();

        createTables();

    }

    private static void createDatabase() {

        try {

            File databaseFile =
                    new File(
                            plugin.getDataFolder(),
                            "database.db"
                    );

            if (!plugin.getDataFolder().exists()) {

                if (!plugin.getDataFolder().mkdirs()) {

                    plugin.getLogger().severe(
                            "Failed to create plugin data folder."
                    );

                    return;

                }

            }

            databaseUrl =
                    "jdbc:sqlite:"
                            + databaseFile.getAbsolutePath();

            Connection connection =
                    DriverManager.getConnection(
                            databaseUrl
                    );

            connection.close();

            plugin.getLogger().info(
                    "SQLite database initialized."
            );

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to initialize database.",
                    e
            );

        }

    }

    private static void createTables() {

        try (

                Connection connection =
                        DriverManager.getConnection(
                                databaseUrl
                        )

        ) {

            connection.createStatement().execute(
                    """
                    CREATE TABLE IF NOT EXISTS machines (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        world TEXT NOT NULL,
                        x INTEGER NOT NULL,
                        y INTEGER NOT NULL,
                        z INTEGER NOT NULL,
                        owner_uuid TEXT,
                        owner_name TEXT,
                        machine_type TEXT,
                        target_world TEXT,
                        target_x INTEGER,
                        target_y INTEGER,
                        target_z INTEGER,
                        radius INTEGER DEFAULT 5,
                        paused INTEGER DEFAULT 0,
                        status TEXT DEFAULT 'RUNNING',
                        filter_mode TEXT DEFAULT 'ALLOW_ALL',
                        created_at TEXT
                    )
                    """
            );

            connection.createStatement().execute(
                    """
                    CREATE TABLE IF NOT EXISTS machine_filters (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        machine_id INTEGER NOT NULL,
                        page INTEGER NOT NULL,
                        slot INTEGER NOT NULL,
                        item_data TEXT NOT NULL
                    )
                    """
            );

            plugin.getLogger().info(
                    "Database tables initialized."
            );

            try {

                connection.createStatement().execute(
                        """
                        ALTER TABLE machines
                        ADD COLUMN status TEXT DEFAULT 'RUNNING'
                        """
                );

            }
            catch (Exception ignored) {

            }

        }

        catch (Exception e) {

            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to create tables.",
                    e
            );

        }

    }
    public static Connection getConnection()
            throws Exception {

        return DriverManager.getConnection(
                databaseUrl
        );

    }


}