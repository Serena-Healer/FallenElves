package scellena.fallen_elves.entities.mobs.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
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
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BossGod extends HostileMob implements ICustomEntity{

    public BossGod(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getSpeed() {
        return 0.25F;
    }

    @Override
    public double getEffectiveHealth() {
        return 999;
    }

    @Override
    public double getAttackDamage() {
        return 20;
    }

    @Override
    public double getAntiKB() {
        return 0.5;
    }

    int tick = 0;
    int paradiseLostTick = 0;
    int lightningTick = 0;
    List<Vec3d> lightningPos = new ArrayList<>();

    int messageStep = 0;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if(messageStep < 4){
            tick--;
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 100, false, false));
            addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2, 100, false, false));
            addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2, 100, false, false));
            if(tick <= 0) {
                EntitySkillUtils.saySomething(this, "decay.god.activate." + (++messageStep));
                tick = 60;
            }
            return;
        }
        if(getAttackTarget() != null){
            tick--;
            if(tick <= 0){
                tick = rand.nextInt(100) + 190;
                int action = rand.nextInt(3);
                switch (action){
                    case 0:
                        EntityCapabilityProvider.getEntityData(this).getSpellObjects().get(new ResourceLocation(FallenElves.MOD_ID, "bullet_light")).onRightClick();
                        break;
                    case 1:
                        paradiseLostTick = 20 * 9;
                        break;
                    case 2:
                        lightningTick = 90;
                        lightningPos = new ArrayList<>();
                        break;
                }
            }
        }

        paradiseLostTick--;
        if(paradiseLostTick >= 0){
            SkillUtils.spawnParticleFromServer(world, getPositionVector(), EnumParticleTypes.END_ROD, 10, 0, 0, 0, 1);
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 8, false, false));
            SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 2F, 2.0F - (paradiseLostTick / 140F) * 1.5F);
            SkillUtils.spawnParticleCircle(world, getPositionVector(), EnumParticleTypes.END_ROD, 22);
        }
        if(paradiseLostTick >= 0 && paradiseLostTick < 20 && paradiseLostTick % 2 == 0){
            SkillUtils.spawnParticleCircleFill(world, getPositionVector().add(0, (20 - paradiseLostTick) * 0.2, 0), EnumParticleTypes.VILLAGER_HAPPY, 22);
            SkillUtils.spawnParticleCircleFill(world, getPositionVector().add(0, (20 - paradiseLostTick) * 0.2, 0), EnumParticleTypes.END_ROD, 22);
            SkillUtils.playSoundFromServer(world, getPositionVector(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.HOSTILE, 1, 2);
            for(EntityLivingBase living : SkillUtils.getPlayersInArea(world, getPositionVector(), 22)){
                living.attackEntityFrom(new EntityDamageSource("light", this).setDamageBypassesArmor(), 998244353);
            }
        }

        lightningTick--;
        if(lightningTick >= 0){
            if(lightningTick > 60){
                if(getAttackTarget() != null) {
                    lightningPos.add(getAttackTarget().getPositionVector());
                    SkillUtils.playSoundFromServer(world, getAttackTarget().getPositionVector(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 0.4F, 1.0F);
                }
            }
            if(lightningTick < 30){
                if(lightningPos.size() > 0) {
                    Vec3d pos = lightningPos.remove(0);
                    EntityLightningBolt lightning = new EntityLightningBolt(getEntityWorld(), pos.x, pos.y, pos.z, true);
                    getEntityWorld().addWeatherEffect(lightning);
                    for (EntityLivingBase living : SkillUtils.getPlayersInArea(world, pos, 1)) {
                        living.attackEntityFrom(new EntityDamageSource(DamageSource.LIGHTNING_BOLT.getDamageType(), this), 15);
                    }
                }
            }
            for(Vec3d pos : lightningPos){
                SkillUtils.spawnParticleFromServer(world, pos, EnumParticleTypes.END_ROD, 1, 0.1, 0.1, 0.1, 0);
            }
            addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2, 100, false, false));
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
        SpellAttributes.init(this);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MIGHT).setBaseValue(200);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MENDING).setBaseValue(200);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GOD;
    }
}
