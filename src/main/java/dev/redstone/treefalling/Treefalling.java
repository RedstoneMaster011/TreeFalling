package dev.redstone.treefalling;

import dev.redstone.treefalling.config.TreefallingConfigs;
import dev.redstone.treefalling.physics.TreeFallingBodys;
import dev.redstone.treefalling.util.WaitBeforeExecuting;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.xmx.velthoric.physics.body.manager.VxBodyManager;
import net.xmx.velthoric.physics.body.manager.VxRemovalReason;
import net.xmx.velthoric.physics.body.type.VxBody;
import net.xmx.velthoric.physics.world.VxPhysicsWorld;

import java.util.ArrayList;
import java.util.List;

public class Treefalling implements ModInitializer {

    public static final String MOD_ID = "treefalling";

    @Override
    public void onInitialize() {

        TreefallingConfigs.init();

        TreeFallingBodys.register();

        WaitBeforeExecuting.init();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                VxPhysicsWorld physicsWorld = VxPhysicsWorld.get(world.getRegistryKey());
                if (physicsWorld != null && physicsWorld.isRunning()) {
                    VxBodyManager bodyManager = physicsWorld.getBodyManager();
                    for (VxBody body : new ArrayList<>(bodyManager.getAllBodies())) {
                        bodyManager.removeBody(body.getPhysicsId(), VxRemovalReason.DISCARD);
                    }
                }
            }
        });
    }
}
