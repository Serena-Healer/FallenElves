package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

public class HealSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (2F * (1.0F + 0.02F * SpellHelper.getMending(getOwner())));
        SkillUtils.spawnParticleCircle(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.VILLAGER_HAPPY, 0.6);
        SkillUtils.playSoundFromServer(getOwner().world, getOwner().getPositionVector(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 1);
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
