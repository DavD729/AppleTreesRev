package net.dav.appletreesrev.world.gen.feature;

import com.google.common.collect.Lists;
import net.dav.appletreesrev.objects.block.BlockApplePlant;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BigAppleTreeGen extends AbstractBaseTree {
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int trunkSize = 1;
    int heightLimitLimit = 12;
    int leafDistanceLimit = 4;
    final IBlockState applePlant;
    final boolean isNatural;
    List<FoliageCoordinates> foliageCoords;
    final List<BlockPos> leavesPos = Lists.newArrayList();

    public BigAppleTreeGen(int meta, boolean isNatural) {
        super();
        this.applePlant = BlockApplePlant.getPlantByMeta(meta);
        this.isNatural = isNatural;
    }

    private void generateLeafNodeList() {
        this.height = (int)((double)this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int i = (int)(1.382D + Math.pow(this.leafDensity * (double)this.heightLimit / 13.0D, 2.0D));

        if (i < 1) {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.foliageCoords = Lists.newArrayList();
        this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k) {
            float f = this.layerSize(k);

            if (f >= 0.0F) {
                for (int l = 0; l < i; ++l) {
                    double d0 = this.scaleWidth * (double)f * ((double)this.rand.nextFloat() + 0.328D);
                    double d1 = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, k - 1, d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

                    if (this.checkBlockLine(blockpos, blockpos1) == -1) {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = (double)blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.branchSlope;
                        int k1 = d4 > (double)j ? j : (int)d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1) {
                            this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    private void crossSection(BlockPos pos, float nodeSize) {
        int i = (int)((double) nodeSize + 0.618D);

        for (int j = -i; j <= i; ++j) {
            for (int k = -i; k <= i; ++k) {
                if (Math.pow((double)Math.abs(j) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k) + 0.5D, 2.0D) <= (double)(nodeSize * nodeSize)) {
                    BlockPos blockpos = pos.add(j, 0, k);
                    IBlockState state = this.world.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos)) {
                        this.leavesPos.add(blockpos);
                        this.setBlockAndNotifyAdequately(this.world, blockpos, AbstractBaseTree.LEAVE);
                    }
                }
            }
        }
    }

    private float layerSize(int y) {
        if ((float)y < (float)this.heightLimit * 0.3F) {
            return -1.0F;
        } else {
            float f = (float)this.heightLimit / 2.0F;
            float f1 = f - (float)y;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F) {
                f2 = f;
            } else if (Math.abs(f1) >= f) {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    private float leafSize(int y) {
        if (y >= 0 && y < this.leafDistanceLimit) {
            return y != 0 && y != this.leafDistanceLimit - 1 ? 3.0F : 2.0F;
        } else {
            return -1.0F;
        }
    }

    private void generateLeafNode(BlockPos pos) {
        for (int i = 0; i < this.leafDistanceLimit; ++i) {
            this.crossSection(pos.up(i), this.leafSize(i));
        }
    }

    private void limb(BlockPos initPos, BlockPos finalPos) {
        BlockPos blockpos = finalPos.add(-initPos.getX(), -initPos.getY(), -initPos.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        for (int j = 0; j <= i; ++j) {
            BlockPos blockpos1 = initPos.add(0.5F + (float)j * f, 0.5F + (float)j * f1, 0.5F + (float)j * f2);
            BlockLog.EnumAxis axis = this.getLogAxis(initPos, blockpos1);
            this.setBlockAndNotifyAdequately(this.world, blockpos1, AbstractBaseTree.LOG.withProperty(BlockLog.LOG_AXIS, axis));
        }
    }

    private int getGreatestDistance(BlockPos posIn) {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());

        if (k > i && k > j) {
            return k;
        } else {
            return Math.max(j, i);
        }
    }

    private BlockLog.EnumAxis getLogAxis(BlockPos p_175938_1_, BlockPos p_175938_2_) {
        BlockLog.EnumAxis axis = BlockLog.EnumAxis.Y;
        int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
        int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
        int k = Math.max(i, j);

        if (k > 0) {
            if (i == k) {
                axis = BlockLog.EnumAxis.X;
            } else {
                axis = BlockLog.EnumAxis.Z;
            }
        }

        return axis;
    }

    private void generateLeaves() {
        for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.foliageCoords) {
            this.generateLeafNode(worldgenbigtree$foliagecoordinates);
        }
    }

    private boolean leafNodeNeedsBase(int p_76493_1_) {
        return (double)p_76493_1_ >= (double)this.heightLimit * 0.2D;
    }

    private void generateTrunk() {
        BlockPos initPos = this.basePos;
        BlockPos finalPos = this.basePos.up(this.height);
        this.limb(initPos, finalPos);

        if (this.trunkSize == 2) {
            this.limb(initPos.east(), finalPos.east());
            this.limb(initPos.east().south(), finalPos.east().south());
            this.limb(initPos.south(), finalPos.south());
        }
    }

    private void generateLeafNodeBases() {
        for (FoliageCoordinates foliageCoordinates : this.foliageCoords) {
            int i = foliageCoordinates.getBranchBase();
            BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

            if (!blockpos.equals(foliageCoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY())) {
                this.limb(blockpos, foliageCoordinates);
            }
        }
    }

    private int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
        BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        if (i != 0) {
            for (int j = 0; j <= i; ++j) {
                BlockPos blockpos1 = posOne.add(0.5F + (float) j * f, 0.5F + (float) j * f1, 0.5F + (float) j * f2);

                if (!this.isReplaceable(world, blockpos1)) {
                    return j;
                }
            }

        }
        return -1;
    }

    @Override
    public void setDecorationDefaults() {
        this.leafDistanceLimit = 5;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.world = worldIn;
        this.basePos = position;
        this.rand = new Random(rand.nextLong());

        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation()) {
            this.world = null;
            return false;
        } else {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            this.generateFruit(worldIn, rand, position);
            this.world = null;
            return true;
        }
    }

    @Override
    protected void generateFruit(World worldIn, Random rand, BlockPos pos) {
        List<BlockPos> applePos = leavesPos.stream().filter(leavePos -> worldIn.isAirBlock(leavePos.down())).collect(Collectors.toList());
        if(!applePos.isEmpty()) {
            Collections.shuffle(applePos, rand);
            int cont = 0;

            for(int i = 0; i < applePos.size() && cont < 12; i++) {
                if(i < 3 || rand.nextInt(5) == 0) {
                    this.setBlockAndNotifyAdequately(worldIn, applePos.get(i).down(), this.getNatAge(applePlant, rand));
                    cont++;
                }
            }
        }
    }

    private IBlockState getNatAge(IBlockState state, Random rand) {
        return this.isNatural ? state.withProperty(BlockApplePlant.AGE, 2 + rand.nextInt(4)) : state;
    }

    private boolean validTreeLocation() {
        BlockPos down = this.basePos.down();
        IBlockState state = this.world.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, this.world, down, EnumFacing.UP, ((BlockSapling) Blocks.SAPLING));

        if (!isSoil) {
            return false;
        } else {
            int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));

            if (i == -1) {
                return true;
            } else if (i < 6) {
                return false;
            } else {
                this.heightLimit = i;
                return true;
            }
        }
    }

    private static class FoliageCoordinates extends BlockPos {
        private final int branchBase;

        public FoliageCoordinates(BlockPos pos, int p_i45635_2_) {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.branchBase = p_i45635_2_;
        }

        public int getBranchBase() {
            return this.branchBase;
        }
    }
}
