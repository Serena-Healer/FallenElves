package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

public class AreaHealSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (4F * (1.0F + 0.02F * SpellHelper.getMending(getOwner())));
        SkillUtils.spawnParticleCircle(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.VILLAGER_HAPPY, 4);
        SkillUtils.playSoundFromServer(getOwner().world, getOwner().getPositionVector(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.PLAYERS, 1, 1);
        SkillUtils.playSoundFromServer(getOwner().world, getOwner().getPositionVector(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.75F, 1);
        for(EntityLivingBase living : SkillUtils.getPlayersInArea(getOwner().world, getOwner().getPositionVector(), 4)){
            living.heal(amount);
        }
        return false;
    }

    @Override
    public double getManaCost() {
        return 100;
    }
}
