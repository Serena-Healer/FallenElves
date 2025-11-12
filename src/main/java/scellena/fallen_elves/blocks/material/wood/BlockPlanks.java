package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.items.ModCreativeTab;

public class BlockPlanks extends Block implements IBlockBase, IBlockWithVariants {

    public static final PropertyEnum<WoodSet.EnumWoodTypes> TYPE = PropertyEnum.create("type", WoodSet.EnumWoodTypes.class);

    public BlockPlanks() {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, WoodSet.EnumWoodTypes.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(TYPE).getId();
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
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

}
