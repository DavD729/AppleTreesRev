package net.dav.appletreesrev.objects.block;

import net.dav.appletreesrev.AppleTreesRev;
import net.dav.appletreesrev.init.BlockInit;
import net.dav.appletreesrev.init.ItemInit;
import net.dav.appletreesrev.objects.item.ItemBlockVariant;
import net.dav.appletreesrev.util.IHasModel;
import net.dav.appletreesrev.world.gen.feature.AppleTreeGen;
import net.dav.appletreesrev.world.gen.feature.BigAppleTreeGen;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BlockAppleSapling extends BlockBush implements IGrowable, IHasModel {
    public static final PropertyEnum<TreeType> APPLE_TYPE = PropertyEnum.create("apple_type", TreeType.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockAppleSapling(String name) {
        super();
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(STAGE, 0)
                .withProperty(APPLE_TYPE, TreeType.APPLE));
        this.setCreativeTab(CreativeTabs.DECORATIONS);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlockVariant(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    public String getLocalizedName() {
        return I18n.translateToLocal(this.getTranslationKey() + "." + TreeType.APPLE.getName() + ".name");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);
            if(!worldIn.isAreaLoaded(pos, 1)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldGen = rand.nextInt(10) == 0 ?
                new BigAppleTreeGen(this.damageDropped(state), false) :
                new AppleTreeGen(this.damageDropped(state), false);

        IBlockState air = Blocks.AIR.getDefaultState();
        worldIn.setBlockState(pos, air, 4);
        if(!worldGen.generate(worldIn, rand, pos)) {
            worldIn.setBlockState(pos, state, 4);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(APPLE_TYPE).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(TreeType appleType : TreeType.values()) {
            items.add(new ItemStack(this, 1, appleType.getMeta()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(BlockApplePlant.APPLE_TYPE).getMeta());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(APPLE_TYPE, TreeType.byMetadata(meta & 1))
                .withProperty(STAGE, (meta & 2) >> 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(STAGE) << 1) | this.damageDropped(state);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state, rand);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE, APPLE_TYPE);
    }

    @Override
    public void registerModel() {
        for(TreeType type : TreeType.values()) {
            AppleTreesRev.proxy.registerItemVariantRenderer(Item.getItemFromBlock(this), type.getName(), type.getMeta());
        }
    }

    public enum TreeType implements IStringSerializable {
        APPLE(0, "apple"),
        GOLDEN(1, "gold_apple");

        private static final TreeType[] META_LOOKUP = new TreeType[values().length];
        private final int meta;
        private final String name;

        TreeType(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public Item getDrop() {
            switch (this) {
                case APPLE: return Items.APPLE;
                case GOLDEN: return Items.GOLDEN_APPLE;
                default: return Items.AIR;
            }
        }

        public int getMeta() {
            return this.meta;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static TreeType byMetadata(int meta) {
            if(meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        static {
            for(TreeType type : values()) {
                META_LOOKUP[type.getMeta()] = type;
            }
        }
    }
}
