package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextComponentString;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

public class HealSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (2F * (1.0F + 0.02F * SpellHelper.getMending(getOwner())));
        if(getOwner() instanceof EntityLivingBase){
            ((EntityLivingBase) getOwner()).heal(amount);
            return true;
        }
        return false;
    }

    @Override
    public double getManaCost() {
        return 40;
    }
}
