package dav.mod.objects.blocks.tree;

import java.util.Random;

import javax.annotation.Nullable;

import dav.mod.Main;
import dav.mod.init.BlockInit;
import dav.mod.init.ItemInit;
import dav.mod.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockApplePlant extends Block implements IGrowable, IHasModel{
	
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	protected static final AxisAlignedBB APPLE_AABB[] = new AxisAlignedBB[] {
		new AxisAlignedBB(0.25, 0.9, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.8, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.7, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.5, 0.25, 0.75, 1.0, 0.75),
		new AxisAlignedBB(0.25, 0.4, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.3, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.2, 0.25, 0.75, 1.0, 0.75), 
		new AxisAlignedBB(0.25, 0.2, 0.25, 0.75, 1.0, 0.75)
	};
	
	private int Type;
	
	public BlockApplePlant(String name, int type) {
		super(Material.PLANTS);
		this.Type = type;
		setSoundType(SoundType.PLANT);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		
		this.setHardness(0.5F);
		this.setTickRandomly(true);
		this.disableStats();
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(getRegistryName()));
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState upState = worldIn.getBlockState(pos.up());
    	if(!upState.getBlock().isLeaves(upState, worldIn, pos)) {
    		worldIn.destroyBlock(pos, true);
    	}
	}
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public boolean isFoliage(IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	// From the BlockCrops Class
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return APPLE_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }

    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 7;
    }

    protected int getAge(IBlockState state) {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age) {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(IBlockState state) {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.getInt(worldIn.rand, 2, 5);
    }

    protected Item getSeed() {
    	switch(Type) {
    	case 1: return Items.GOLDEN_APPLE;
    	default: return Items.APPLE;
    	}
    }

    protected Item getCrop() {
    	switch(Type) {
    	case 1: return Items.GOLDEN_APPLE;
    	default: return Items.APPLE;
    	}
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, 0);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? this.getCrop() : null;
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.getSeed());
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.withAge(meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return this.getAge(state);
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	IBlockState upState = worldIn.getBlockState(pos.up());
    	if(upState.getBlock().isLeaves(upState, worldIn, pos)) {
    		if (worldIn.getLightFromNeighbors(pos) >= 8) {
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
		return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).getBlock().isLeaves(worldIn.getBlockState(pos), worldIn, pos);
	}
    
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return !isMaxAge(state);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        worldIn.setBlockState(pos, this.withAge(i), 2);
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
}
