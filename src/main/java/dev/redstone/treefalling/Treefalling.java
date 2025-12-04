package dev.redstone.treefalling;

import dev.redstone.treefalling.physics.TreeFallingBodys;
import dev.redstone.treefalling.util.WaitBeforeExecuting;
import net.fabricmc.api.ModInitializer;

public class Treefalling implements ModInitializer {

    public static final String MOD_ID = "treefalling";

    @Override
    public void onInitialize() {

        TreeFallingBodys.register();

        WaitBeforeExecuting.init();
    }
}
