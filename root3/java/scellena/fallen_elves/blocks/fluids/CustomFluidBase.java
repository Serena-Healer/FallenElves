package scellena.fallen_elves.blocks.fluids;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import scellena.fallen_elves.Confiance5th;

import java.awt.*;

public abstract class CustomFluidBase extends Fluid {
    public CustomFluidBase(String fluidName, Color color) {
        super(fluidName, new ResourceLocation(Confiance5th.MOD_ID, "block/fluids/" + fluidName + "_still"), new ResourceLocation(Confiance5th.MOD_ID, "block/fluids/" + fluidName + "_flow"), color);
    }

    public void onEnter(Entity entity){

    }
}
