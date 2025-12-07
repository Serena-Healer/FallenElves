package scellena.fallen_elves.items.consumables;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.ModCreativeTab;

public class ItemExperience extends ItemBase {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(playerIn);
        data.collectXP(1000);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.DUNGEON_BLOCKS;
    }
}
