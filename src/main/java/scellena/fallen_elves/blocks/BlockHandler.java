package scellena.fallen_elves.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import scellena.fallen_elves.blocks.decayed.DecayedDirtBlock;
import scellena.fallen_elves.blocks.decayed.DecayedGrassBlock;
import scellena.fallen_elves.blocks.decayed.DecayedSandBlock;
import scellena.fallen_elves.blocks.fluids.FluidBlock;
import scellena.fallen_elves.blocks.fluids.FluidsHandler;
import scellena.fallen_elves.blocks.material.crops.BlockRobeFlower;
import scellena.fallen_elves.blocks.material.ore.BlockMagicOre;
import scellena.fallen_elves.blocks.material.wood.*;
import scellena.fallen_elves.blocks.spell.SpellSetBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockHandler {

    public static List<IBlockBase> allBlocks = new ArrayList<>();

    public static Block PLANKS;
    public static Block SLABS;
    public static Block SLABS_FULL;
    public static Block STAIR_1;
    public static Block STAIR_2;
    public static Block LOGS;
    public static Block LEAFS;
    public static Block SAPLINGS;
    public static Block FLOWER;
    public static Block ORE;

    public static Block SPELL_SET;

    public static Block DECAY_GRASS;
    public static Block DECAY_DIRT;
    public static Block DECAY_SAND;

    public static Map<Fluid, FluidBlock> FLUIDS = new HashMap<>();

    public static void init(){
        //ブロックを登録
        PLANKS = register("planks", new BlockPlanks());
        SLABS = register("slab", new BlockWoodSlabHalf());
        SLABS_FULL = register("slab_full", new BlockWoodSlabDouble());
        STAIR_1 = register("magic_stairs", new BlockStairs(PLANKS.getDefaultState().withProperty(BlockPlanks.TYPE, WoodSet.EnumWoodTypes.MAGIC)));
        LOGS = register("logs", new BlockLogs());
        LEAFS = register("leaves", new BlockLeafs());
        SAPLINGS = register("saplings", new BlockSaplings());
        //FLOWER = register("robe_flower", new BlockRobeFlower());
        ORE = register("magic_ore", new BlockMagicOre());

        SPELL_SET = register("spell_set", new SpellSetBlock());

        DECAY_DIRT = register("decay_dirt", new DecayedDirtBlock());
        DECAY_GRASS = register("decay_grass", new DecayedGrassBlock());
        DECAY_SAND = register("decay_sand", new DecayedSandBlock());

        //液体を登録
        FluidsHandler.fluids.forEach(f -> {
            FLUIDS.put((Fluid) f, (FluidBlock) register(f.getName(), new FluidBlock(f)));
        });
    }

    public static Block register(String name, Block block){
        block.setRegistryName(name);
        block.setTranslationKey(name);
        block.setCreativeTab(((IBlockBase)block).getTab());
        allBlocks.add((IBlockBase) block);
        return block;
    }

}
