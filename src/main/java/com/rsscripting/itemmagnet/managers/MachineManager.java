package com.rsscripting.itemmagnet.managers;

import com.rsscripting.itemmagnet.database.DatabaseManager;
import com.rsscripting.itemmagnet.enums.MachineStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class MachineManager {

    public static boolean machineExists(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            return result.next();

        }

        catch (Exception e) {

            logError(
                    "Failed to check if machine exists.",
                    e
            );

            return false;

        }

    }

    public static void insertMachine(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                INSERT INTO machines (
                                    world,
                                    x,
                                    y,
                                    z,
                                    created_at
                                )
                                VALUES (
                                    ?, ?, ?, ?, datetime('now')
                                )
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to insert machine.",
                    e
            );

        }

    }

    public static void deleteMachine(
            Block block
    ) {

        int machineId =
                getMachineId(
                        block
                );

        try (

                Connection connection =
                        DatabaseManager.getConnection()

        ) {

            if (machineId != -1) {

                try (

                        PreparedStatement filterStatement =
                                connection.prepareStatement(
                                        """
                                        DELETE FROM machine_filters
                                        WHERE machine_id = ?
                                        """
                                )

                ) {

                    filterStatement.setInt(
                            1,
                            machineId
                    );

                    filterStatement.executeUpdate();

                }

            }

            try (

                    PreparedStatement machineStatement =
                            connection.prepareStatement(
                                    """
                                    DELETE FROM machines
                                    WHERE world = ?
                                    AND x = ?
                                    AND y = ?
                                    AND z = ?
                                    """
                            )

            ) {

                machineStatement.setString(
                        1,
                        block.getWorld().getName()
                );

                machineStatement.setInt(
                        2,
                        block.getX()
                );

                machineStatement.setInt(
                        3,
                        block.getY()
                );

                machineStatement.setInt(
                        4,
                        block.getZ()
                );

                machineStatement.executeUpdate();

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to delete machine.",
                    e
            );

        }

    }

    public static void setOwner(
            Block block,
            Player player
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET owner_uuid = ?,
                                    owner_name = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    player.getUniqueId().toString()
            );

            statement.setString(
                    2,
                    player.getName()
            );

            statement.setString(
                    3,
                    block.getWorld().getName()
            );

            statement.setInt(
                    4,
                    block.getX()
            );

            statement.setInt(
                    5,
                    block.getY()
            );

            statement.setInt(
                    6,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to set machine owner.",
                    e
            );

        }

    }

    public static boolean isOwner(
            Block block,
            Player player
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT owner_uuid
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (!result.next()) {

                return false;

            }

            String ownerUuid =
                    result.getString(
                            "owner_uuid"
                    );

            if (ownerUuid == null) {

                return false;

            }

            return UUID.fromString(
                    ownerUuid
            ).equals(
                    player.getUniqueId()
            );

        }

        catch (Exception e) {

            logError(
                    "Failed to check machine ownership.",
                    e
            );

            return false;

        }

    }

    public static void setRadius(
            Block block,
            int radius
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET radius = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    radius
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to set machine radius.",
                    e
            );

        }

    }

    public static int getRadius(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT radius
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(
                        "radius"
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to get machine radius.",
                    e
            );

        }

        return 5;

    }

/*
|--------------------------------------------------------------------------
| TARGET CONTAINER
|--------------------------------------------------------------------------
*/

    public static void setTargetContainer(
            Block machine,
            Block container
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET target_world = ?,
                                    target_x = ?,
                                    target_y = ?,
                                    target_z = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    container.getWorld().getName()
            );

            statement.setInt(
                    2,
                    container.getX()
            );

            statement.setInt(
                    3,
                    container.getY()
            );

            statement.setInt(
                    4,
                    container.getZ()
            );

            statement.setString(
                    5,
                    machine.getWorld().getName()
            );

            statement.setInt(
                    6,
                    machine.getX()
            );

            statement.setInt(
                    7,
                    machine.getY()
            );

            statement.setInt(
                    8,
                    machine.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to set target container.",
                    e
            );

        }

    }

    public static int[] getTargetContainer(
            Block machine
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT target_x,
                                       target_y,
                                       target_z
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    machine.getWorld().getName()
            );

            statement.setInt(
                    2,
                    machine.getX()
            );

            statement.setInt(
                    3,
                    machine.getY()
            );

            statement.setInt(
                    4,
                    machine.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return new int[] {

                        result.getInt(
                                "target_x"
                        ),

                        result.getInt(
                                "target_y"
                        ),

                        result.getInt(
                                "target_z"
                        )

                };

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to get target container.",
                    e
            );

        }

        return null;

    }

    public static void setPaused(
            Block block,
            boolean paused
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET paused = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setBoolean(
                    1,
                    paused
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to update machine status.",
                    e
            );

        }

    }

    public static void setStatus(
            Block block,
            String status
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET status = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    status
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to set machine status.",
                    e
            );

        }

    }

    public static boolean isPaused(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT paused
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getBoolean(
                        "paused"
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to check machine pause state.",
                    e
            );

        }

        return false;

    }

    public static String getStoredStatus(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT status
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getString(
                        "status"
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to get stored machine status.",
                    e
            );

        }

        return "RUNNING";

    }

    /*
|--------------------------------------------------------------------------
| ALL MACHINES
|--------------------------------------------------------------------------
*/

    public static Map<Location, Integer> getAllMachines() {

        Map<Location, Integer> machines =
                new HashMap<>();

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id,
                                       world,
                                       x,
                                       y,
                                       z
                                FROM machines
                                """
                        )

        ) {

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                String worldName =
                        result.getString(
                                "world"
                        );

                if (Bukkit.getWorld(
                        worldName
                ) == null) {

                    continue;

                }

                Location location =
                        new Location(

                                Bukkit.getWorld(
                                        worldName
                                ),

                                result.getInt(
                                        "x"
                                ),

                                result.getInt(
                                        "y"
                                ),

                                result.getInt(
                                        "z"
                                )

                        );

                machines.put(

                        location,

                        result.getInt(
                                "id"
                        )

                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to load machines.",
                    e
            );

        }

        return machines;

    }

    public static void setFilterMode(
            Block block,
            String filterMode
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                UPDATE machines
                                SET filter_mode = ?
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                """
                        )

        ) {

            statement.setString(
                    1,
                    filterMode
            );

            statement.setString(
                    2,
                    block.getWorld().getName()
            );

            statement.setInt(
                    3,
                    block.getX()
            );

            statement.setInt(
                    4,
                    block.getY()
            );

            statement.setInt(
                    5,
                    block.getZ()
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to set filter mode.",
                    e
            );

        }

    }

    public static String getFilterMode(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT filter_mode
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getString(
                        "filter_mode"
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to get filter mode.",
                    e
            );

        }

        return "ALLOW_ALL";

    }

    public static int getMachineId(
            Block block
    ) {

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT id
                                FROM machines
                                WHERE world = ?
                                AND x = ?
                                AND y = ?
                                AND z = ?
                                LIMIT 1
                                """
                        )

        ) {

            statement.setString(
                    1,
                    block.getWorld().getName()
            );

            statement.setInt(
                    2,
                    block.getX()
            );

            statement.setInt(
                    3,
                    block.getY()
            );

            statement.setInt(
                    4,
                    block.getZ()
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(
                        "id"
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to get machine id.",
                    e
            );

        }

        return -1;

    }

    public static void removeFilterItem(
            Block block,
            int page,
            int slot
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                DELETE FROM machine_filters
                                WHERE machine_id = ?
                                AND page = ?
                                AND slot = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            statement.setInt(
                    3,
                    slot
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to remove filter item.",
                    e
            );

        }

    }

    public static String serializeItemStack(
            ItemStack itemStack
    ) {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            BukkitObjectOutputStream dataOutput =
                    new BukkitObjectOutputStream(
                            outputStream
                    );

            dataOutput.writeObject(
                    itemStack
            );

            dataOutput.close();

            return Base64.getEncoder()
                    .encodeToString(
                            outputStream.toByteArray()
                    );

        }

        catch (IOException e) {

            logError(
                    "Failed to serialize item stack.",
                    e
            );

        }

        return null;

    }

    public static void saveFilterItem(
            Block block,
            int page,
            int slot,
            ItemStack itemStack
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return;

        }

        removeFilterItem(
                block,
                page,
                slot
        );

        String serializedItem =
                serializeItemStack(
                        itemStack
                );

        if (serializedItem == null) {

            return;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                INSERT INTO machine_filters
                                (
                                    machine_id,
                                    page,
                                    slot,
                                    item_data
                                )
                                VALUES
                                (
                                    ?, ?, ?, ?
                                )
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            statement.setInt(
                    3,
                    slot
            );

            statement.setString(
                    4,
                    serializedItem
            );

            statement.executeUpdate();

        }

        catch (Exception e) {

            logError(
                    "Failed to save filter item.",
                    e
            );

        }

    }

    public static ItemStack deserializeItemStack(
            String serializedItem
    ) {

        try {

            byte[] data =
                    Base64.getDecoder()
                            .decode(
                                    serializedItem
                            );

            BukkitObjectInputStream inputStream =
                    new BukkitObjectInputStream(
                            new ByteArrayInputStream(
                                    data
                            )
                    );

            ItemStack itemStack =
                    (ItemStack) inputStream.readObject();

            inputStream.close();

            return itemStack;

        }

        catch (Exception e) {

            logError(
                    "Failed to deserialize item stack.",
                    e
            );

        }

        return null;

    }

    public static Map<Integer, ItemStack> getFilterItems(
            Block block,
            int page
    ) {

        Map<Integer, ItemStack> items =
                new HashMap<>();

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return items;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT slot, item_data
                                FROM machine_filters
                                WHERE machine_id = ?
                                AND page = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            statement.setInt(
                    2,
                    page
            );

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                items.put(

                        result.getInt(
                                "slot"
                        ),

                        deserializeItemStack(
                                result.getString(
                                        "item_data"
                                )
                        )

                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to load filter items.",
                    e
            );

        }

        return items;

    }

    public static Map<Integer, ItemStack> getAllFilterItems(
            Block block
    ) {

        Map<Integer, ItemStack> items =
                new HashMap<>();

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return items;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT page,
                                       slot,
                                       item_data
                                FROM machine_filters
                                WHERE machine_id = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                int page =
                        result.getInt(
                                "page"
                        );

                int slot =
                        result.getInt(
                                "slot"
                        );

                items.put(

                        (page * 1000) + slot,

                        deserializeItemStack(
                                result.getString(
                                        "item_data"
                                )
                        )

                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to retrieve filter items.",
                    e
            );

        }

        return items;

    }

    public static int getHighestFilterPage(
            Block block
    ) {

        int machineId =
                getMachineId(
                        block
                );

        if (machineId == -1) {

            return 1;

        }

        try (

                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                """
                                SELECT MAX(page)
                                FROM machine_filters
                                WHERE machine_id = ?
                                """
                        )

        ) {

            statement.setInt(
                    1,
                    machineId
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                int page =
                        result.getInt(
                                1
                        );

                return Math.max(
                        page,
                        1
                );

            }

        }

        catch (Exception e) {

            logError(
                    "Failed to determine highest filter page.",
                    e
            );

        }

        return 1;

    }

    /*
|--------------------------------------------------------------------------
| CONSOLE ERROR
|--------------------------------------------------------------------------
*/

    public static void logError(
            String message,
            Exception exception
    ) {

        org.bukkit.Bukkit.getLogger().log(

                java.util.logging.Level.SEVERE,

                "[RS-ItemMagnet] " + message,

                exception

        );

    }

    public static MachineStatus getDetectedStatus(
            Block machineBlock
    ) {

        int[] containerCoordinates =
                getTargetContainer(
                        machineBlock
                );

        if (containerCoordinates == null) {

            return MachineStatus.NO_TARGET;

        }

        Block containerBlock =
                machineBlock.getWorld()
                        .getBlockAt(

                                containerCoordinates[0],
                                containerCoordinates[1],
                                containerCoordinates[2]

                        );

        if (containerBlock.getType()
                == Material.AIR) {

            return MachineStatus.TARGET_MISSING;

        }

        Material type =
                containerBlock.getType();

        if (
                type != Material.HOPPER
                        &&
                        type != Material.CHEST
                        &&
                        type != Material.BARREL
        ) {

            return MachineStatus.TARGET_INVALID;

        }

        return MachineStatus.RUNNING;

    }

}