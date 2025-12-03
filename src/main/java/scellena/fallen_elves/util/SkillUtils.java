package scellena.fallen_elves.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SkillUtils {

    public static List<EntityLivingBase> getEntitiesInArea(World world, Vec3d location, float distance){
        List<EntityLivingBase> list = new ArrayList<>();
        world.getLoadedEntityList().forEach(e -> {
            if(e instanceof EntityLivingBase){
                if(e.getEntityWorld() == world && getDistanceFromBox(location, e.getEntityBoundingBox()) <= distance){
                    list.add((EntityLivingBase) e);
                }
            }
        });
        return list;
    };

    public static List<EntityPlayer> getPlayersInArea(World world, Vec3d location, float distance) {
        List<EntityPlayer> list = new ArrayList<>();
        getEntitiesInArea(world, location, distance).forEach(e -> {
            if(e instanceof EntityPlayer){
                list.add((EntityPlayer) e);
            }
        });
        return list;
    }

    public static List<EntityLivingBase> getAlliesInArea(World world, Vec3d location, float distance) {
        List<EntityLivingBase> list = new ArrayList<>();
        getEntitiesInArea(world, location, distance).forEach(e -> {
            if(e instanceof EntityPlayer){
                list.add((EntityLivingBase) e);
            }
        });
        return list;
    }

    public static List<EntityLivingBase> getEnemiesInArea(World world, Vec3d location, float distance) {
        List<EntityLivingBase> list = new ArrayList<>();
        getEntitiesInArea(world, location, distance).forEach(e -> {
            if(!(e instanceof EntityPlayer)){
                list.add(e);
            }
        });
        return list;
    }

    public static EntityPlayer getNearestPlayer(World world, Vec3d location, float distance){
        AtomicReference<EntityPlayer> pl = new AtomicReference<>();
        AtomicReference<Double> dist = new AtomicReference<>((double) distance);
        getPlayersInArea(world, location, distance).forEach(p -> {
            double d = getDistanceFromBox(location, p.getEntityBoundingBox());
            if(d <= dist.get()){
                dist.set(d);
                pl.set(p);
            }
        });
        return pl.get();
    }

    public static EntityLivingBase getNearestEnemy(World world, Vec3d location, float distance){
        AtomicReference<EntityLivingBase> pl = new AtomicReference<>();
        AtomicReference<Double> dist = new AtomicReference<>((double) distance);
        getEnemiesInArea(world, location, distance).forEach(p -> {
            double d = getDistanceFromBox(location, p.getEntityBoundingBox());
            if(d <= dist.get()){
                dist.set(d);
                pl.set(p);
            }
        });
        return pl.get();
    }

    public static double getDistanceFromBox(Vec3d vec, AxisAlignedBB hitBox){
        double dist = Math.pow(10, 9);
        double posX = vec.x;
        double posY = vec.y;
        double posZ = vec.z;
        double boxXmin = hitBox.minX;
        double boxYmin = hitBox.minY;
        double boxZmin = hitBox.minZ;
        double boxXmax = hitBox.maxX;
        double boxYmax = hitBox.maxY;
        double boxZmax = hitBox.maxZ;
        boolean isXin = (boxXmin <= posX && posX <= boxXmax);
        boolean isYin = (boxYmin <= posY && posY <= boxYmax);
        boolean isZin = (boxZmin <= posZ && posZ <= boxZmax);

        int cnt = 0;
        if(isXin)cnt++;
        if(isYin)cnt++;
        if(isZin)cnt++;

        switch(cnt){
            case 0: //各頂点からの距離の最大値を求める
                for(int i=0; i<2; i++){
                    for(int j=0; j<2; j++){
                        for(int k=0; k<2; k++){
                            double pX = (i == 0 ? boxXmin : boxXmax);
                            double pY = (j == 0 ? boxYmin : boxYmax);
                            double pZ = (k == 0 ? boxZmin : boxZmax);
                            dist = Math.min(dist, Math.sqrt(Math.pow(posX - pX, 2) + Math.pow(posY - pY, 2) + Math.pow(posZ - pZ, 2)));
                        }
                    }
                }
                break;
            case 1: //各辺からの距離の最大値を求める
                if(isXin){
                    for(int i=0; i<2; i++){
                        for(int j=0; j<2; j++){
                            double pY = (i == 0 ? boxYmin : boxYmax);
                            double pZ = (j == 0 ? boxZmin : boxZmax);
                            dist = Math.min(dist, Math.sqrt(Math.pow(posY - pY, 2) + Math.pow(posZ - pZ, 2)));
                        }
                    }
                }
                if(isYin){
                    for(int i=0; i<2; i++){
                        for(int j=0; j<2; j++){
                            double pX = (i == 0 ? boxXmin : boxXmax);
                            double pZ = (j == 0 ? boxZmin : boxZmax);
                            dist = Math.min(dist, Math.sqrt(Math.pow(posX - pX, 2) + Math.pow(posZ - pZ, 2)));
                        }
                    }
                }
                if(isZin){
                    for(int i=0; i<2; i++){
                        for(int j=0; j<2; j++){
                            double pX = (i == 0 ? boxXmin : boxXmax);
                            double pY = (j == 0 ? boxYmin : boxYmax);
                            dist = Math.min(dist, Math.sqrt(Math.pow(posX - pX, 2) + Math.pow(posY - pY, 2)));
                        }
                    }
                }
                break;
            case 2: //各面からの距離の最大値を求める
                if(!isXin){
                    dist = Math.min(dist, Math.abs(posX - boxXmax));
                    dist = Math.min(dist, Math.abs(posX - boxXmin));
                }
                if(!isYin){
                    dist = Math.min(dist, Math.abs(posY - boxYmax));
                    dist = Math.min(dist, Math.abs(posY - boxYmin));
                }
                if(!isZin){
                    dist = Math.min(dist, Math.abs(posZ - boxZmax));
                    dist = Math.min(dist, Math.abs(posZ - boxZmin));
                }
                break;
            case 3:
                dist = 0;
                break;
        }
        return dist;
    }

    /**
     * サーバー側からParticleをコールする
     */
    public static void spawnParticleFromServer(World world, Vec3d location, EnumParticleTypes particle, int count, double offX, double offY, double offZ, double speed){
        if(world instanceof WorldServer){
            ((WorldServer) world).spawnParticle(particle, location.x, location.y, location.z, count, offX, offY, offZ, speed);
        }
    }

    /**
     * 円形にパーティクルを出現させる。
     * @param world ワールド
     * @param location 中心座標
     * @param particle パーティクル種類
     * @param r 半径
     */
    public static void spawnParticleCircle(World world, Vec3d location, EnumParticleTypes particle, double r){
        double cnt = r * 20;
        for(int i=0; i<cnt; i++){
            double t = ((double)i) / cnt * Math.PI * 2;
            Vec3d pos = location.add(Math.sin(t) * r, 0.2, Math.cos(t) * r);
            spawnParticleFromServer(world, pos, particle, 1, 0, 0, 0, 0);
        }
    }

    /**
     * 円形にパーティクルを出現させる。中を埋める。
     * @param world ワールド
     * @param location 中心座標
     * @param particle パーティクル種類
     * @param r 半径
     */
    public static void spawnParticleCircleFill(World world, Vec3d location, EnumParticleTypes particle, double r){
        for(double k=0; k<r; k+=0.5){
            spawnParticleCircle(world, location, particle, k);
        }
    }

    /**
     * 円形にパーティクルを出現させる。中を埋める。
     * @param world ワールド
     * @param location 中心座標
     * @param particle パーティクル種類
     */
    public static void spawnParticleLine(World world, Vec3d location, Vec3d location2, EnumParticleTypes particle){
        Vec3d v = (location2.subtract(location).normalize().scale(0.5));
        Vec3d v2 = location;
        for(double k=0; k<(location2.distanceTo(location)); k+=0.5){
            v2 = v2.add(v);
            spawnParticleFromServer(world, v2, particle, 1, 0, 0, 0, 0);
        }
    }

    /**
     * サーバー側からPlaysoundをコールする
     */
    public static void playSoundFromServer(World world, Vec3d location, SoundEvent sound, SoundCategory category, float volume, float pitch){
        world.playSound(null, location.x, location.y, location.z, sound, category, volume, pitch);
    }

    /**
     * 向いている方向にノックバックさせる
     * @param dealer 攻撃者
     * @param target 対象
     * @param strength ノックバックの強さ(参考: ノックバック 1 = 0.5)
     */
    public static void knockbackFacingDirection(Entity dealer, Entity target, float strength){
        double d1 = target.motionX;
        double d2 = target.motionY;
        double d3 = target.motionZ;

        if (target instanceof EntityLivingBase)
        {
            ((EntityLivingBase)target).knockBack(dealer, strength, (double) MathHelper.sin(dealer.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(dealer.rotationYaw * 0.017453292F)));
        }
        else
        {
            target.addVelocity((double)(-MathHelper.sin(dealer.rotationYaw * 0.017453292F) * strength), 0.1D, (double)(MathHelper.cos(dealer.rotationYaw * 0.017453292F) * strength));
        }

        if (target instanceof EntityPlayerMP && target.velocityChanged)
        {
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
            target.velocityChanged = false;
            target.motionX = d1;
            target.motionY = d2;
            target.motionZ = d3;
        }

    }

}
