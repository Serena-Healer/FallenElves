package scellena.fallen_elves.events.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;
import scellena.fallen_elves.potions.PotionsHandler;

public class DecayEventHandler {

    @SubscribeEvent
    public void onKillEntity(LivingDeathEvent event){
        DamageSource source = event.getSource();
        EntityLivingBase target = event.getEntityLiving();
        if(source instanceof EntityDamageSource){
            Entity attackedIn = source.getTrueSource();
            if(attackedIn instanceof EntityLivingBase){
                if(target instanceof EntityMob && !(target instanceof HostileMob)){
                    if(((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.ALLIANCE) != null) {
                        attackedIn.attackEntityFrom(new DamageSource("alliance_violation"), 998244353);
                    } else if(((EntityLivingBase) attackedIn).getActivePotionEffect(PotionsHandler.ALLIANCE) != null){
                        attackedIn.attackEntityFrom(DamageSource.WITHER, 2);
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 10, 0));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 10, 0));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 * 10, 2));
                        ((EntityLivingBase) attackedIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 * 10, 2));
                    }
                }else {
                    ((EntityLivingBase) attackedIn).removeActivePotionEffect(PotionsHandler.KILL_DESIRE);
                }
            }
        }
    }

}
