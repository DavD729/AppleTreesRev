package dav.mod.objects.blocks.tree;

import java.util.Random;

import dav.mod.Main;
import dav.mod.init.BlockInit;
import dav.mod.init.ItemInit;
import dav.mod.util.interfaces.IHasModel;
import dav.mod.world.gen.tree.AppleTreeGen;
import dav.mod.world.gen.tree.GoldAppleTreeGen;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AppleBlockSapling extends BlockBush implements IGrowable, IHasModel{
	
	private String type;
	
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    
   	public AppleBlockSapling(String name) {
    	
    		type = name.replaceAll("sapling_", "").trim();
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
		setCreativeTab(CreativeTabs.DECORATIONS);
			
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
    	
    	@Override
    	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    		return SAPLING_AABB;
    	}
    
    	@Override
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
  	public IBlockState getStateFromMeta(int meta) {
  		return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
  	}
  	
  	@Override
  	public int getMetaFromState(IBlockState state){
  		int i = 0;
  		i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
  		return i;
  	}
  	
  	@Override
  	protected BlockStateContainer createBlockState() {
  		return new BlockStateContainer(this, new IProperty[] {STAGE});	
  	}
  	
  	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        	if (!worldIn.isRemote) {
            		super.updateTick(worldIn, pos, state, rand);
            		if (worldIn.getLightFromNeighbors(pos) >= 9 && rand.nextInt(7) == 0) {
                		this.grow(worldIn, rand, pos, state);
            		}
        	}
    	}
  	
	@Override
	public void registerModels() {
		Main.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if(((Integer)state.getValue(STAGE)).intValue() == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, rand, pos, state);
		}
	}
	
	public void generateTree(World world, Random rand, BlockPos pos, IBlockState state) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;
        	WorldGenerator worldgenerator = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
        	switch(type) {
        	case "apple": worldgenerator = new AppleTreeGen();
        		      break;
		case "gapple": worldgenerator = new GoldAppleTreeGen();
        	}
        	IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
        	world.setBlockState(pos, iblockstate2, 4);
        	if(!worldgenerator.generate(world, rand, pos)) {
        		world.setBlockState(pos, state, 4);
        	}
    	}
		
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}
		
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}
}
