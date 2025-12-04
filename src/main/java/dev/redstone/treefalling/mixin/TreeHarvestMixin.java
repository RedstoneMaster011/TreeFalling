package dev.redstone.treefalling.mixin;

import dev.redstone.treefalling.util.SpawnPhysicsBlock;
import dev.redstone.treefalling.util.TreeDetection;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public class TreeHarvestMixin {

    @Shadow
    protected ServerWorld world;

    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At("HEAD"))
    private void beforeBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (player.isSneaking()) {
            if (world.getBlockState(pos).isIn(BlockTags.LOGS)) {

                List<BlockPos> list = TreeDetection.getTreeBlocks(world, pos);
                if (list.size() == 1) return;
                int i = 0;
                for (; i != list.size(); i++) {

                    BlockPos blockPOS = list.get(i);
                    BlockState blockSTATE = world.getBlockState(blockPOS);

                    world.breakBlock(blockPOS, true);

                    SpawnPhysicsBlock.spawn(blockSTATE, world, blockPOS);
                }
            }
        }
    }
}