package dav.mod.objects.block.tree;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BlockApplePlant extends Block implements Fertilizable {
	
	private Item drop;
	
	public static final IntProperty AGE = Properties.AGE_7;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
		Block.createCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 13.0D, 6.0D), //Age 0
		Block.createCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 11.0D, 6.0D), //Age 1
		Block.createCuboidShape(11.0D, 16.0D, 11.0D, 4.0D, 9.5D, 4.0D),  //Age 2
		Block.createCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 6.0D, 4.0D),  //Age 3
		Block.createCuboidShape(12.0D, 16.0D, 12.0D, 3.0D, 4.0D, 3.0D),  //Age 4
		Block.createCuboidShape(14.0D, 16.0D, 14.0D, 2.0D, 3.0D, 2.0D),  //Age 5
		Block.createCuboidShape(15.0D, 16.0D, 15.0D, 1.0D, 1.0D, 1.0D),  //Age 6
		Block.createCuboidShape(15.0D, 16.0D, 15.0D, 1.0D, 1.0D, 1.0D)   //Age 7
	};
	
	public BlockApplePlant(Settings settings, Item drop) {
		super(settings.strength(0.5F, 0.0F).noCollision());
		this.drop = drop;
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0));
	}
	
	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
		BlockState upState = world.getBlockState(pos.up());
		if(!upState.matches(BlockTags.LEAVES)) {
			world.breakBlock(pos, true);
		}
	}
	
	@Override
	public RenderLayer getRenderLayer() {
		return RenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
		return true;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
		return SHAPE_BY_AGE[(Integer)state.get(this.getAgeProperty())];
	}

	public IntProperty getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 7;
	}

	protected int getAge(BlockState state) {
		return (Integer)state.get(this.getAgeProperty());
	}

	public BlockState withAge(int age) {
		return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
	}

	public boolean isMature(BlockState state) {
		return (Integer)state.get(this.getAgeProperty()) >= this.getMaxAge();
	}
	
	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random random) {
		BlockState upState = world.getBlockState(pos.up());
		if(upState.matches(BlockTags.LEAVES)) {
			if (world.getLightLevel(pos, 0) >= 9) {
				int i = this.getAge(state);
				if (i < this.getMaxAge()) {
					float f = getAvailableMoisture(this, world, pos);
					if (random.nextInt((int)(20.0F / f) + 1) == 0) {
						world.setBlockState(pos, this.withAge(i + 1), 2);
					}
				}
			}
		} else {
			world.breakBlock(pos, true);
		}
	}

	public void applyGrowth(World world, BlockPos pos, BlockState state) {
		int i = this.getAge(state) + this.getGrowthAmount(world);
		int j = this.getMaxAge();
		if (i > j) {
			i = j;
		}
		world.setBlockState(pos, this.withAge(i), 2);
	}

	protected int getGrowthAmount(World world) {
		return MathHelper.nextInt(world.random, 2, 5);
	}

	protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
		float f = 2.0F;
        BlockPos blockpos = pos;
        BlockState air = Blocks.AIR.getDefaultState();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState iblockstate = world.getBlockState(blockpos.add(i, 0, j));
                if (iblockstate.getBlock().getDefaultState() == air) f1 = 2.0F;
                if (i != 0 || j != 0) f1 /= 2.0F;
                f += f1;
            }
        }
        BlockPos blockposN = pos.north();
  		BlockPos blockposS = pos.south();
  		BlockPos blockposW = pos.west();
  		BlockPos blockposE = pos.east();
  		boolean flag = !isLeavesOrAir(world, blockposN) || !isLeavesOrAir(world, blockposS);
        boolean flag1 = !isLeavesOrAir(world, blockposE) || !isLeavesOrAir(world, blockposW);

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = !isLeavesOrAir(world, blockposW.north()) || !isLeavesOrAir(world, blockposE.north()) || !isLeavesOrAir(world, blockposN.south()) || !isLeavesOrAir(world, blockposW.south());
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
  	}
	
	private static boolean isLeavesOrAir(BlockView worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isAir() || worldIn.getBlockState(pos).matches(BlockTags.LEAVES);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, CollisionView world, BlockPos pos) {
		return (world.getLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
			world.breakBlock(pos, true);
		}
		super.onEntityCollision(state, world, pos, entity);
	}

	@Environment(EnvType.CLIENT)
	protected ItemConvertible getSeedsItem() {
		return this.drop;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(this.getSeedsItem());
	}
	
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return !this.isMature(state);
	}
	
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}
	
	@Override
	public void grow(World world, Random random, BlockPos pos, BlockState state) {
		this.applyGrowth(world, pos, state);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
