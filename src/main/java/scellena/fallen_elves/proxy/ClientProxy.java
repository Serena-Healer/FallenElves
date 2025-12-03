package scellena.fallen_elves.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.blocks.fluids.FluidsHandler;
import scellena.fallen_elves.client.ClientEventHandler;
import scellena.fallen_elves.client.KeyHandler;
import scellena.fallen_elves.entities.EntityHandler;
import scellena.fallen_elves.items.IItemWithVariants;
import scellena.fallen_elves.items.ItemHandler;

public class ClientProxy extends CommonProxy{


    public void construct(FMLConstructionEvent event) {
        super.construct(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void preinit(FMLPreInitializationEvent event) {
        super.preinit(event);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        ClientRegistry.registerKeyBinding(KeyHandler.GO_NEXT_SKILL);
        ClientRegistry.registerKeyBinding(KeyHandler.GO_BACK_SKILL);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    public void postinit(FMLPostInitializationEvent event) {
        super.postinit(event);
    }


    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            ItemHandler.allItems.forEach(i -> {
                if(i instanceof IItemWithVariants){
                    for(int j=0; j<((IItemWithVariants)i).getModelCount(); j++){
                        ModelLoader.setCustomModelResourceLocation((Item) i, j, new ModelResourceLocation(((Item) i).getRegistryName() + "_" + ((IItemWithVariants)i).getModelName(j), "inventory"));
                    }
                }else {
                    ModelLoader.setCustomModelResourceLocation((Item) i, 0, new ModelResourceLocation(i.modelJsonName(), "inventory"));
                }
            });
            BlockHandler.allBlocks.forEach(b -> {
                if(b instanceof IBlockWithVariants){
                    for(int i=0; i<((IBlockWithVariants) b).getModelCount(); i++){
                        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) b), i, new ModelResourceLocation(((Block) b).getRegistryName() + "_" + ((IBlockWithVariants) b).getModelName(i), "inventory"));
                    }
                }else {
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) b), 0, new ModelResourceLocation(((Block) b).getRegistryName(), "inventory"));
                }
            });
            EntityHandler.registerModels();
            FluidsHandler.registerRenderers();
        }
    }

    @Override
    public World getWorld() {
        return FMLClientHandler.instance().getWorldClient();
    }
}
