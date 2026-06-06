package com.rsscripting.itemmagnet.listeners;

import com.rsscripting.itemmagnet.config.ConfigManager;
import com.rsscripting.itemmagnet.gui.RSMainMenu;
import com.rsscripting.itemmagnet.gui.RSMenuHolder;
import com.rsscripting.itemmagnet.gui.RSRadiusMenu;
import com.rsscripting.itemmagnet.managers.MachineManager;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RSRadiusListener
        implements Listener {

    private final RSConvertListener
            conversionListener;

    public RSRadiusListener(
            RSConvertListener conversionListener
    ) {

        this.conversionListener =
                conversionListener;

    }

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!(event.getWhoClicked()
                instanceof Player player)) {
            return;
        }

        if (!(event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder)) {
            return;
        }

        if (!holder.getMenuId()
                .equals(
                        "RADIUS"
                )) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        Block block =
                conversionListener.getSelectedBlock();

        if (block == null) {

            player.closeInventory();

            return;

        }

                /*
        |--------------------------------------------------------------------------
        | BACK
        |--------------------------------------------------------------------------
        */

        if (event.getRawSlot() == 4) {

            RSMainMenu.open(
                    player,
                    block
            );

            return;

        }

        int radius =
                MachineManager.getRadius(
                        block
                );

        int change;

        switch (event.getRawSlot()) {

            case 10:
                change = -10;
                break;

            case 11:
                change = -5;
                break;

            case 12:
                change = -1;
                break;

            case 14:
                change = 1;
                break;

            case 15:
                change = 5;
                break;

            case 16:
                change = 10;
                break;

            default:
                return;

        }

        radius += change;

        if (radius < 1) {
            radius = 1;
        }

        int maxRadius =
                (int) ConfigManager.get()
                        .getDouble(
                                "max-radius"
                        );
        if (maxRadius > 0
                && radius > maxRadius) {

            radius = maxRadius;

        }

        MachineManager.setRadius(
                block,
                radius
        );

        RSRadiusMenu.open(
                player,
                block
        );

    }

}