package com.rsscripting.itemmagnet.managers;

import org.bukkit.block.Block;

public class PendingConversion {

    private final Block lodestone;
    private final long expirationTime;

    public PendingConversion(
            Block lodestone,
            long expirationTime
    ) {

        this.lodestone =
                lodestone;

        this.expirationTime =
                expirationTime;

    }

    public Block getLodestone() {

        return lodestone;

    }

    public boolean isExpired() {

        return System.currentTimeMillis()
                > expirationTime;

    }

}