package com.rsscripting.itemmagnet.listeners;

import com.rsscripting.itemmagnet.utils.GitHubUpdateChecker;
import com.rsscripting.itemmagnet.utils.RSConstants;
import com.rsscripting.itemmagnet.utils.VersionUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RSUpdateNotificationListener
        implements Listener {

    @EventHandler
    public void onPlayerJoin(
            PlayerJoinEvent event
    ) {

        if (!event.getPlayer().isOp()) {

            return;

        }

        if (!GitHubUpdateChecker.isUpdateAvailable()) {

            return;

        }

        event.getPlayer().sendMessage(

                RSConstants.PREFIX
                        + "Update Available! Current: "
                        + VersionUtils.getVersion()
                        + " Latest: "
                        + GitHubUpdateChecker.getLatestVersionFound()

        );

    }

}