package scellena.fallen_elves.items.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

public class ItemWand extends ItemBase {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        SpellBase spell = getSetSpell(playerIn, stack);
        if(spell != null && spell.isAvailable()){
            if(spell.onRightClick()){
                SpellHelper.onFinishSpell(playerIn, spell);
            };
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public SpellBase getSetSpell(EntityPlayer playerIn, ItemStack stackIn){
        return null;
    }

}
