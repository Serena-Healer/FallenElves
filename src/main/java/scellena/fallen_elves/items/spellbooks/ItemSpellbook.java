package scellena.fallen_elves.items.spellbooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

public class ItemSpellbook extends ItemBase {

    Class<? extends SpellBase> spell;

    public Class<? extends SpellBase> getSpell(ItemStack stackIn){
        return spell;
    }

    public ItemSpellbook(Class<? extends SpellBase> spell) {
        this.spell = spell;
    }

    @Override
    public String modelJsonName() {
        return FallenElves.MOD_ID + ":spellbook";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocalFormatted("item.spellbook.name", I18n.translateToLocalFormatted("spell." + SpellHelper.getRegistryName(spell).toString() + ".name"));
    }

}
