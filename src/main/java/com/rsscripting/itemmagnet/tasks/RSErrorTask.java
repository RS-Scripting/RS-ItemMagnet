package com.rsscripting.itemmagnet.tasks;

import com.rsscripting.itemmagnet.managers.MachineManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class RSErrorTask extends BukkitRunnable {

    @Override
    public void run() {

        Map<Location, Integer> machines =
                MachineManager.getAllMachines();

        for (
                Location machineLocation
                : machines.keySet()
        ) {

            Block machineBlock =
                    machineLocation.getBlock();

            String status =
                    MachineManager.getStoredStatus(
                            machineBlock
                    );

            if (
                    !status.startsWith(
                            "ERROR_"
                    )
            ) {

                continue;

            }

            machineLocation.getWorld()
                    .spawnParticle(

                            Particle.ANGRY_VILLAGER,

                            machineLocation.clone()
                                    .add(
                                            0.5,
                                            0.75,
                                            0.5
                                    ),

                            1

                    );

        }

    }

}