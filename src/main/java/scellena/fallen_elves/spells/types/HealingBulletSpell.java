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

public class HealingBulletSpell extends SpellBase {
    Vec3d position;
    Vec3d direction;
    int duration = 0;

    @Override
    public boolean onRightClick() {
        position = getOwner().getPositionVector().add(0, 1.5, 0);
        direction = getOwner().getLookVec();
        duration = 20;
        return true;
    }

    @Override
    public void onTick() {
        super.onTick();
        float amount = 3F + EntityCapabilityProvider.getEntityData(getOwner()).getCurrentLevel() * 0.015F;
        if(duration > 0) {
            for (int i = 0; i < 50; i++) {
                position = position.add(direction.normalize().scale(0.1));
                int hit = 0;
                EntityLivingBase target = SkillUtils.getNearestPlayer(getOwner().world, position, 1);
                IBlockState state = getOwner().world.getBlockState(new BlockPos(position));
                if(state.getBlock().canCollideCheck(state, false)) hit++;
                if(target != null && (target != getOwner() || hit > 0)){
                    hit++;
                    target.heal(amount);
                }
                if (hit > 0) {
                    duration = 0;
                    SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.VILLAGER_HAPPY, 200, 0.5, 1.5, 0.5, 0);
                    SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1, 1);
                    break;
                }
                SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.5F);
                SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.VILLAGER_HAPPY, 1, 0, 0, 0, 0);
            }
            duration--;
        }
    }

    @Override
    public double getManaCost() {
        return 10;
    }
}
