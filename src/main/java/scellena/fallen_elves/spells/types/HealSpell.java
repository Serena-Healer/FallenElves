package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextComponentString;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

public class HealSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = 4F + EntityCapabilityProvider.getEntityData(getOwner()).getCurrentLevel() * 0.1F;
        if(getOwner() instanceof EntityLivingBase){
            ((EntityLivingBase) getOwner()).heal(amount);
            return true;
        }
        return false;
    }

    @Override
    public double getManaCost() {
        return 15;
    }
}
