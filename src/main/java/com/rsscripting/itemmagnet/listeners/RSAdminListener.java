package com.rsscripting.itemmagnet.listeners;

import com.rsscripting.itemmagnet.config.ConfigManager;
import com.rsscripting.itemmagnet.gui.RSAdminMenu;
import com.rsscripting.itemmagnet.gui.RSMenuHolder;
import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.gui.RSMainMenu;
import org.bukkit.block.Block;
import com.rsscripting.itemmagnet.utils.RSMessageUtils;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RSAdminListener
        implements Listener {

    private final RSConvertListener
            conversionListener;

    public RSAdminListener(
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
                        "ADMIN"
                )) {
            return;
        }

        event.setCancelled(true);

        /*
        |--------------------------------------------------------------------------
        | SECURITY
        |--------------------------------------------------------------------------
        */

        if (!player.isOp()
                ||

                !player.hasPermission(
                        RSConstants.ADMIN_PERMISSION
                )) {

            player.closeInventory();

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | BACK
        |--------------------------------------------------------------------------
        */

        if (event.getRawSlot() == 4) {

            Block block =
                    conversionListener.getSelectedBlock();

            if (block == null) {

                player.closeInventory();

                return;

            }

            RSMainMenu.open(
                    player,
                    block
            );

            return;

        }

        /*
        |--------------------------------------------------------------------------
        | RELOAD
        |--------------------------------------------------------------------------
        */

        if (event.getRawSlot() == 12) {

            ConfigManager.reload();

            RSMessageUtils.success(
                    player,
                    "Configuration reloaded."
            );

            RSAdminMenu.open(
                    player
            );

        }

    }

}