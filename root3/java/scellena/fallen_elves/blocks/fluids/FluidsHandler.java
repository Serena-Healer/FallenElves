package scellena.fallen_elves.blocks.fluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.blocks.BlockHandler;

import java.util.ArrayList;
import java.util.List;

public class FluidsHandler {

    public static List<CustomFluidBase> fluids = new ArrayList<>();

    public static void init(){
    }

    public static CustomFluidBase register(CustomFluidBase fluid){
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
        fluids.add(fluid);
        return fluid;
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers(){
        fluids.forEach(f -> {
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockHandler.FLUIDS.get(f)), new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    return new ModelResourceLocation(Confiance5th.MOD_ID + ":" + f.getName(), "fluid");
                }
            });

            ModelLoader.setCustomStateMapper(BlockHandler.FLUIDS.get(f), new StateMapperBase() {
                @Override
                public ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    return new ModelResourceLocation(Confiance5th.MOD_ID + ":" + f.getName(), "fluid");
                }
            });
        });
    }
}
