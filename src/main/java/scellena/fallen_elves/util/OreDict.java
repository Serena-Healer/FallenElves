package scellena.fallen_elves.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scellena.fallen_elves.blocks.BlockHandler;

public class OreDict {

    public static void init(){
        OreDictionary.registerOre("plankWood", new ItemStack(BlockHandler.PLANKS, 1, 0));
        OreDictionary.registerOre("logWood", new ItemStack(BlockHandler.LOGS, 1, 0));
    }

}
