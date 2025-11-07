package scellena.fallen_elves;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scellena.fallen_elves.proxy.CommonProxy;

@Mod(
        modid = Confiance5th.MOD_ID,
        name = Confiance5th.MOD_NAME,
        version = Confiance5th.VERSION
)
public class Confiance5th {

    public static final String MOD_ID = "confiance5th";
    public static final String MOD_NAME = "Confiance5th";
    public static final String VERSION = "0.1";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Confiance5th INSTANCE;


    @SidedProxy(clientSide = "scellena.fallen_elves.proxy.ClientProxy",serverSide = "scellena.fallen_elves.proxy.ServerProxy")
    public static CommonProxy proxy;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event){
        proxy.construct(event);
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preinit(event);
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postinit(event);
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event){
        proxy.serverInit(event);
    }

}
