package scellena.fallen_elves.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import scellena.fallen_elves.blocks.spell.ContainerSpellSet;
import scellena.fallen_elves.blocks.spell.GuiSpellSet;
import scellena.fallen_elves.blocks.spell.TileEntitySpellSet;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == 1)return new ContainerSpellSet(player.inventory, (TileEntitySpellSet) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == 1)return new GuiSpellSet(player.inventory, (TileEntitySpellSet) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
