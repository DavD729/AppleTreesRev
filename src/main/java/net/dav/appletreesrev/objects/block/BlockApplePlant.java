package net.dav.appletreesrev.objects.block;

import net.dav.appletreesrev.AppleTreesRev;
import net.dav.appletreesrev.init.BlockInit;
import net.dav.appletreesrev.init.ItemInit;
import net.dav.appletreesrev.objects.item.ItemBlockVariant;
import net.dav.appletreesrev.util.ConfigHandler;
import net.dav.appletreesrev.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockApplePlant extends Block implements IGrowable, IHasModel {
    public static final PropertyEnum<BlockAppleSapling.TreeType> APPLE_TYPE = PropertyEnum.create("apple_type", BlockAppleSapling.TreeType.class);
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    protected static final AxisAlignedBB[] APPLE_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.25, 0.9, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.8, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.7, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.5, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.4, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.3, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.2, 0.25, 0.75, 1.0, 0.75),
            new AxisAlignedBB(0.25, 0.2, 0.25, 0.75, 1.0, 0.75)
    };

    public BlockApplePlant(String name) {
        super(Material.PLANTS);
        this.setSoundType(SoundType.PLANT);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(APPLE_TYPE, BlockAppleSapling.TreeType.APPLE)
                .withProperty(AGE, 0));

        this.setHardness(0.5F);
        this.setTickRandomly(true);
        this.disableStats();
        this.setCreativeTab(CreativeTabs.DECORATIONS);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlockVariant(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && this.isMaxAge(state) && ConfigHandler.easyHarvest) {
            worldIn.destroyBlock(pos, true);
            if(worldIn.rand.nextInt(4) == 0)
                worldIn.setBlockState(pos, this.getDefaultState()
                        .withProperty(APPLE_TYPE, BlockAppleSapling.TreeType.byMetadata(state.getValue(APPLE_TYPE).getMeta())), 4);
            return true;
        }
        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        IBlockState upState = worldIn.getBlockState(pos.up());
        if(!upState.getBlock().isLeaves(upState, worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Nullable
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
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFoliage(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return APPLE_AABB[state.getValue(BlockApplePlant.AGE)];
    }

    public int getMaxAge() {
        return 7;
    }

    protected int getAge(IBlockState state) {
        return state.getValue(BlockApplePlant.AGE);
    }

    public IBlockState withAge(int age, BlockAppleSapling.TreeType type) {
        return this.getDefaultState()
                .withProperty(BlockApplePlant.AGE, age)
                .withProperty(BlockApplePlant.APPLE_TYPE, type);
    }

    public boolean isMaxAge(IBlockState state) {
        return state.getValue(BlockApplePlant.AGE) >= this.getMaxAge();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(BlockApplePlant.APPLE_TYPE).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(BlockAppleSapling.TreeType appleType : BlockAppleSapling.TreeType.values()) {
            items.add(new ItemStack(this, 1, appleType.getMeta()));
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        IBlockState upState = worldIn.getBlockState(pos.up());
        if(upState.getMaterial() == Material.LEAVES) {
            if (worldIn.getLightFromNeighbors(pos) >= 8) {
                int i = this.getAge(state);
                if (i < this.getMaxAge()){
                    float f = getGrowthChance(worldIn, pos);
                    if(rand.nextInt((int)(20.0F / f) + 1) == 0) {
                        worldIn.setBlockState(pos, this.withAge(i + 1, state.getValue(BlockApplePlant.APPLE_TYPE)), 2);
                    }
                }
            }
        } else {
            worldIn.destroyBlock(pos, true);
        }
    }

    private static float getGrowthChance(World worldIn, BlockPos pos) {
        float f = 2.0F;
        IBlockState air = Blocks.AIR.getDefaultState();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState state = worldIn.getBlockState(pos.add(i, 0, j));
                if (state.getBlock().getDefaultState() == air) {
                    f1 = 2.0F;
                }
                if (i != 0 || j != 0) {
                    f1 /= 2.0F;
                }
                f += f1;
            }
        }
        BlockPos posN = pos.north();
        BlockPos posS = pos.south();
        BlockPos posW = pos.west();
        BlockPos posE = pos.east();
        boolean flag = isNotLeavesOrAir(worldIn, posN) || isNotLeavesOrAir(worldIn, posS);
        boolean flag1 = isNotLeavesOrAir(worldIn, posE) || isNotLeavesOrAir(worldIn, posW);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = isNotLeavesOrAir(worldIn, posW.north()) || isNotLeavesOrAir(worldIn, posE.north()) || isNotLeavesOrAir(worldIn, posN.south()) || isNotLeavesOrAir(worldIn, posW.south());
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }

    private static boolean isNotLeavesOrAir(World worldIn, BlockPos pos) {
        return !worldIn.isAirBlock(pos) && !(worldIn.getBlockState(pos).getMaterial() == Material.LEAVES);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        int count = quantityDropped(state, 0, rand);

        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, 0);
            if (item != Items.AIR) {
                drops.add(new ItemStack(item, 1, 0));
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? state.getValue(BlockApplePlant.APPLE_TYPE).getDrop() : Items.AIR;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(BlockAppleSapling.TreeType.byMetadata(this.damageDropped(state)).getDrop(), 1, 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BlockApplePlant.APPLE_TYPE, BlockAppleSapling.TreeType.byMetadata(meta & 1))
                .withProperty(BlockApplePlant.AGE, (meta & 14) >> 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(BlockApplePlant.APPLE_TYPE).getMeta();
        meta = meta | (state.getValue(BlockApplePlant.AGE) << 1);
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, APPLE_TYPE, AGE);
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.getInt(worldIn.rand, 2, 5);
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return worldIn.getBlockState(pos.up()).getMaterial() == Material.LEAVES;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !this.isMaxAge(state);
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
        worldIn.setBlockState(pos, this.withAge(i, state.getValue(BlockApplePlant.APPLE_TYPE)), 2);
    }

    public static IBlockState getPlantByMeta(int meta) {
        return BlockInit.PLANT.getDefaultState()
                .withProperty(BlockApplePlant.APPLE_TYPE, BlockAppleSapling.TreeType.byMetadata(meta))
                .withProperty(BlockApplePlant.AGE, 0);
    }

    @Override
    public void registerModel() {
        for(BlockAppleSapling.TreeType type : BlockAppleSapling.TreeType.values()) {
            AppleTreesRev.proxy.registerItemVariantRenderer(Item.getItemFromBlock(this), type.getName(), type.getMeta());
        }
    }
}
