package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import scellena.fallen_elves.potions.PotionsHandler;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

public class MightSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        float amount = (float) (10F * (1.0F + 0.04F * SpellHelper.getMending(getOwner())));
        SkillUtils.spawnParticleCircle(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.SPELL_WITCH, 0.6);
        SkillUtils.spawnParticleCircle(getOwner().world, getOwner().getPositionVector(), EnumParticleTypes.DRAGON_BREATH, 0.6);
        SkillUtils.playSoundFromServer(getOwner().world, getOwner().getPositionVector(), SoundEvents.BLOCK_PORTAL_TRIGGER, SoundCategory.PLAYERS, 1, 2);
        if(getOwner() instanceof EntityLivingBase){
            ((EntityLivingBase) getOwner()).addPotionEffect(new PotionEffect(PotionsHandler.MIGHT, 20 * 30, (int)amount));
            return true;
        }
        return false;
    }

    @Override
    public double getManaCost() {
        return 160;
    }
}
