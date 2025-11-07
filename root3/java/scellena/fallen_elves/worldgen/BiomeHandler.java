package scellena.fallen_elves.worldgen;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeHandler {

    public static List<ICustomBiome> allBiomes = new ArrayList<>();

    public static Map<net.minecraft.world.biome.Biome, Integer> getDimensionMap = new HashMap<>();

    public static void init(){
    }

    public static net.minecraft.world.biome.Biome register(String name, ICustomBiome biome, int id){
        net.minecraft.world.biome.Biome b = (net.minecraft.world.biome.Biome) biome;
        b.setRegistryName(name);
        allBiomes.add(biome);
        ForgeRegistries.BIOMES.register(b);
        BiomeDictionary.addTypes(b, biome.getType());
        BiomeManager.addBiome(biome.getBiomeType(), new BiomeManager.BiomeEntry(b, 1));
        BiomeManager.addSpawnBiome(b);
        getDimensionMap.put((net.minecraft.world.biome.Biome) biome, id);
        return b;
    }

}
