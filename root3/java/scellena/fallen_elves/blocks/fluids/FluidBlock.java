package scellena.fallen_elves.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import scellena.fallen_elves.blocks.IBlockBase;

public class FluidBlock extends BlockFluidClassic implements IBlockBase {
    public FluidBlock(Fluid fluid) {
        super(fluid, Material.WATER);
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public CreativeTabs getTab() {
        return CreativeTabs.MATERIALS;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollision(worldIn, pos, state, entityIn);
        ((CustomFluidBase)getFluid()).onEnter(entityIn);
    }
}
