package scellena.fallen_elves.util;

import net.minecraft.client.audio.MusicTicker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.Confiance5th;

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
        ResourceLocation location = new ResourceLocation(Confiance5th.MOD_ID,name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
