package scellena.fallen_elves.items.consumables;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.ModCreativeTab;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemDebug extends ItemBase {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(playerIn);
        data.setRandomEventTick(20 * 10);
        data.addMana(998244353);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.DUNGEON_BLOCKS;
    }
}
