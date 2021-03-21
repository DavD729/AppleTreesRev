package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ApplePlantBlock extends Block implements IGrowable{
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
		Block.makeCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 13.0D, 6.0D), //Age 0
		Block.makeCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 11.0D, 6.0D), //Age 1
		Block.makeCuboidShape(11.0D, 16.0D, 11.0D, 4.0D, 9.5D, 4.0D),  //Age 2
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 6.0D, 4.0D),  //Age 3
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 3.0D, 4.0D, 3.0D),  //Age 4
		Block.makeCuboidShape(14.0D, 16.0D, 14.0D, 2.0D, 3.0D, 2.0D),  //Age 5
		Block.makeCuboidShape(15.0D, 16.0D, 15.0D, 1.0D, 1.0D, 1.0D),  //Age 6
		Block.makeCuboidShape(15.0D, 16.0D, 15.0D, 1.0D, 1.0D, 1.0D)   //Age 7
	};
	private Item drop;
	
	public ApplePlantBlock(Properties properties, Item drop) {
		super(properties.doesNotBlockMovement().hardnessAndResistance(0.4F, 0.0F).tickRandomly().sound(SoundType.PLANT).notSolid());
		this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), Integer.valueOf(0)));
		this.drop = drop;
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockState upState = worldIn.getBlockState(pos.up());
		if(!upState.isIn(BlockTags.LEAVES)) {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
	
	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
	}

	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 7;
	}

	protected int getAge(BlockState state) {
		return state.get(this.getAgeProperty());
	}

	public BlockState withAge(int age) {
		return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
	}

	public boolean isMaxAge(BlockState state) {
		return state.get(this.getAgeProperty()) >= this.getMaxAge();
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!worldIn.isAreaLoaded(pos, 1)) return;
		BlockState upState = worldIn.getBlockState(pos.up());
		if(upState.isIn(BlockTags.LEAVES)) {
			if (worldIn.getLightSubtracted(pos, 0) >= 9) {
				int i = this.getAge(state);
				if (i < this.getMaxAge()) {
					float f = getGrowthChance(this, worldIn, pos);
					if (rand.nextInt((int)(20.0F / f) + 1) == 0) {
						worldIn.setBlockState(pos, this.withAge(i + 1), 2);
					}
				}
			}
		} else {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	public void grow(World worldIn, BlockPos pos, BlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = this.getMaxAge();
		if (i > j) {
			i = j;
		}
		worldIn.setBlockState(pos, this.withAge(i), 2);
	}

	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.nextInt(worldIn.rand, 2, 5);
	}

	protected static float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {
		float f = 2.0F;
        BlockPos blockpos = pos;
        BlockState air = Blocks.AIR.getDefaultState();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
                if (iblockstate.getBlock().getDefaultState() == air) f1 = 2.0F;
                if (i != 0 || j != 0) f1 /= 2.0F;
                f += f1;
            }
        }
        BlockPos blockposN = pos.north();
  		BlockPos blockposS = pos.south();
  		BlockPos blockposW = pos.west();
  		BlockPos blockposE = pos.east();
  		boolean flag = !isLeavesOrAir(worldIn, blockposN) || !isLeavesOrAir(worldIn, blockposS);
        boolean flag1 = !isLeavesOrAir(worldIn, blockposE) || !isLeavesOrAir(worldIn, blockposW);

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = !isLeavesOrAir(worldIn, blockposW.north()) || !isLeavesOrAir(worldIn, blockposE.north()) || !isLeavesOrAir(worldIn, blockposN.south()) || !isLeavesOrAir(worldIn, blockposW.south());
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
  	}
	
	private static boolean isLeavesOrAir(IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).isAir(worldIn, pos) || worldIn.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canBlockSeeSky(pos);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof RavagerEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	protected IItemProvider getSeedsItem() {
		return this.drop;
	}
	
	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(this.getSeedsItem());
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return !this.isMaxAge(state);
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		this.grow(worldIn, pos, state);
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
