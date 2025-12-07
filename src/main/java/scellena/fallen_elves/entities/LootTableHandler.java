package scellena.fallen_elves.entities;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import scellena.fallen_elves.FallenElves;

public class LootTableHandler {

    public static final ResourceLocation ELF_HOSTILE = LootTableList.register(new ResourceLocation(FallenElves.MOD_ID, "elf"));
    public static final ResourceLocation LUMINARY = LootTableList.register(new ResourceLocation(FallenElves.MOD_ID, "luminary"));
    public static final ResourceLocation GOD = LootTableList.register(new ResourceLocation(FallenElves.MOD_ID, "god"));

}
