package scellena.fallen_elves.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import scellena.fallen_elves.blocks.fluids.FluidBlock;
import scellena.fallen_elves.blocks.fluids.FluidsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockHandler {

    public static List<IBlockBase> allBlocks = new ArrayList<>();

    public static Map<Fluid, FluidBlock> FLUIDS = new HashMap<>();

    public static void init(){
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
