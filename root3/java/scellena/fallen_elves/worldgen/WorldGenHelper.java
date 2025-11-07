package scellena.fallen_elves.worldgen;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class WorldGenHelper {

    public static Mirror getMirrored(EnumFacing facing){
        Mirror mirror = Mirror.NONE;
        if (facing == null)
        {
        }
        else
        {
            switch (facing)
            {
                case SOUTH:
                case WEST:
                    mirror = Mirror.LEFT_RIGHT;
                    break;
                default:
                    mirror = Mirror.NONE;
            }
        }
        return mirror;
    }

    public static Rotation getRotation(EnumFacing facing){
        Rotation rotation = Rotation.NONE;
        if (facing == null)
        {
        }
        else
        {
            switch (facing)
            {
                case SOUTH:
                    rotation = Rotation.NONE;
                    break;
                case WEST:
                    rotation = Rotation.CLOCKWISE_90;
                    break;
                case EAST:
                    rotation = Rotation.CLOCKWISE_90;
                    break;
                default:
                    rotation = Rotation.NONE;
            }
        }
        return rotation;
    }

}
