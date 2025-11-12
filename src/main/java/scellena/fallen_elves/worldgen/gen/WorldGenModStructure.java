package scellena.fallen_elves.worldgen.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scellena.fallen_elves.worldgen.WorldUtils;

import java.util.*;

public class WorldGenModStructure implements IWorldGenerator {
    Map<Integer, List<StructureGenerator>> DUNGEONS = new HashMap<>();
    StructureGenerator library;

    public static Map<String, Integer> chanceMap = new HashMap<>();

    public WorldGenModStructure(){
        super();

        library = new StructureGenerator("general/library", 0);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 0)
            run(library, world, random, chunkX, chunkZ, 32 * 32, 64, 128);
    }

    public void run(WorldGenerator gen, World world, Random rand, int x, int z, int chance, int minHeight, int maxHeight){
        String name = ((gen instanceof StructureGenerator) ? ((StructureGenerator) gen).name : gen.toString());
        chanceMap.put(name, chance);

        //ストラクチャー名のハッシュ値・ワールドシード・座標から
        //シード値を計算する
        /*
        long seed = world.getSeed() + (x / gap) * 1000000007L + (z / gap) * 998244353L + name.hashCode();
        Random lundi = new Random(seed);

        int px = lundi.nextInt(gap);
        int pz = lundi.nextInt(gap);

        if(x % gap == px && z % gap == pz) {
            int bx = x * 16 + lundi.nextInt(16);
            int bz = z * 16 + lundi.nextInt(16);
            int by = getGenerationHeight(world, bx, bz);
            BlockPos pos = new BlockPos(bx, by, bz);
            gen.generate(world, lundi, pos);
        }
        //if(rand.nextInt(chance) == 0)gen.generate(world, rand, pos);

         */
        BlockPos pos = WorldUtils.getNearestStructurePos(new BlockPos(x * 16, 0, z * 16), world, chance, name);
        if((pos.getX() / 16) == x && (pos.getZ() / 16) == z){
            int by = getGenerationHeight(world, pos.getX(), pos.getZ());
            long seed = world.getSeed() + x * 1000000007L + z * 998244353L + name.hashCode();
            Random lundi = new Random(seed);
            gen.generate(world, lundi, new BlockPos(pos.getX(), by, pos.getZ()));
        }
    }

    public static int getGenerationHeight(World world, int x, int z){
        int y = world.getHeight();
        boolean foundGround = false;
        while(!foundGround && y-- >= 0){
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = block != Blocks.AIR;
        }
        return y;
    }
}
