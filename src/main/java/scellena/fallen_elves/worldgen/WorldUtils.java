package scellena.fallen_elves.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldUtils {

    public static ITextComponent getLocationString(BlockPos from, BlockPos to){
        int dx = to.getX() - from.getX();
        String text1 = (dx < 0 ? "message.west" : "message.east");
        int dz = to.getZ() - from.getZ();
        String text2 = (dz < 0 ? "message.north" : "message.south");
        return new TextComponentTranslation("message.direction", new TextComponentTranslation(text1), ((int)(Math.abs(dx))), new TextComponentTranslation(text2), ((int)(Math.abs(dz))));
    }

    public static BlockPos getNearestStructurePos(BlockPos center, World world, int chance, String name){
        int gap = (int)(Math.round(Math.sqrt(chance)));
        int x_i = ((int)(center.getX() / 16 / gap)) * gap;
        int z_i = ((int)(center.getZ() / 16 / gap)) * gap;
        BlockPos ans = null;
        List<BlockPos> all = new ArrayList<>();

        //周囲 9 区画のみ捜索すればOK
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                int x = x_i + ((i - 1) * gap);
                int z = z_i + ((j - 1) * gap);
                long seed = world.getSeed() + (x / gap) * 1000000007L + (z / gap) * 998244353L + name.hashCode();
                Random lundi = new Random(seed);
                x += lundi.nextInt(gap);
                z += lundi.nextInt(gap);

                int bx = x * 16 + lundi.nextInt(16);
                int bz = z * 16 + lundi.nextInt(16);
                int by = 0;
                all.add(new BlockPos(bx, by, bz));
            }
        }

        for(Object loc : all.toArray()){
            BlockPos pos = (BlockPos) loc;
            if(ans == null || ans.distanceSq(center) > ((BlockPos) loc).distanceSq(center)){
                ans = pos;
            }
        }

        return ans;
    }

}
