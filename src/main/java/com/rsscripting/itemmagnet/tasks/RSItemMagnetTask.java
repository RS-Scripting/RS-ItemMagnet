package com.rsscripting.itemmagnet.tasks;

import com.rsscripting.itemmagnet.enums.MachineStatus;
import com.rsscripting.itemmagnet.managers.MachineManager;
import com.rsscripting.itemmagnet.managers.RSConversionManager;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Item;
import org.bukkit.block.Block;
import org.bukkit.Particle;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RSItemMagnetTask
        extends BukkitRunnable {

    @Override
    public void run() {

        Map<Location, Integer> machines =
                MachineManager.getAllMachines();

        for (Location machineLocation
                : machines.keySet()) {

            if (
                    MachineManager.isPaused(
                            machineLocation.getBlock()
                    )
            ) {

                continue;

            }

            MachineStatus detectedStatus =
                    MachineManager.getDetectedStatus(
                            machineLocation.getBlock()
                    );

            if (
                    detectedStatus
                            == MachineStatus.TARGET_MISSING
            ) {

                MachineManager.setPaused(
                        machineLocation.getBlock(),
                        true
                );

                MachineManager.setStatus(
                        machineLocation.getBlock(),
                        "ERROR_TARGET_MISSING"
                );

                continue;

            }

            if (
                    detectedStatus
                            == MachineStatus.TARGET_INVALID
            ) {

                MachineManager.setPaused(
                        machineLocation.getBlock(),
                        true
                );

                MachineManager.setStatus(
                        machineLocation.getBlock(),
                        "ERROR_TARGET_INVALID"
                );

                continue;

            }

            if (
                    detectedStatus
                            == MachineStatus.NO_TARGET
            ) {

                MachineManager.setPaused(
                        machineLocation.getBlock(),
                        true
                );

                MachineManager.setStatus(
                        machineLocation.getBlock(),
                        "ERROR_NO_TARGET"
                );

                continue;

            }

            int radius =
                    MachineManager.getRadius(
                            machineLocation.getBlock()
                    );

            Collection<Item> nearbyItems =
                    machineLocation.getWorld()
                            .getNearbyEntitiesByType(
                                    Item.class,
                                    machineLocation,
                                    radius
                            );

            int[] containerCoordinates =
                    MachineManager.getTargetContainer(
                            machineLocation.getBlock()
                    );

            if (containerCoordinates == null) {
                continue;
            }

            Location containerLocation =
                    new Location(

                            machineLocation.getWorld(),

                            containerCoordinates[0] + 0.5,
                            containerCoordinates[1] + 0.5,
                            containerCoordinates[2] + 0.5

                    );

            for (Item item : nearbyItems) {

                if (item.getTicksLived() < 40) {
                    continue;
                }

                String filterMode =
                        MachineManager.getFilterMode(
                                machineLocation.getBlock()
                        );

                boolean inFilter =
                        RSConversionManager.containsFilterItem(
                                machineLocation.getBlock(),
                                item.getItemStack()
                        );

                if (filterMode.equalsIgnoreCase(
                        "WHITELIST"
                ) && !inFilter) {

                    continue;

                }

                if (filterMode.equalsIgnoreCase(
                        "BLACKLIST"
                ) && inFilter) {

                    continue;

                }

                Block containerBlock =
                        containerLocation.getBlock();

                if (!(containerBlock.getState()
                        instanceof InventoryHolder container)) {

                    continue;

                }

                Inventory inventory =
                        container.getInventory();

                ItemStack stack =
                        item.getItemStack();

                HashMap<Integer, ItemStack> leftovers =
                        inventory.addItem(stack);

                if (leftovers.isEmpty()) {

                    item.getWorld().spawnParticle(
                            Particle.ELECTRIC_SPARK,
                            item.getLocation(),
                            10,
                            0.2,
                            0.2,
                            0.2,
                            0.05
                    );

                    item.remove();

                }
                else {

                    ItemStack remaining =
                            leftovers.values()
                                    .iterator()
                                    .next();

                    item.setItemStack(
                            remaining
                    );

                }

            }

        }

    }

}