package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

public class ShieldSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (5F * (1.0F + 0.01F * SpellHelper.getMending(getOwner())));
        if(getOwner() instanceof EntityLivingBase){
            ((EntityLivingBase) getOwner()).setAbsorptionAmount(Math.max(amount, ((EntityLivingBase) getOwner()).getAbsorptionAmount()));
            return true;
        }
        return false;
    }

    @Override
    public double getManaCost() {
        return 25;
    }
}
