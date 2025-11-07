package scellena.fallen_elves.entities.mobs.templetes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scellena.fallen_elves.entities.EntitySkillUtils;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.IEntityBase;

public abstract class CustomMobGuardian extends EntityGuardian implements IEntityBase {
    public CustomMobGuardian(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean getCanSpawnHere() {
        return EntitySkillUtils.canMobSpawnOn(this, getPosition()) && super.getCanSpawnHere();
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25);
        if(this instanceof ICustomEntity){
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.min(1000, ((ICustomEntity)this).getEffectiveHealth()));
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Math.min(1000, ((ICustomEntity)this).getAttackDamage()));
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Math.min(1000, ((ICustomEntity)this).getAntiKB()));
        }
    }

    public abstract float getSpeed();
}
