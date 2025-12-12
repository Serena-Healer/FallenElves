package scellena.fallen_elves.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.FallenElves;

public class SoundsHandler {

    public static SoundEvent LEVEL;


    public static void init(){
        LEVEL = registerSound("level");
    }

    public static SoundEvent registerSound(String name){
        ResourceLocation location = new ResourceLocation(FallenElves.MOD_ID,name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
