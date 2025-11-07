package scellena.fallen_elves.entities.models.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.entities.models.models.HorseModel;

import javax.annotation.Nullable;

public class HorseBasedRenderer extends RenderLiving<EntityLiving> {

    protected String filename = "";

    public HorseBasedRenderer(RenderManager renderer, String fileName)
    {
        super(renderer, new HorseModel(), 0.5F);
        this.filename = fileName;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return new ResourceLocation(Confiance5th.MOD_ID + ":textures/entity/"+filename+".png");
    }
}
