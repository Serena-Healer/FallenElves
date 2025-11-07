package scellena.fallen_elves.entities;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderPolarBear;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.entities.models.renderer.PlayerBasedRenderer;

import java.util.ArrayList;
import java.util.List;

public class EntityHandler {

    private static int id = 0;
    public static List<Class<? extends Entity>> allEntities = new ArrayList<>();

    public static void init(){
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels(){
            }

    public static Class<? extends Entity> register(String name, Class<? extends Entity> entity){
        EntityRegistry.registerModEntity(new ResourceLocation(Confiance5th.MOD_ID, name), (Class<? extends Entity>) entity, name, id++, Confiance5th.INSTANCE,50,1,true,-1,-1);
        allEntities.add(entity);
        return entity;
    }

    @SideOnly(Side.CLIENT)
    public static void playerEntityRender(String key, Class<? extends EntityLiving> c, boolean slim){
        RenderingRegistry.registerEntityRenderingHandler(c, manager -> new PlayerBasedRenderer(manager,key, slim, 0));
    }

}
