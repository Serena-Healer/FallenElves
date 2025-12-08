package scellena.fallen_elves.worldgen.gen;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GeneratorHandler {

    public static void init(){
        register(new WorldGenOres());
        register(new WorldGenModStructure());
        register(new WorldGenTree());
        //register(new WorldGenFlower());
    }

    public static IWorldGenerator register(IWorldGenerator gen){
        GameRegistry.registerWorldGenerator(gen, 0);
        return gen;
    }

}
