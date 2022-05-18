package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockWartSeed;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWartBush implements IStructure {
    private static final Direction[] DIRS = new Direction[]{
            Direction.UP,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.EAST,
            Direction.WEST
    };

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        if (world.isEmptyBlock(pos)) {
            BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
            for (Direction dir : DIRS)
                setSeed(world, pos, dir);
        }
    }

    private void setSeed(ServerLevelAccessor world, BlockPos pos, Direction dir) {
        BlockPos p = pos.relative(dir);
        if (world.isEmptyBlock(p))
            BlocksHelper.setWithoutUpdate(world, p, NetherBlocks.MAT_WART.getSeed().defaultBlockState().setValue(
                    BlockWartSeed.FACING, dir));
    }
}