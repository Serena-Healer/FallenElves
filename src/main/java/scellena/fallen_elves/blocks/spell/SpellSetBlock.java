package scellena.fallen_elves.blocks.spell;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.blocks.BlockBase;
import scellena.fallen_elves.blocks.IBlockWithTileEntity;

import javax.annotation.Nullable;

public class SpellSetBlock extends BlockBase implements IBlockWithTileEntity, ITileEntityProvider {

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public SpellSetBlock() {
        super(Material.WOOD);
        setHardness(5);
        setLightLevel(15);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Override
    public String getModelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)playerIn.openGui(FallenElves.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        if(playerIn.isSneaking())return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        return true;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileEntitySpellSet.class;
    }

    @Override
    public String getTileEntityName() {
        return "upgrade_table";
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySpellSet();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileEntitySpellSet){
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySpellSet)tile);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
