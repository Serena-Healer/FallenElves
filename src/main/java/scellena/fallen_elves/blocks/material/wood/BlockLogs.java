package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.items.ModCreativeTab;

public class BlockLogs extends BlockLog implements IBlockBase, IBlockWithVariants {

    public static final PropertyEnum<WoodSet.EnumWoodTypes> TYPE = PropertyEnum.create("type", WoodSet.EnumWoodTypes.class);

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, LOG_AXIS});
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, WoodSet.EnumWoodTypes.byMetadata((meta & 3)));

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
        }

        return iblockstate;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(TYPE).getId();

        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
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
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

}
