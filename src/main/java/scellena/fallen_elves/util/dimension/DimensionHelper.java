package scellena.fallen_elves.util.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class DimensionHelper {

    /**
     * エンティティを指定ディメンションの指定座標に移動させる。
     * @param entity エンティティ
     * @param dimension ディメンションID(素の値)
     * @param location 座標
     */
    public static void placePlayerOnFixedCoordinate(Entity entity, int dimension, Vec3d location){
        if(entity.dimension != dimension) {
            entity.changeDimension(dimension, new SimpleDimensionTeleporter(location, 0, 0));
        } else {
            entity.setLocationAndAngles(location.x, location.y, location.z, 0, 0);
        }
    }

}

