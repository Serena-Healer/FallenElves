package scellena.fallen_elves.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import scellena.fallen_elves.items.ModCreativeTab;

public class BlockBase extends Block implements IBlockBase{

    ModCreativeTab tab;

    public BlockBase(Material blockMaterialIn, double hardness) {
        this(blockMaterialIn, hardness, ModCreativeTab.BLOCKS);
    }

    public BlockBase(Material blockMaterialIn) {
        super(blockMaterialIn);
        this.tab = ModCreativeTab.BLOCKS;
    }

    public BlockBase(Material blockMaterialIn, double hardness, ModCreativeTab tab) {
        super(blockMaterialIn);
        setHardness((float) hardness);
        this.tab = tab;
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public CreativeTabs getTab() {
        return tab;
    }

}
