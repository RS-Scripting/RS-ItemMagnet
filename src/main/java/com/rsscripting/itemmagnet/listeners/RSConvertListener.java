package com.rsscripting.itemmagnet.listeners;

import com.rsscripting.itemmagnet.gui.RSConversionMenu;
import com.rsscripting.itemmagnet.gui.RSMainMenu;
import com.rsscripting.itemmagnet.managers.MachineManager;
import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.utils.RSMessageUtils;
import com.rsscripting.itemmagnet.config.ConfigManager;
import com.rsscripting.itemmagnet.managers.PendingConversion;
import com.rsscripting.itemmagnet.managers.RSConversionManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RSConvertListener
        implements Listener {

    private Block selectedBlock;

    @EventHandler
    public void onPlayerInteractBlock(
            PlayerInteractEvent event
    ) {

        Player player =
                event.getPlayer();

        Block block =
                event.getClickedBlock();

        if (block == null) {
            return;
        }

        /*
|--------------------------------------------------------------------------
| PENDING TARGET ASSIGNMENT
|--------------------------------------------------------------------------
*/

        Block targetAssignmentMachine =
                RSConversionManager.getPendingTargetAssignment(
                        player
                );

        if (targetAssignmentMachine != null) {

            event.setCancelled(true);

            Material type =
                    block.getType();

            if (
                    type != Material.HOPPER
                            &&
                            type != Material.CHEST
                            &&
                            type != Material.BARREL
            ) {

                RSMessageUtils.error(
                        player,
                        "Please select a Hopper, Chest, or Barrel."
                );

                return;

            }

            if (!targetAssignmentMachine.getWorld().equals(
                    block.getWorld()
            )) {

                RSMessageUtils.error(
                        player,
                        "Target container must be in the same world."
                );

                return;

            }

            double maxDistance =
                    ConfigManager.getDefaultRadius();

            if (targetAssignmentMachine
                    .getLocation()
                    .distance(
                            block.getLocation()
                    ) > maxDistance) {

                RSMessageUtils.error(
                        player,
                        "Target container must be within "
                                + maxDistance
                                + " blocks."
                );

                return;

            }

            MachineManager.setTargetContainer(
                    targetAssignmentMachine,
                    block
            );

            RSConversionManager.removePendingTargetAssignment(
                    player
            );

            RSMessageUtils.success(
                    player,
                    "Target container updated."
            );

            return;

        }

/*
|--------------------------------------------------------------------------
| PENDING Conversion CONTAINER SELECTION
|--------------------------------------------------------------------------
*/

        PendingConversion pendingConversion =
                RSConversionManager.getPendingConversion(
                        player
                );

        if (pendingConversion != null) {

            event.setCancelled(true);

            if (pendingConversion.isExpired()) {

                RSConversionManager.removePendingConversion(
                        player
                );

                RSMessageUtils.error(
                        player,
                        "Conversion timed out."
                );

                return;

            }

            Material type =
                    block.getType();

            if (
                    type != Material.HOPPER
                            &&
                            type != Material.CHEST
                            &&
                            type != Material.BARREL
            ) {

                RSMessageUtils.error(
                        player,
                        "Please select a Hopper, Chest, or Barrel."
                );

                return;

            }

            Block lodestone =
                    pendingConversion.getLodestone();

            double maxDistance =
                    ConfigManager.getDefaultRadius();

            if (!lodestone.getWorld().equals(
                    block.getWorld()
            )) {

                RSMessageUtils.error(
                        player,
                        "Target container must be in the same world."
                );

                return;

            }

            if (lodestone.getLocation().distance(
                    block.getLocation()
            ) > maxDistance) {

                RSMessageUtils.error(

                        player,

                        "Target container must be within "
                                + maxDistance
                                + " blocks."

                );

                return;

            }

            MachineManager.insertMachine(
                    lodestone
            );

            MachineManager.setOwner(
                    lodestone,
                    player
            );

            MachineManager.setPaused(
                    lodestone,
                    false
            );

            MachineManager.setStatus(
                    lodestone,
                    "RUNNING"
            );

            MachineManager.setRadius(
                    lodestone,
                    ConfigManager.getDefaultRadius()
            );

            MachineManager.setFilterMode(
                    lodestone,
                    "ALLOW_ALL"
            );

            MachineManager.setTargetContainer(
                    lodestone,
                    block
            );

            RSConversionManager.removePendingConversion(
                    player
            );

            RSMessageUtils.success(
                    player,
                    "Target container assigned."
            );

            RSMainMenu.open(
                    player,
                    lodestone
            );

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | REQUIRE SNEAKING
        |--------------------------------------------------------------------------
        */

        if (!player.isSneaking()) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | VALIDATE TARGET ENTITY
        |--------------------------------------------------------------------------
        */

        if (block.getType() !=
                RSConstants.REQUIRED_TARGET_BLOCK) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | VALIDATE HELD ITEM
        |--------------------------------------------------------------------------
        */

        ItemStack heldItem =
                player.getInventory()
                        .getItemInMainHand();

        if (heldItem.getType() !=
                RSConstants.REQUIRED_HELD_ITEM) {
            return;
        }

        /*
        |--------------------------------------------------------------------------
        | CANCEL VANILLA ACTION
        |--------------------------------------------------------------------------
        */

        event.setCancelled(true);

        /*
        |--------------------------------------------------------------------------
        | STORE SELECTED ENTITY
        |--------------------------------------------------------------------------
        */

        selectedBlock = block;

        /*
|--------------------------------------------------------------------------
| ALREADY CONVERTED
|--------------------------------------------------------------------------
*/

        if (MachineManager.machineExists(
                block
        )) {

    /*
    |--------------------------------------------------------------------------
    | OWNER CHECK
    |--------------------------------------------------------------------------
    */

            if (!MachineManager.isOwner(
                    block,
                    player
            )

                    &&

                    !player.hasPermission(
                            RSConstants.BYPASS_PERMISSION
                    )

            ) {

                RSMessageUtils.error(
                        player,
                        "You are not the owner."
                );

                return;

            }

    /*
    |--------------------------------------------------------------------------
    | OPEN MAIN MENU
    |--------------------------------------------------------------------------
    */

            RSMainMenu.open(
                    player,
                    block
            );

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | OPEN CONVERSION MENU
        |--------------------------------------------------------------------------
        */

        RSConversionMenu.open(player);

    }

    /*
    |--------------------------------------------------------------------------
    | GET SELECTED ENTITY
    |--------------------------------------------------------------------------
    */

    public Block getSelectedBlock() {

        return selectedBlock;

    }

    /*
    |--------------------------------------------------------------------------
    | Convert Back
    |--------------------------------------------------------------------------
    */

    public void convertBack(
            Player player
    ) {

        Block block =
                selectedBlock;

        if (block == null) {

            player.closeInventory();

            return;

        }

        MachineManager.deleteMachine(
                block
        );

        RSMessageUtils.success(
                player,
                "Machine removed."
        );

        player.closeInventory();

    }

}