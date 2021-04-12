package dav.mod.objects.blocks.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AppleBlockPlant extends Block implements IGrowable{
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
		Block.makeCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 13.0D, 6.0D), //Age 0
		Block.makeCuboidShape(10.0D, 16.0D, 10.0D, 6.0D, 11.0D, 6.0D), //Age 1
		Block.makeCuboidShape(11.0D, 16.0D, 11.0D, 4.0D, 9.5D, 4.0D),  //Age 2
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 6.0D, 4.0D),  //Age 3
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 4.0D, 4.0D),  //Age 4
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 3.0D, 4.0D),  //Age 5
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 2.0D, 4.0D),  //Age 6
		Block.makeCuboidShape(12.0D, 16.0D, 12.0D, 4.0D, 2.0D, 4.0D)   //Age 7
	};
	
	private int Type;
	
	public AppleBlockPlant(int type, Properties properties) {
		super(properties.doesNotBlockMovement().needsRandomTick());
		this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), Integer.valueOf(0)));
		this.Type = type;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState upState = worldIn.getBlockState(pos.up());
    	if(!upState.isIn(BlockTags.LEAVES)) {
    		worldIn.destroyBlock(pos, true);
    	}
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public BlockRenderLayer getRenderLayer() {
	    return BlockRenderLayer.CUTOUT;
	}
	
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
	    return BlockFaceShape.UNDEFINED;
	}
	
	public int getOpacity(IBlockState state, IBlockReader worldIn, BlockPos pos) {
	    return 0;
	}
	
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
	}
	
	public IntegerProperty getAgeProperty() {
		return AGE;
	}
	
	public int getMaxAge() {
		return 7;
	}
	
	protected int getAge(IBlockState state) {
		return state.get(this.getAgeProperty());
	}
	
	public IBlockState withAge(int age) {
		return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
	}
	
	public boolean isMaxAge(IBlockState state) {
	    return state.get(this.getAgeProperty()) >= this.getMaxAge();
	}
	
	@Override
	public void tick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		IBlockState upState = worldIn.getBlockState(pos.up());
    	if(upState.isIn(BlockTags.LEAVES)) {
    		if (worldIn.getLight(pos) >= 8) {
    			int i = this.getAge(state);
    			if (i < this.getMaxAge()){
    				float f = getGrowthChance(worldIn, pos);
    				if(rand.nextInt((int)(20.0F / f) + 1) == 0) {
    					worldIn.setBlockState(pos, this.withAge(i + 1), 2);
    				}
    			}
    		}
    	} else {
    		worldIn.destroyBlock(pos, true);
    	}
	}
	
	public void grow(World worldIn, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = this.getMaxAge();
		if (i > j) i = j;
		worldIn.setBlockState(pos, this.withAge(i), 2);
	}
	
	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.nextInt(worldIn.rand, 2, 5);
	}
	
	private static float getGrowthChance(World worldIn, BlockPos pos) {
  		float f = 2.0F;
        BlockPos blockpos = pos;
        IBlockState air = Blocks.AIR.getDefaultState();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
                if (iblockstate.getBlock().getDefaultState() == air) {
                    f1 = 2.0F;
                }
                if (i != 0 || j != 0) {
                    f1 /= 2.0F;
                }
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
	
	private static boolean isLeavesOrAir(World worldIn, BlockPos pos) {
		return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).isIn(BlockTags.LEAVES);
	}
	
	protected IItemProvider getSeedsItem() {
		switch(Type) {
		case 1: return Items.GOLDEN_APPLE;
		default: return Items.APPLE;
		}
	}

	protected IItemProvider getCropsItem() {
		switch(Type) {
		case 1: return Items.GOLDEN_APPLE;
		default: return Items.APPLE;
		}
	}
	
	@Override
	public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
		super.getDrops(state, drops, world, pos, 0);
	}
	
	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		return this.isMaxAge(state) ? this.getCropsItem() : Items.AIR;
	}
	
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, IBlockState state) {
	    return new ItemStack(this.getSeedsItem());
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return !this.isMaxAge(state);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state);
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(AGE);
	}
}
