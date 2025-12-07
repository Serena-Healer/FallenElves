package scellena.fallen_elves.entities.mobs.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.entities.EntitySkillUtils;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.LootTableHandler;
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.spells.SpellAttributes;
import scellena.fallen_elves.util.SkillUtils;

import javax.annotation.Nullable;

public class BossLuminary extends HostileMob implements ICustomEntity{

    public BossLuminary(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getSpeed() {
        return 0.25F;
    }

    @Override
    public double getEffectiveHealth() {
        return 500;
    }

    @Override
    public double getAttackDamage() {
        return 8;
    }

    @Override
    public double getAntiKB() {
        return 0.5;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemHandler.SWORD_ENEMY));
        setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
    }

    int tick = 0;
    int slashTick = 0;
    int dashTick = 0;

    int messageStep = 0;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if(messageStep < 2){
            tick--;
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 100, false, false));
            addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2, 100, false, false));
            addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2, 100, false, false));
            if(tick <= 0) {
                EntitySkillUtils.saySomething(this, "decay.luminary.activate." + (++messageStep));
                tick = 60;
            }
            return;
        }

        if(getAttackTarget() != null){
            tick--;
            if(tick <= 0){
                tick = rand.nextInt(100) + 140;
                int action = rand.nextInt(3);
                switch (action){
                    case 0:
                        EntityCapabilityProvider.getEntityData(this).getSpellObjects().get(new ResourceLocation(FallenElves.MOD_ID, "bullet_light")).onRightClick();
                        break;
                    case 1:
                        slashTick = 60;
                        SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.HOSTILE, 1, 0.5F);
                        break;
                    case 2:
                        dashTick = 100;
                        SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1, 2);
                        break;
                }
            }
        }

        slashTick--;
        if(slashTick >= 0){
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 8, false, false));
        }
        if(slashTick >= 0 && slashTick < 15 && slashTick % 3 == 0){
            SkillUtils.spawnParticleCircleFill(world, getPositionVector(), EnumParticleTypes.SWEEP_ATTACK, 6);
            SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1, 1);
            for(EntityLivingBase living : SkillUtils.getPlayersInArea(world, getPositionVector(), 6)){
                living.attackEntityFrom(EntityDamageSource.causeMobDamage(this), (float) (getAttackDamage() * 3));
            }
        }

        dashTick--;
        if(dashTick >= 0){
            SkillUtils.spawnParticleFromServer(world, getPositionVector(), EnumParticleTypes.SMOKE_NORMAL, 1, 0.1, 0.1, 0.1, 0);
            SkillUtils.spawnParticleFromServer(world, getPositionVector(), EnumParticleTypes.EXPLOSION_NORMAL, 1, 0.1, 0.1, 0.1, 0);
            addPotionEffect(new PotionEffect(MobEffects.SPEED, 2, 2, false, false));
            addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2, 1, false, false));
            addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2, 0, false, false));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        SpellAttributes.init(this);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MIGHT).setBaseValue(70);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MENDING).setBaseValue(70);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.LUMINARY;
    }
}
