package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

public class ShieldSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (5F * (1.0F + 0.01F * SpellHelper.getMending(getOwner())));
        SkillUtils.spawnParticleFromServer(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.TOTEM, 800, 0.5, 1.5, 0.5, 0);
        SkillUtils.spawnParticleFromServer(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.DRAGON_BREATH, 800, 0.5, 1.5, 0.5, 0);
        SkillUtils.playSoundFromServer(getOwner().world, getOwner().getPositionVector(), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.PLAYERS, 1, 1);
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
