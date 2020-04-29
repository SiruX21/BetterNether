package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.EntityRegistry;

public class BlockEggPlant extends BlockCommonPlant
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
	
	private boolean enableModDamage = true;
	private boolean enablePlayerDamage = true;
	
	public BlockEggPlant()
	{
		super(MaterialColor.WHITE_TERRACOTTA);
		enableModDamage = Config.getBoolean("egg_plant", "mob_damage", true);
		enablePlayerDamage = Config.getBoolean("egg_plant", "player_damage", true);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		world.addParticle(
				ParticleTypes.ENTITY_EFFECT,
				pos.getX() + random.nextDouble(),
				pos.getY() + 0.4,
				pos.getZ() + random.nextDouble(),
				0.46, 0.28, 0.55);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if (enableModDamage && entity instanceof LivingEntity && !((LivingEntity) entity).hasStatusEffect(StatusEffects.POISON))
		{
			if (!EntityRegistry.isNetherEntity(entity))
				((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3));
		}
		else if (enablePlayerDamage && entity instanceof PlayerEntity && !((PlayerEntity) entity).hasStatusEffect(StatusEffects.POISON))
			((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3));
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (builder.get(LootContextParameters.TOOL).getItem() instanceof ShearsItem)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}
}
