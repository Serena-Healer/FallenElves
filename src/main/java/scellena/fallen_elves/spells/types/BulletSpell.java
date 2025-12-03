package scellena.fallen_elves.spells.types;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.util.SkillUtils;

public class BulletSpell extends SpellBase {
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
        float amount = 4F * (float)Math.pow(7, EntityCapabilityProvider.getEntityData(getOwner()).getCurrentLevel() / 30.0);
        if(duration > 0) {
            for (int i = 0; i < 50; i++) {
                position = position.add(direction.normalize().scale(0.1));
                int hit = 0;
                for (EntityLivingBase entity : SkillUtils.getEnemiesInArea(getOwner().world, position, 1)) {
                    hit++;
                    entity.attackEntityFrom(new EntityDamageSource("wither", getOwner()), amount);
                }
                IBlockState state = getOwner().world.getBlockState(new BlockPos(position));
                if(state.getBlock().canCollideCheck(state, false)) hit++;
                if (hit > 0) {
                    duration = 0;
                    SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.EXPLOSION_NORMAL, 1, 0, 0, 0, 0);
                    SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 1);
                    break;
                }
                SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.2F, 1);
                SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.DRAGON_BREATH, 1, 0, 0, 0, 0);
            }
            duration--;
        }
    }

    @Override
    public double getManaCost() {
        return 10;
    }
}
