package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.items.ModCreativeTab;
import scellena.fallen_elves.worldgen.gen.TreeGenerator;

import java.util.Random;

public class BlockSaplings extends BlockBush implements IBlockBase, IBlockWithVariants, IGrowable {

    public static final PropertyEnum<WoodSet.EnumWoodTypes> TYPE = PropertyEnum.create("type", WoodSet.EnumWoodTypes.class);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockSaplings() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, WoodSet.EnumWoodTypes.MAGIC).withProperty(STAGE, Integer.valueOf(0)));
        setSoundType(SoundType.PLANT);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, STAGE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, WoodSet.EnumWoodTypes.byMetadata(meta % 8)).withProperty(STAGE, meta / 8);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(TYPE).getId() + state.getValue(STAGE) * 8;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        if(itemIn == ModCreativeTab.BLOCKS){
            for(int i=1; i<WoodSet.EnumWoodTypes.values().length; i++){
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getModelCount() {
        return WoodSet.EnumWoodTypes.values().length;
    }

    @Override
    public String getModelName(int meta) {
        return WoodSet.EnumWoodTypes.byMetadata(meta).getName();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state) % 8;
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch (state.getValue(TYPE))
        {
            case MAGIC:
                worldgenerator = new TreeGenerator(BlockHandler.LOGS.getDefaultState().withProperty(BlockLogs.TYPE, WoodSet.EnumWoodTypes.MAGIC).withProperty(BlockLogs.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.LEAFS.getDefaultState().withProperty(BlockLogs.TYPE, WoodSet.EnumWoodTypes.MAGIC).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE), true);
                break;
        }

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        worldIn.setBlockState(pos, iblockstate2, 4);

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
        {
            worldIn.setBlockState(pos, state, 4);
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (((Integer)state.getValue(STAGE)).intValue() == 0)
        {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.grow(worldIn, pos, state, rand);
    }

}

