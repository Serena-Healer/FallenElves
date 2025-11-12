package scellena.fallen_elves.blocks.material.crops;

import net.minecraft.block.BlockFlower;
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

public class BlockRobeFlower extends BlockFlower implements IBlockBase {
    @Override
    public EnumFlowerColor getBlockType() {
        return EnumFlowerColor.RED;
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
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if(getTab() == itemIn){
            items.add(new ItemStack(this));
        }
    }
}
