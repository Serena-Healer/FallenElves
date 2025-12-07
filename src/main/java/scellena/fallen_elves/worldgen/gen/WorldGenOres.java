package scellena.fallen_elves.worldgen.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.worldgen.DimensionHandler;

import java.util.Random;

public class WorldGenOres implements IWorldGenerator {

    WorldGenerator ORE_ARMOR_GEN;

    public WorldGenOres(){
        super();
        ORE_ARMOR_GEN = new WorldGenMinable(BlockHandler.ORE.getDefaultState(), 3);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int id = world.provider.getDimension() - DimensionHandler.ID_START;
        if(world.provider.getDimension() == 0){
            run(ORE_ARMOR_GEN, world, random, chunkX, chunkZ, 10, 0, 32);
        }
    }

    public void run(WorldGenerator gen, World world, Random rand, int x, int z, int chance, int minHeight, int maxHeight){
        int heightD = maxHeight - minHeight + 1;
        for(int i=0; i<chance; i++){
            int bx = x * 16 + rand.nextInt(16);
            int by = minHeight + rand.nextInt(heightD);
            int bz = z * 16 + rand.nextInt(16);
            gen.generate(world, rand, new BlockPos(bx, by, bz));
        }
    }

}
