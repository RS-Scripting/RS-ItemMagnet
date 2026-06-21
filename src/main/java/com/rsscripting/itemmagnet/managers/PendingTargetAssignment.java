package com.rsscripting.itemmagnet.managers;

import org.bukkit.block.Block;

public class PendingTargetAssignment {

    private final Block machine;

    private final long expirationTime;

    public PendingTargetAssignment(
            Block machine,
            long expirationTime
    ) {

        this.machine = machine;
        this.expirationTime = expirationTime;

    }

    public Block getMachine() {

        return machine;

    }

    public boolean isExpired() {

        return System.currentTimeMillis()
                > expirationTime;

    }

}