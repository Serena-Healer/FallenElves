package scellena.fallen_elves.decay;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.potions.PotionsHandler;

import java.util.Random;

public class DecayTicker {

    public static void onTick(Entity entity){
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(entity);
        int level = data.getCurrentLevel();
        int tick = data.getRandomEventTick();
        EntityLivingBase living = null;
        if(entity instanceof EntityLivingBase){
            living = (EntityLivingBase) entity;
            if(living.getActivePotionEffect(PotionsHandler.KILL_DESIRE) != null){
                living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2, 0));
                living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 2, 2));
                living.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2, 0));
                living.addPotionEffect(new PotionEffect(MobEffects.POISON, 2, 0));
            }
        }

        Random random = new Random();
        if(tick == 0 && level >= 5){
            int type = Math.min(random.nextInt(level / 5), 6);
            ITextComponent text;
            String key = "";
            switch (type){
                case 0:
                    key = "decay.overridden.activate";
                    living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 60, 2));
                    living.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 60, 0));
                    living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 60, 0));
                    living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 10, 0));
                    break;
                case 1:
                    key = "decay.hostile.activate";
                    living.addPotionEffect(new PotionEffect(PotionsHandler.KILL_DESIRE, 20 * 20, 1));
                    break;
                case 2:
                    key = "decay.alliance.activate";
                    living.addPotionEffect(new PotionEffect(PotionsHandler.ALLIANCE, 20 * 20, 0));
                    break;
                case 4:
                    key = "decay.alliance_2.activate";
                    living.addPotionEffect(new PotionEffect(PotionsHandler.ALLIANCE_2, 20 * 20, 0));
                    break;
            }
            text = new TextComponentTranslation(key);
            text.setStyle(text.getStyle().setColor(TextFormatting.DARK_PURPLE).setBold(true));
            entity.sendMessage(text);
        }
        if(tick < 0 && level >= 5){
            data.setRandomEventTick((int) ((random.nextDouble() * 0.4 + 0.8) * (20 * 60 * Math.max(1, 30 - level))));
        }
    }

}
