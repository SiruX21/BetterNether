package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.features.DefaultFeature;
import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.interfaces.SurvivesOnNetherGround;
import org.betterx.betternether.world.features.NetherChunkPopulatorFeature;
import org.betterx.betternether.world.structures.plants.StructureWillow;

class WillowTreeFeature extends DefaultFeature {
    private static final StructureWillow STRUCTURE = new StructureWillow();

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        STRUCTURE.generate(featurePlaceContext.level(),
                featurePlaceContext.origin(),
                featurePlaceContext.random(),
                featurePlaceContext.chunkGenerator().getGenDepth(),
                NetherChunkPopulatorFeature.generatorForThread().context);
        return true;
    }
}

public class BlockWillowSapling extends FeatureSaplingBlock implements BonemealableBlock, SurvivesOnNetherGround {
    private static final DefaultFeature FEATURE = new WillowTreeFeature();

    public BlockWillowSapling() {
        super((state) -> FEATURE);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return BlocksHelper.isFertile(world.getBlockState(pos.below()))
                ? (random.nextInt(8) == 0)
                : (random.nextInt(16) == 0);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
//
//	@Override
//	protected Feature<?> getFeature() {
//		return FEATURE;
//	}
}