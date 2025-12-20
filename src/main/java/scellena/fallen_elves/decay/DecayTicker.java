package scellena.fallen_elves.decay;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.entities.mobs.mobs.BossGod;
import scellena.fallen_elves.entities.mobs.mobs.BossLuminary;
import scellena.fallen_elves.entities.mobs.mobs.ElfHostile;
import scellena.fallen_elves.potions.PotionsHandler;
import scellena.fallen_elves.util.SkillUtils;
import scellena.fallen_elves.util.Utilities;

import java.util.Random;

public class DecayTicker {

    public static void onTick(Entity entity){
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(entity);
        int level = data.getCurrentLevel() + 1;
        int tick = data.getRandomEventTick();
        EntityLivingBase living = null;
        if(entity instanceof EntityLivingBase){
            living = (EntityLivingBase) entity;

            /*
            if(living.getActivePotionEffect(PotionsHandler.KILL_DESIRE) != null){
                living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2, 0));
                living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 2, 2));
                living.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2, 0));
                living.addPotionEffect(new PotionEffect(MobEffects.POISON, 2, 0));
            }
             */
        }

        Random random = new Random();
        if(tick <= 0 && level >= 5){
            boolean ac = false;
            while(!ac) {
                ac = true;
                int type = Math.min(random.nextInt(level / 5 + (level >= 10 ? 1 : 0)), 6);
                ITextComponent text;
                String key = "";
                boolean bossFlag = false;
                for (EntityLivingBase e2 : SkillUtils.getEntitiesInArea(living.world, entity.getPositionVector(), 100)) {
                    if (e2 instanceof BossGod || e2 instanceof BossLuminary) bossFlag = true;
                }
                switch (type) {
                    case 0:
                        key = "decay.overridden.activate";
                        living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 30, 2));
                        living.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 30, 0));
                        living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 30, 0));
                        living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 10, 0));
                        living.addPotionEffect(new PotionEffect(PotionsHandler.DECAY_1, 20 * 30, 0));
                        break;
                    case 1:
                        key = "decay.hostile.activate";
                        living.addPotionEffect(new PotionEffect(PotionsHandler.KILL_DESIRE, 20 * 180, 1));
                        break;
                    case 2:
                        key = "decay.elf.activate";
                        int cnt = (4 + (level - 10) / 5);
                        for (int i = 0; i < cnt; i++) {
                            double arg = Math.PI * 2 * i / ((double) cnt);
                            BlockPos pos = living.getPosition().add(Math.sin(arg) * 12, 0, Math.cos(arg) * 12);
                            pos = Utilities.getTopPos(living.world, pos);
                            ElfHostile elf = new ElfHostile(living.getEntityWorld());
                            elf.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                            elf.onInitialSpawn(living.getEntityWorld().getDifficultyForLocation(living.getPosition()), null);
                            living.getEntityWorld().spawnEntity(elf);
                        }
                        break;
                    case 3:
                        key = "decay.alliance.activate";
                        living.addPotionEffect(new PotionEffect(PotionsHandler.ALLIANCE, 20 * 300, 0));
                        break;
                    case 4:
                        if (bossFlag) {
                            ac = false;
                        } else {
                            EntityMob boss = new BossLuminary(living.getEntityWorld());
                            BlockPos pos = living.getPosition();
                            pos = Utilities.getTopPos(living.world, pos);
                            boss.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                            boss.onInitialSpawn(living.getEntityWorld().getDifficultyForLocation(living.getPosition()), null);
                            living.getEntityWorld().spawnEntity(boss);
                        }
                        break;
                    case 5:
                        key = "decay.alliance_2.activate";
                        living.addPotionEffect(new PotionEffect(PotionsHandler.ALLIANCE_2, 20 * 30, 0));
                        break;
                    case 6:
                        if (bossFlag) {
                            ac = false;
                        } else {
                            EntityMob boss = new BossGod(living.getEntityWorld());
                            BlockPos pos = living.getPosition();
                            pos = Utilities.getTopPos(living.world, pos);
                            boss.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                            boss.onInitialSpawn(living.getEntityWorld().getDifficultyForLocation(living.getPosition()), null);
                            living.getEntityWorld().spawnEntity(boss);
                        }
                        break;
                }
                if (key.length() > 0) {
                    text = new TextComponentTranslation(key);
                    //text.setStyle(text.getStyle().setColor(TextFormatting.DARK_PURPLE).setBold(true));
                    entity.sendMessage(text);
                }
            }
            data.setRandomEventTick((int) ((random.nextDouble() * 0.4 + 0.8) * (20 * 30 * Math.max(10, 40 - level))));
        }
    }

}
