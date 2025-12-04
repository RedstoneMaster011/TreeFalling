package dev.redstone.treefalling.util;

import com.github.stephengold.joltjni.Quat;
import com.github.stephengold.joltjni.RVec3;
import com.github.stephengold.joltjni.enumerate.EActivation;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xmx.velthoric.builtin.VxRegisteredBodies;
import net.xmx.velthoric.builtin.block.BlockRigidBody;
import net.xmx.velthoric.math.VxTransform;
import net.xmx.velthoric.physics.body.manager.VxBodyManager;
import net.xmx.velthoric.physics.body.manager.VxRemovalReason;
import net.xmx.velthoric.physics.world.VxPhysicsWorld;

public class SpawnPhysicsBlock {
    public static void spawn(BlockState state, World world, BlockPos pos) {
        VxPhysicsWorld physicsWorld = VxPhysicsWorld.get(world.getRegistryKey());
        VxBodyManager bodyManager = physicsWorld.getBodyManager();


        BlockState originalState = state;

        RVec3 blockPOS = new RVec3(pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5);
        Quat rot = Quat.sIdentity();
        VxTransform trans = new VxTransform(blockPOS, rot);

        BlockRigidBody blockBody = bodyManager.createRigidBody(
                VxRegisteredBodies.BLOCK,
                trans,
                EActivation.Activate,
                b -> b.setRepresentedBlockState(originalState != null ? originalState : Blocks.STONE.getDefaultState())
        );

        if (blockBody == null) return;
        WaitBeforeExecuting.execute(250, () -> {

            bodyManager.removeBody(blockBody.getPhysicsId(), VxRemovalReason.DISCARD);
        });
    }
}

