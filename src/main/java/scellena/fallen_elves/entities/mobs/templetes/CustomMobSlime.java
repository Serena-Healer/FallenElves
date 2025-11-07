package scellena.fallen_elves.entities.mobs.templetes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scellena.fallen_elves.entities.EntitySkillUtils;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.IEntityBase;

public abstract class CustomMobSlime extends EntitySlime implements IEntityBase {
    public CustomMobSlime(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean getCanSpawnHere() {
        return EntitySkillUtils.canMobSpawnOn(this, getPosition()) && super.getCanSpawnHere();
    }

    @Override
    protected int getAttackStrength() {
        if(this instanceof ICustomEntity){
            return (int) ((ICustomEntity)this).getAttackDamage();
        }
        return super.getAttackStrength();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25);
        if(this instanceof ICustomEntity){
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.min(1000, ((ICustomEntity)this).getEffectiveHealth()));
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Math.min(1000, ((ICustomEntity)this).getAntiKB()));
        }
    }

    public abstract float getSpeed();

    @Override
    protected boolean canDamagePlayer() {
        return true;
    }
}
