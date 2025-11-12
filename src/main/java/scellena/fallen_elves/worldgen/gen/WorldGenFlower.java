package scellena.fallen_elves.worldgen.gen;

import net.minecraft.block.BlockFlower;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.worldgen.DimensionHandler;

import java.util.Random;

public class WorldGenFlower implements IWorldGenerator {

    WorldGenerator ROBE_FLOWER;

    public WorldGenFlower() {
        super();
        ROBE_FLOWER = new WorldGenFlowers((BlockFlower) BlockHandler.FLOWER, BlockFlower.EnumFlowerType.POPPY);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int id = world.provider.getDimension() - DimensionHandler.ID_START;
        if(id >= 0 && id <= 10){
            run(ROBE_FLOWER, world, random, chunkX, chunkZ, 200, 60, 128);
        }
    }

    public void run(WorldGenerator gen, World world, Random rand, int x, int z, int chance, int minHeight, int maxHeight) {
        int heightD = maxHeight - minHeight + 1;
        if(rand.nextInt(chance) == 0){
            int bx = x * 16 + rand.nextInt(16);
            int bz = z * 16 + rand.nextInt(16);
            int by = world.getHeight(bx, bz);
            gen.generate(world, rand, new BlockPos(bx, by, bz));
        }
    }

}