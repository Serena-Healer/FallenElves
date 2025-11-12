package scellena.fallen_elves.worldgen.gen;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.material.wood.BlockLogs;
import scellena.fallen_elves.blocks.material.wood.WoodSet;
import scellena.fallen_elves.worldgen.DimensionHandler;

import java.util.Random;

public class WorldGenTree implements IWorldGenerator {

    public static WorldGenAbstractTree LOG_1;

    public WorldGenTree() {
        super();
        LOG_1 = new TreeGenerator(BlockHandler.LOGS.getDefaultState().withProperty(BlockLogs.TYPE, WoodSet.EnumWoodTypes.MAGIC).withProperty(BlockLogs.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.LEAFS.getDefaultState().withProperty(BlockLogs.TYPE, WoodSet.EnumWoodTypes.MAGIC).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE), true);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 0){
            run(LOG_1, world, random, chunkX, chunkZ, 0.0003, 60, 128);
        }
    }

    public void run(WorldGenerator gen, World world, Random rand, int x, int z, double chance, int minHeight, int maxHeight) {
        int heightD = maxHeight - minHeight + 1;
        for(int i=0; i<chance + Math.random(); i++){
            int bx = x * 16 + rand.nextInt(16);
            int bz = z * 16 + rand.nextInt(16);
            int by = world.getHeight(bx, bz);
            gen.generate(world, rand, new BlockPos(bx, by, bz));
        }
    }

}