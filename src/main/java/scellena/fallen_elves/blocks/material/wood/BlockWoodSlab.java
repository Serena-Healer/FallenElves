package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.items.ModCreativeTab;

import java.util.Random;

public abstract class BlockWoodSlab extends BlockSlab implements IBlockBase, IBlockWithVariants
{
    public static final PropertyEnum<WoodSet.EnumWoodTypes> TYPE = PropertyEnum.create("type", WoodSet.EnumWoodTypes.class);

    public BlockWoodSlab()
    {
        super(Material.WOOD);
        IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(TYPE, WoodSet.EnumWoodTypes.MAGIC));
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockHandler.SLABS, 1, ((WoodSet.EnumWoodTypes)state.getValue(TYPE)).getId());
    }

    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey() + "." + WoodSet.EnumWoodTypes.byMetadata(meta).getName();
    }

    public IProperty<?> getVariantProperty()
    {
        return TYPE;
    }

    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return WoodSet.EnumWoodTypes.byMetadata(stack.getMetadata() & 7);
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (WoodSet.EnumWoodTypes type : WoodSet.EnumWoodTypes.values())
        {
            items.add(new ItemStack(this, 1, type.getId()));
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, WoodSet.EnumWoodTypes.byMetadata(meta & 7));

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        System.out.println(iblockstate);
        return iblockstate;
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((state.getValue(TYPE)).getId());

        if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {TYPE}) : new BlockStateContainer(this, new IProperty[] {HALF, TYPE});
    }

    public int damageDropped(IBlockState state)
    {
        return (state.getValue(TYPE)).getId();
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

    @Override
    public int getModelCount() {
        return 1;
    }

    @Override
    public String getModelName(int meta) {
        return WoodSet.EnumWoodTypes.byMetadata(meta).getName();
    }
}