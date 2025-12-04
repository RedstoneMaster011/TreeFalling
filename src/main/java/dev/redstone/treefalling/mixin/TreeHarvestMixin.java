package dev.redstone.treefalling.mixin;

import dev.redstone.treefalling.config.TreefallingConfigs;
import dev.redstone.treefalling.util.SpawnPhysicsBlock;
import dev.redstone.treefalling.util.TreeDetection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

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

                Random random = new Random();

                for (; i != list.size(); i++) {

                    BlockPos blockPOS = list.get(i);
                    BlockState blockSTATE = world.getBlockState(blockPOS);
                    
                    world.setBlockState(blockPOS, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                    
                    BlockEntity blockEntity = world.getBlockEntity(blockPOS);

                    List<ItemStack> drops = Block.getDroppedStacks(
                            blockSTATE,
                            world,
                            blockPOS,
                            blockEntity,
                            player,
                            player.getMainHandStack()
                    );

                    DefaultedList<ItemStack> defaultedDrops = DefaultedList.ofSize(drops.size(), ItemStack.EMPTY);
                    for (int ii = 0; ii < drops.size(); ii++) {
                        defaultedDrops.set(ii, drops.get(ii));
                    }

                    ItemScatterer.spawn(world, blockPOS, defaultedDrops);


                    if (TreefallingConfigs.TreefallingConfig.Physics) {
                        SpawnPhysicsBlock.spawn(blockSTATE, world, blockPOS);
                    }


                    if (random.nextInt(2) == 1) {
                        if (TreefallingConfigs.TreefallingConfig.Durability) {
                            ItemStack stack = player.getMainHandStack();

                            if (!stack.isEmpty() && stack.isDamageable()) {
                                stack.damage(1, player, EquipmentSlot.MAINHAND);
                            }
                        }
                    }
                }
            }
        }
    }
}