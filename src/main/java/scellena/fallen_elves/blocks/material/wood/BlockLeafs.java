package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.items.ModCreativeTab;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockLeafs extends BlockLeaves implements IBlockBase, IBlockWithVariants {

    public static final PropertyEnum<WoodSet.EnumWoodTypes> TYPE = PropertyEnum.create("type", WoodSet.EnumWoodTypes.class);

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, CHECK_DECAY, DECAYABLE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, Objects.requireNonNull(WoodSet.EnumWoodTypes.byMetadata(meta & 3))).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int i = 0;
        i = i | state.getValue(TYPE).getId();
        if (!(Boolean) state.getValue(DECAYABLE))
        {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            i |= 8;
        }

        return i;
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
        return getMetaFromState(state);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if(Math.random() < 0.02 + fortune * 0.01) {
            //drops.add(new ItemStack((Item) ItemHandler.APPLE, 2 + fortune));
        }
        if(Math.random() < 0.02 + fortune * 0.01) {
            drops.add(new ItemStack(ItemBlock.getItemFromBlock(BlockHandler.SAPLINGS), 1));
        }
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return !this.leavesFancy;
    }

    @SideOnly(Side.CLIENT)
    public void setGraphicsLevel(boolean fancy)
    {
        this.leavesFancy = fancy;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }
}
