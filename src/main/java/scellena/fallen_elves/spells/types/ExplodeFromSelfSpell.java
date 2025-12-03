package scellena.fallen_elves.spells.types;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.util.SkillUtils;

public class ExplodeFromSelfSpell extends SpellBase {
    Vec3d position;
    int duration = 0;

    @Override
    public boolean onRightClick() {
        position = getOwner().getPositionVector().add(0, 1.5, 0);
        duration = 40;
        return true;
    }

    @Override
    public void onTick() {
        super.onTick();
        float amount = 8F + EntityCapabilityProvider.getEntityData(getOwner()).getCurrentLevel() * 0.2F;
        if(duration > 0) {
            duration--;
            double r = 20;
            SkillUtils.spawnParticleCircle(getOwner().world, position, EnumParticleTypes.FLAME, (40 - duration) * r / 40.0);
            SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1, (40 - duration) / 20.0F);
            if(duration == 0){
                SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1, 1);
                SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 0.5F);
                SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.EXPLOSION_LARGE, 50, 4, 4, 4, 0);
                for (EntityLivingBase entity : SkillUtils.getEnemiesInArea(getOwner().world, position, (float) r)) {
                    entity.attackEntityFrom(new EntityDamageSource("wither", getOwner()), amount);
                }
            }
        }
    }

    @Override
    public double getManaCost() {
        return 300;
    }
}
