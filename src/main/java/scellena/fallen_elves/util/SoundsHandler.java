package scellena.fallen_elves.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.FallenElves;

@SideOnly(Side.CLIENT)
public class SoundsHandler {

    public static SoundEvent PAPER;
    public static SoundEvent COINS;
    public static SoundEvent GUI_POINT;
    public static SoundEvent NAMED;
    public static SoundEvent LEVELUP;


    public static void init(){
        PAPER = registerSound("paper");
        COINS = registerSound("coins");
        GUI_POINT = registerSound("gui_point");
        NAMED = registerSound("named");
        LEVELUP = registerSound("levelup");
    }

    public static SoundEvent registerSound(String name){
        ResourceLocation location = new ResourceLocation(FallenElves.MOD_ID,name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
