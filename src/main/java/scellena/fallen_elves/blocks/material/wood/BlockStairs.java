package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.items.ModCreativeTab;

public class BlockStairs extends net.minecraft.block.BlockStairs implements IBlockBase {
    public BlockStairs(IBlockState p_i45684_1_) {
        super(p_i45684_1_);
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
