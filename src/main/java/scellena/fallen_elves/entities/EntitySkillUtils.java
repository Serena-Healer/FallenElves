package scellena.fallen_elves.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.text.translation.I18n;

public class EntitySkillUtils {

    /**
     * ボス名義で何か喋らせる
     * @param entity 喋らせるボス
     * @param str 文字列(ただしTranslationKey)
     */
    public static void saySomething(Entity entity, String str){
        if(!entity.world.isRemote) {
            entity.getEntityWorld().getEntities(EntityPlayer.class, entityPlayer -> true).forEach(e -> {
                if (e != null && entity.getDistance(e) <= 50) {
                    if(I18n.canTranslate(str)) {
                        e.sendMessage(new TextComponentString(entity.getDisplayName().getUnformattedText() + "「").appendSibling(new TextComponentTranslation(str)));
                    }else{
                        System.out.println("Translation key " + str + " not found, skipping");
                        System.out.println("(This may be an intended when the mob won't speak)");
                    }
                }
            });
        }
    }

    public static void sendMessageToAll(Entity entity, String str){
        if(!entity.world.isRemote) {
            entity.getEntityWorld().getEntities(EntityPlayer.class, entityPlayer -> true).forEach(e -> {
                if (e != null && entity.getDistance(e) <= 50) {
                    e.sendMessage(new TextComponentTranslation(str));
                }
            });
        }
    }

    public static void stopForTicks(EntityLivingBase entity, int time){
        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, time, 9, false, false));
    }

    public static final List<Block> fieldBlocks = Arrays.asList(Blocks.GRASS, Blocks.SAND, Blocks.NETHERRACK, Blocks.NETHER_BRICK, Blocks.STONE, Blocks.END_STONE, Blocks.SNOW_LAYER, Blocks.SNOW);

    public static boolean canMobSpawnOn(Entity entityIn, BlockPos pos){
        return fieldBlocks.contains(entityIn.world.getBlockState(pos.down()).getBlock()) || entityIn.world.getLight(pos) < 8;
    }
}