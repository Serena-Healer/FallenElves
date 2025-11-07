package scellena.fallen_elves.entities.models.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.entities.models.models.ModelPlayerCopy;
import scellena.fallen_elves.entities.models.renderer.components.LayerHeldItemCopy;

import javax.annotation.Nullable;

public class PlayerBasedRenderer extends RenderLiving<EntityLiving> {

    private final boolean smallArms;
    protected String filename = "";

    public PlayerBasedRenderer(RenderManager manager, String name, boolean slim, float size) {
        this(manager, name, slim, size, 12);
    }

    public PlayerBasedRenderer(RenderManager manager, String name, boolean slim, float size, int height){
        super(manager, new ModelPlayerCopy(size, slim, height),0.5F);
        this.smallArms = slim;
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItemCopy(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerElytra(this));
        filename = name;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return new ResourceLocation(FallenElves.MOD_ID + ":textures/entity/"+filename+".png");
    }

}
