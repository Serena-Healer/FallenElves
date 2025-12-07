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
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

public class BulletSpellLight extends SpellBase {
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
        float amount = (float) (3F * (1.0F + 0.02F * SpellHelper.getMight(getOwner())));
        if(duration > 0) {
            for (int i = 0; i < 50; i++) {
                position = position.add(direction.normalize().scale(0.1));
                int hit = 0;
                for (EntityLivingBase entity : SkillUtils.getPlayersInArea(getOwner().world, position, 1)) {
                    hit++;
                    entity.attackEntityFrom(new EntityDamageSource("light", getOwner()), amount);
                }
                IBlockState state = getOwner().world.getBlockState(new BlockPos(position));
                if(state.getBlock().canCollideCheck(state, false)) hit++;
                if (hit > 0) {
                    duration = 0;
                    for(int j=-30; j<30; j++){
                        SkillUtils.spawnParticleCircle(getOwner().world, position.add(0, j * 0.1, 0), EnumParticleTypes.END_ROD, 1);
                    }
                    SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 0.5F);
                    break;
                }
                SkillUtils.playSoundFromServer(getOwner().world, position, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.8F);
                SkillUtils.spawnParticleFromServer(getOwner().world, position, EnumParticleTypes.END_ROD, 1, 0, 0, 0, 0);
            }
            duration--;
        }
    }

    @Override
    public double getManaCost() {
        return 10;
    }
}
