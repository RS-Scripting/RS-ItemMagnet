package com.rsscripting.itemmagnet.gui;

import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.utils.RSMenuUtils;
import com.rsscripting.itemmagnet.managers.MachineManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RSMainMenu {

    public static final String MENU_TITLE = "Main";

    public static void open(
            Player player,
            Block block
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "MAIN"
                        ),
                        18,
                        Component.text(
                                MENU_TITLE
                        ).color(
                                NamedTextColor.BLUE
                        )
                );

        /*
        |--------------------------------------------------------------------------
        | FILLER
        |--------------------------------------------------------------------------
        */

        ItemStack filler =
                RSMenuUtils.createFillerPane();

        for (int i = 0; i < menu.getSize(); i++) {

            menu.setItem(i, filler);

        }

        /*
        |--------------------------------------------------------------------------
        | CONVERT BACK
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                2,
                RSMenuUtils.createMenuItem(
                        Material.COMPOSTER,
                        "§6Convert Back"
                )
        );

/*
|--------------------------------------------------------------------------
| PAUSE / RESUME
|--------------------------------------------------------------------------
*/

        boolean paused =
                MachineManager.isPaused(
                        block
                );

        String status =
                MachineManager.getStoredStatus(
                        block
                );

        String displayStatus;

        switch (status) {

            case "RUNNING" ->

                    displayStatus =
                            "Running";

            case "PAUSED" ->

                    displayStatus =
                            "Paused";

            case "ERROR_TARGET_MISSING" ->

                    displayStatus =
                            "Target Container Missing";

            case "ERROR_TARGET_INVALID" ->

                    displayStatus =
                            "Invalid Target Container";

            case "ERROR_NO_TARGET" ->

                    displayStatus =
                            "No Target Container Assigned";

            default ->

                    displayStatus =
                            status;

        }

        menu.setItem(
                6,
                RSMenuUtils.createMenuItem(

                        paused
                                ? Material.RED_DYE
                                : Material.GREEN_DYE,

                        paused
                                ? "§cPaused"
                                : "§aRunning",

                        List.of(
                                "§7Status: §f"
                                        + displayStatus
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | RADIUS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                11,
                RSMenuUtils.createMenuItem(

                        Material.COMPASS,

                        "§bRadius",

                        List.of(
                                "§7Current Radius: §f"
                                        + MachineManager.getRadius(block)
                        )

                )
        );

/*
|--------------------------------------------------------------------------
| TARGET CONTAINER
|--------------------------------------------------------------------------
*/

        int[] targetContainer =
                MachineManager.getTargetContainer(
                        block
                );

        if (targetContainer == null) {

            menu.setItem(
                    13,
                    RSMenuUtils.createMenuItem(

                            Material.TARGET,

                            "§eTarget Container",

                            List.of(
                                    "§cNo target Container assigned.",
                                    "",
                                    "§7Click to assign",
                                    "§7a target Container."
                            )

                    )
            );

        }

        else {

            menu.setItem(
                    13,
                    RSMenuUtils.createMenuItem(

                            Material.TARGET,

                            "§eTarget Container",

                            List.of(
                                    "§7Current Container:",
                                    " " + targetContainer[0],
                                    " " + targetContainer[1],
                                    " " + targetContainer[2],
                                    "",
                                    "§7Click to assign",
                                    "§7a new target Container."
                            )

                    )
            );

        }

        /*
        |--------------------------------------------------------------------------
        | FILTER
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                15,
                RSMenuUtils.createMenuItem(

                        Material.HOPPER,

                        "§eFilter",

                        List.of(
                                "§7Mode: §f"
                                        + MachineManager.getFilterMode(block)
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | ADMIN
        |--------------------------------------------------------------------------
        */

        if (player.hasPermission(
                RSConstants.ADMIN_PERMISSION
        )) {

            menu.setItem(
                    4,
                    RSMenuUtils.createMenuItem(
                            Material.COMMAND_BLOCK,
                            "§cAdmin"
                    )
            );

        }

        player.openInventory(menu);

    }

}