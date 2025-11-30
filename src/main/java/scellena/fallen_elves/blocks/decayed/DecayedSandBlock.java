package scellena.fallen_elves.blocks.decayed;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scellena.fallen_elves.blocks.BlockBase;
import scellena.fallen_elves.blocks.IBlockBase;
import scellena.fallen_elves.decay.BlockDecayHelper;
import scellena.fallen_elves.items.ModCreativeTab;

import java.util.Random;

public class DecayedSandBlock extends BlockBase implements IBlockBase {

    public DecayedSandBlock()
    {
        super(Material.SAND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        //横
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    BlockPos pos2 = pos.add(i - 1, j - 1, k - 1);
                    if(pos2.getDistance(0, 0, 0) == 0) continue;
                    Block block = worldIn.getBlockState(pos2).getBlock();
                    if(BlockDecayHelper.decay(worldIn, pos2)){
                        worldIn.scheduleUpdate(pos2, worldIn.getBlockState(pos2).getBlock(), rand.nextInt(40) + 40);
                    }
                }
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 40);
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.PURPLE;
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
