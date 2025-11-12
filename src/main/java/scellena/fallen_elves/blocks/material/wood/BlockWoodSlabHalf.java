package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockWithBlockItem;

public class BlockWoodSlabHalf extends BlockWoodSlab implements IBlockWithBlockItem {
    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemSlab(this, this, (BlockSlab) BlockHandler.SLABS_FULL);
    }
}
