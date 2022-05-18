package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureCeilingMushrooms implements IStructure {
    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        if (pos.getY() < 30 + random.nextInt((int) (10 * scale_factor))) return;
        pos = pos.above();
        if (canPlace(world, pos))
            BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
        if (canPlace(world, pos.north()))
            BlocksHelper.setWithoutUpdate(world, pos.north(), NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
        if (canPlace(world, pos.south()))
            BlocksHelper.setWithoutUpdate(world, pos.south(), NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
        if (canPlace(world, pos.east()))
            BlocksHelper.setWithoutUpdate(world, pos.east(), NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
        if (canPlace(world, pos.west()))
            BlocksHelper.setWithoutUpdate(world, pos.west(), NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
    }

    private boolean canPlace(ServerLevelAccessor world, BlockPos pos) {
        return BlocksHelper.isNetherrack(world.getBlockState(pos)) && world.getBlockState(pos.below())
                                                                           .getMaterial()
                                                                           .isReplaceable();
    }
}