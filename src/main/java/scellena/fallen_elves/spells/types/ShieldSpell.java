package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;

public class ShieldSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = 6F + EntityCapabilityProvider.getEntityData(getOwner()).getCurrentLevel() * 0.15F;
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
