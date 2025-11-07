package scellena.fallen_elves.util.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class SimpleDimensionTeleporter implements ITeleporter {

    Vec3d vec;
    float yaw;
    float pitch;

    public SimpleDimensionTeleporter(Vec3d pos, float yaw, float pitch){
        vec = pos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        entity.setLocationAndAngles(vec.x, vec.y, vec.z, this.yaw, this.pitch);
    }

}
