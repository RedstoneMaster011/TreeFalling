package dev.redstone.treefalling.mixin;

import dev.redstone.treefalling.config.TreefallingConfigs;
import dev.redstone.treefalling.util.TreeDetection;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MiningSpeedMixin {
    @Inject(method = "calcBlockBreakingDelta", at = @At("RETURN"), cancellable = true)
    private void slowLogsWhenSneaking(PlayerEntity player, BlockView view, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!TreefallingConfigs.TreefallingConfig.TimeTakes) return;
        BlockState state = (BlockState)(Object)this;

        if (state.isIn(BlockTags.LOGS) && player.isSneaking() && view instanceof World world) {

            List blocks = TreeDetection.getTreeBlocks(world, pos);
            int len = blocks.size();

            if (len > 1) {
                float base = cir.getReturnValue();
                float newSpeed = base / (1.0F + len * 0.01F);
                cir.setReturnValue(newSpeed);
            }
        }
    }
}