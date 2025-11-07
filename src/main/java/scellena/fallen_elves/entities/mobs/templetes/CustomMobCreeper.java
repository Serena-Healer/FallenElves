package scellena.fallen_elves.entities.mobs.templetes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scellena.fallen_elves.entities.EntitySkillUtils;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.IEntityBase;

public abstract class CustomMobCreeper extends EntityCreeper implements IEntityBase {

    private int timeSinceIgnited;
    private int fuseTime = 30;

    public CustomMobCreeper(World worldIn) {
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
            //this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Math.min(1000, ((ICustomEntity)this).getAttackDamage()));
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Math.min(1000, ((ICustomEntity)this).getAntiKB()));
        }
    }

    public abstract float getSpeed();

    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            if (this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.onExplode();
            }
        }

        super.onUpdate();
    }

    public void onExplode(){
        System.out.println("Creeper Exploded!");
    }

}
