package dev.redstone.treefalling.client;

import dev.redstone.treefalling.physics.TreeFallingBodys;
import net.fabricmc.api.ClientModInitializer;

public class TreefallingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        TreeFallingBodys.registerClient();
    }
}
