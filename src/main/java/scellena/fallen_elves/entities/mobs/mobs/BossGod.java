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
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.spells.SpellAttributes;
import scellena.fallen_elves.util.SkillUtils;

public class BossGod extends HostileMob implements ICustomEntity{

    public BossGod(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getSpeed() {
        return 0.30F;
    }

    @Override
    public double getEffectiveHealth() {
        return 999;
    }

    @Override
    public double getAttackDamage() {
        return 19;
    }

    @Override
    public double getAntiKB() {
        return 0.9;
    }

    int tick = 0;
    int paradiseLostTick = 0;
    int dashTick = 0;

    int messageStep = 0;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if(messageStep < 4){
            tick--;
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 100, false, false));
            addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2, 100, false, false));
            if(tick <= 0) {
                EntitySkillUtils.saySomething(this, "decay.god.activate." + (++messageStep));
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
                        paradiseLostTick = 20 * 9;
                        break;
                    case 2:
                        dashTick = 100;
                        SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1, 2);
                        break;
                }
            }
        }

        paradiseLostTick--;
        if(paradiseLostTick >= 0){
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 8, false, false));
            SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 0.4F, 2.0F - (paradiseLostTick / 140F) * 1.5F);
            SkillUtils.spawnParticleCircle(world, getPositionVector(), EnumParticleTypes.END_ROD, 25);
        }
        if(paradiseLostTick >= 0 && paradiseLostTick < 20 && paradiseLostTick % 2 == 0){
            SkillUtils.spawnParticleCircleFill(world, getPositionVector().add(0, (20 - paradiseLostTick) * 0.2, 0), EnumParticleTypes.VILLAGER_HAPPY, 22);
            SkillUtils.spawnParticleCircleFill(world, getPositionVector().add(0, (20 - paradiseLostTick) * 0.2, 0), EnumParticleTypes.END_ROD, 22);
            SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.HOSTILE, 1, 2);
            for(EntityLivingBase living : SkillUtils.getPlayersInArea(world, getPositionVector(), 22)){
                living.attackEntityFrom(new EntityDamageSource("light", this).setDamageBypassesArmor(), 998244353);
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
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemHandler.WAND_GOD));
        setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SpellAttributes.MAGICAL_MIGHT).setBaseValue(200);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MENDING).setBaseValue(200);
    }
}
