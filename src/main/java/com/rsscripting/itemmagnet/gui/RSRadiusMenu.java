package com.rsscripting.itemmagnet.gui;

import com.rsscripting.itemmagnet.managers.MachineManager;
import com.rsscripting.itemmagnet.utils.RSMenuUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RSRadiusMenu {


    public static void open(
            Player player,
            Block block
    ) {

        Inventory menu =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "RADIUS"
                        ),
                        18,
                        Component.text(
                                "Radius: "
                                        + MachineManager.getRadius(
                                        block
                                )
                        ).color(NamedTextColor.BLUE)
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
| BACK
|--------------------------------------------------------------------------
*/

        menu.setItem(
                4,
                RSMenuUtils.createMenuItem(

                        Material.OAK_DOOR,

                        "§eBack"

                )
        );

        /*
        |--------------------------------------------------------------------------
        | CURRENT RADIUS
        |--------------------------------------------------------------------------
        */

        menu.setItem(
                13,
                RSMenuUtils.createMenuItem(

                        Material.COMPASS,

                        "§bCurrent Radius",

                        List.of(
                                "§7Radius: §f"
                                        + MachineManager.getRadius(
                                        block
                                )
                        )

                )
        );

        /*
        |--------------------------------------------------------------------------
        | NEGATIVE BUTTONS
        |--------------------------------------------------------------------------
        */

        ItemStack minusTen =
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-10"
                );

        minusTen.setAmount(10);

        menu.setItem(
                10,
                minusTen
        );

        ItemStack minusFive =
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-5"
                );

        minusFive.setAmount(5);

        menu.setItem(
                11,
                minusFive
        );

        menu.setItem(
                12,
                RSMenuUtils.createMenuItem(
                        Material.RED_WOOL,
                        "§c-1"
                )
        );

        /*
        |--------------------------------------------------------------------------
        | POSITIVE BUTTONS
        |--------------------------------------------------------------------------
        */

        ItemStack plusOne =
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+1"
                );

        plusOne.setAmount(1);

        menu.setItem(
                14,
                plusOne
        );

        ItemStack plusFive =
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+5"
                );

        plusFive.setAmount(5);

        menu.setItem(
                15,
                plusFive
        );

        ItemStack plusTen =
                RSMenuUtils.createMenuItem(
                        Material.GREEN_WOOL,
                        "§a+10"
                );

        plusTen.setAmount(10);

        menu.setItem(
                16,
                plusTen
        );

        player.openInventory(menu);

    }

}