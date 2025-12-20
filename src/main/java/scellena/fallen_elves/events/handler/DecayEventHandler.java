package scellena.fallen_elves.events.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;
import scellena.fallen_elves.potions.PotionsHandler;
import scellena.fallen_elves.spells.SpellHelper;

public class DecayEventHandler {

    @SubscribeEvent
    public void onKillEntity(LivingDeathEvent event){
        DamageSource source = event.getSource();
        EntityLivingBase target = event.getEntityLiving();
        if(source instanceof EntityDamageSource){
            Entity attackedIn = source.getTrueSource();
            if(attackedIn instanceof EntityLivingBase){
                if(target instanceof EntityMob && !(target instanceof HostileMob)){
                    if(((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.ALLIANCE_2) != null) {
                        attackedIn.attackEntityFrom(new DamageSource("alliance_violation").setDamageBypassesArmor(), 998244353);
                        ((EntityLivingBase) attackedIn).removeActivePotionEffect(PotionsHandler.ALLIANCE_2);
                    } else if(((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.ALLIANCE) != null){
                        attackedIn.attackEntityFrom(DamageSource.WITHER, 2);
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 10, 0));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 10, 0));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 * 10, 2));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 * 10, 2));
                    }
                }else {

                    ((EntityLivingBase) attackedIn).removeActivePotionEffect(PotionsHandler.KILL_DESIRE);

                    //1.1.1
                    EntityCapabilityProvider.getEntityData(attackedIn).collectXP(SpellHelper.getXPRequired(EntityCapabilityProvider.getEntityData(attackedIn).getCurrentLevel() + 1) * 0.75);
                }
            }
        }
    }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event){
        DamageSource source = event.getSource();
        EntityLivingBase target = event.getEntityLiving();
        if(source instanceof EntityDamageSource){
            Entity attackedIn = source.getTrueSource();
            if(attackedIn instanceof EntityLivingBase){
                if(source.isMagicDamage()) {
                    PotionEffect effect = ((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.MIGHT);
                    if(effect != null){
                        event.setAmount(event.getAmount() * (1.0F + effect.getAmplifier() / 100.0F));
                    }
                }else{
                    PotionEffect effect = ((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.STRENGTH);
                    if(effect != null){
                        event.setAmount(event.getAmount() * (1.0F + effect.getAmplifier() / 100.0F));
                    }
                }
            }
        }
    }

}
