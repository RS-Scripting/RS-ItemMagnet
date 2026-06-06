package com.rsscripting.itemmagnet.managers;

import com.rsscripting.itemmagnet.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RSConversionManager {

    private static final Map<UUID, PendingConversion>
            pendingConversions =
            new HashMap<>();

    private static final Map<UUID, Block>
            pendingTargetAssignments =
            new HashMap<>();

    public static boolean containsFilterItem(
            Block block,
            ItemStack item
    ) {

        Map<Integer, ItemStack> filterItems =
                MachineManager.getAllFilterItems(
                        block
                );

        for (ItemStack filterItem
                : filterItems.values()) {

            if (filterItem == null) {
                continue;
            }

            if (filterItem.isSimilar(
                    item
            )) {

                return true;

            }

        }

        return false;

    }

    /*
|--------------------------------------------------------------------------
| PENDING CONVERSION
|--------------------------------------------------------------------------
*/

    public static void startPendingConversion(
            Player player,
            Block lodestone
    ) {

        long timeoutMillis =
                ConfigManager.getConversionTimeout()
                        * 1000L;

        pendingConversions.put(

                player.getUniqueId(),

                new PendingConversion(

                        lodestone,

                        System.currentTimeMillis()
                                + timeoutMillis

                )

        );

    }

    public static PendingConversion getPendingConversion(
            Player player
    ) {

        return pendingConversions.get(
                player.getUniqueId()
        );

    }

    public static void removePendingConversion(
            Player player
    ) {

        pendingConversions.remove(
                player.getUniqueId()
        );

    }

    /*
|--------------------------------------------------------------------------
| PENDING TARGET ASSIGNMENT
|--------------------------------------------------------------------------
*/

    public static void startPendingTargetAssignment(
            Player player,
            Block lodestone
    ) {

        pendingTargetAssignments.put(
                player.getUniqueId(),
                lodestone
        );

    }

    public static Block getPendingTargetAssignment(
            Player player
    ) {

        return pendingTargetAssignments.get(
                player.getUniqueId()
        );

    }

    public static void removePendingTargetAssignment(
            Player player
    ) {

        pendingTargetAssignments.remove(
                player.getUniqueId()
        );

    }

}