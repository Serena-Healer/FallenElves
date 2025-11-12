package scellena.fallen_elves.blocks.material.ore;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.items.ModCreativeTab;

public class BlockMagicOre extends BlockOre implements IBlockBase {

    public BlockMagicOre(){
        super();
        setHardness(3);
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.BLOCKS;
    }

}
