package com.rsscripting.itemmagnet.listeners;

import com.rsscripting.itemmagnet.config.ConfigManager;
import com.rsscripting.itemmagnet.enums.MachineStatus;
import com.rsscripting.itemmagnet.managers.MachineManager;
import com.rsscripting.itemmagnet.managers.RSConversionManager;
import com.rsscripting.itemmagnet.gui.*;
import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.utils.RSMessageUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RSMenuListener
        implements Listener {

    private final RSConvertListener
            conversionListener;

    public RSMenuListener(
            RSConvertListener conversionListener
    ) {

        this.conversionListener =
                conversionListener;

    }

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        Player player =
                (Player) event.getWhoClicked();

        /*
        |--------------------------------------------------------------------------
        | CONVERSION MENU
        |--------------------------------------------------------------------------
        */

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "CONVERT"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            if (event.getRawSlot() == 4) {

                Block block =
                        conversionListener.getSelectedBlock(
                                player
                        );

                if (block == null){

                    player.closeInventory();

                    return;

                }

                RSConversionManager.startPendingConversion(
                        player,
                        block
                );

                RSMessageUtils.info(

                        player,

                        "Please select a Container within "
                                + ConfigManager.getDefaultRadius()
                                + " blocks to finish conversion."

                );

                player.closeInventory();

            }

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | MAIN MENU
        |--------------------------------------------------------------------------
        */

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "MAIN"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            Block block =
                    conversionListener.getSelectedBlock(
                            player
                    );

            if (block == null) {

                player.closeInventory();

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | CONVERT BACK
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 2) {

                conversionListener.convertBack(
                        player
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | PAUSE / RESUME
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 6) {

                boolean paused =
                        MachineManager.isPaused(
                                block
                        );

                if (paused) {

                    MachineStatus detectedStatus =
                            MachineManager.getDetectedStatus(
                                    block
                            );

                    if (
                            detectedStatus
                                    != MachineStatus.PAUSED
                                    &&
                                    detectedStatus
                                            != MachineStatus.RUNNING
                    ) {

                        String line1;
                        String line2;

                        switch (detectedStatus) {

                            case NO_TARGET -> {

                                line1 =
                                        "No target container assigned.";

                                line2 =
                                        "Please assign a valid Hopper, Chest, or Barrel and press resume.";

                            }

                            case TARGET_MISSING -> {

                                line1 =
                                        "Target container is missing.";

                                line2 =
                                        "Assign a new container and press resume.";

                            }

                            case TARGET_INVALID -> {

                                line1 =
                                        "Target container is invalid.";

                                line2 =
                                        "Please assign a valid Hopper, Chest, or Barrel and press resume.";

                            }

                            default -> {

                                line1 =
                                        "Machine is not ready.";

                                line2 =
                                        "";

                            }

                        }

                        RSMessageUtils.error(
                                player,
                                line1
                        );

                        if (!line2.isEmpty()) {

                            RSMessageUtils.error(
                                    player,
                                    line2
                            );

                        }

                        return;

                    }

                    MachineManager.setPaused(
                            block,
                            false
                    );

                    MachineManager.setStatus(
                            block,
                            "RUNNING"
                    );

                }
                else {

                    MachineManager.setPaused(
                            block,
                            true
                    );

                    MachineManager.setStatus(
                            block,
                            "PAUSED"
                    );

                }

                RSMainMenu.open(
                        player,
                        block
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | RADIUS MENU
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 11) {

                RSRadiusMenu.open(
                        player,
                        block
                );

                return;

            }

            /*
|--------------------------------------------------------------------------
| TARGET CONTAINER
|--------------------------------------------------------------------------
*/

            if (event.getRawSlot() == 13) {

                RSConversionManager.startPendingTargetAssignment(
                        player,
                        block
                );

                RSMessageUtils.info(

                        player,

                        "Please select a new target Container."

                );

                player.closeInventory();

                return;

            }

/*
|--------------------------------------------------------------------------
| FILTER
|--------------------------------------------------------------------------
*/

            if (event.getRawSlot() == 15) {

                RSFilterMenu.open(
                        player,
                        block
                );

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | ADMIN MENU
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 4) {

                if (!player.isOp()
                        ||

                        !player.hasPermission(
                                RSConstants.ADMIN_PERMISSION
                        )) {

                    return;

                }

                RSAdminMenu.open(
                        player
                );

            }

        }

    }

}