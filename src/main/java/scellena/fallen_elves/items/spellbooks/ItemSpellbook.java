package scellena.fallen_elves.items.spellbooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.spells.SpellBase;

public class ItemSpellbook extends ItemBase {

    Class<? extends SpellBase> spell;

    public Class<? extends SpellBase> getSpell(ItemStack stackIn){
        return spell;
    }

    public ItemSpellbook(Class<? extends SpellBase> spell) {
        this.spell = spell;
    }

}
