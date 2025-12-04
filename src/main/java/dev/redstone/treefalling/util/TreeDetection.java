package dev.redstone.treefalling.util;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.Set;

public class TreeDetection {
    public static java.util.List<BlockPos> getTreeBlocks(World world, BlockPos startPos) {
        Set<BlockPos> visited = new java.util.HashSet<>();
        ArrayDeque<BlockPos> toCheck = new java.util.ArrayDeque<>();
        toCheck.add(startPos);
        while (!toCheck.isEmpty()) {
            BlockPos pos = toCheck.poll();
            if (!visited.add(pos)) continue;
            BlockState state = world.getBlockState(pos);
            if (state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.LEAVES)) {
                for (BlockPos neighbor : BlockPos.iterate(
                        pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1,
                        pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)) {
                    if (!visited.contains(neighbor)) {
                        BlockState neighborstate = world.getBlockState(neighbor);
                        if (neighborstate.isIn(BlockTags.LOGS) || neighborstate.isIn(BlockTags.LEAVES)) {
                            toCheck.add(neighbor.toImmutable());
                        }
                    }
                }
            }
        }
        return new java.util.ArrayList<>(visited);
    }
}
