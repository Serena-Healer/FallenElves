package scellena.fallen_elves.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.RegistryBuilder;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.blocks.IBlockWithBlockItem;
import scellena.fallen_elves.blocks.IBlockWithTileEntity;
import scellena.fallen_elves.blocks.IBlockWithVariants;
import scellena.fallen_elves.blocks.fluids.FluidsHandler;
import scellena.fallen_elves.client.GuiHandler;
import scellena.fallen_elves.commands.CommandsHandler;
import scellena.fallen_elves.data.entity.EntityCapabilityStorage;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.data.player.CapabilityStorage;
import scellena.fallen_elves.data.player.PlayerDataHandler;
import scellena.fallen_elves.decay.BlockDecayHelper;
import scellena.fallen_elves.entities.EntityHandler;
import scellena.fallen_elves.events.handler.CapabilityEventHandler;
import scellena.fallen_elves.events.handler.DecayEventHandler;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.network.PacketHandler;
import scellena.fallen_elves.potions.PotionsHandler;
import scellena.fallen_elves.spells.SpellRegistry;
import scellena.fallen_elves.util.OreDict;
import scellena.fallen_elves.util.SoundsHandler;
import scellena.fallen_elves.worldgen.gen.GeneratorHandler;

import java.util.Objects;

public abstract class CommonProxy {

    public void construct(FMLConstructionEvent event){
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
        MinecraftForge.EVENT_BUS.register(new DecayEventHandler());
    }

    public void preinit(FMLPreInitializationEvent event) {
        long startTime = System.nanoTime();

        FluidsHandler.init();
        System.out.println("Fluid inited");

        SpellRegistry.init();
        System.out.println("Spells inited");

        PacketHandler.initPackets();
        System.out.println("Packet inited");
        BlockHandler.init();
        System.out.println("Block inited");
        ItemHandler.init();
        System.out.println("Item inited");
        EntityHandler.init();
        System.out.println("Entities registered");

        GeneratorHandler.init();
        System.out.println("Generator inited");
        /*
        StructureHandler.init();
        System.out.println("Structures inited");
        BiomeHandler.init();
        System.out.println("Biome inited");
        DimensionHandler.init();
        System.out.println("Dimension inited");
        DimensionHandler.registerDims();
        System.out.println("Dimension registered");
         */
        CommandsHandler.init();
        System.out.println("Command inited");
        CapabilityManager.INSTANCE.register(PlayerDataHandler.class, new CapabilityStorage(), PlayerDataHandler.class);
        CapabilityManager.INSTANCE.register(EntityDataHandler.class, new EntityCapabilityStorage(), EntityDataHandler.class);

        System.out.println(FallenElves.MOD_NAME + " : PreInit took: " + (System.nanoTime() - startTime) + "ms.");
    }

    public void init(FMLInitializationEvent event) {
        long startTime = System.nanoTime();

        System.out.println("Registering GUI");
        NetworkRegistry.INSTANCE.registerGuiHandler(FallenElves.INSTANCE, new GuiHandler());
        System.out.println("Registered GUI");
        OreDict.init();
        System.out.println("Ore Dictionary inited");
        PotionsHandler.init();
        System.out.println("Potions inited");
        BlockDecayHelper.initMap();
        System.out.println("Decaying Block Data inited");

        System.out.println(FallenElves.MOD_NAME + " : Init took: " + (System.nanoTime() - startTime) + "ms.");
    }

    public void postinit(FMLPostInitializationEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            ItemHandler.allItems.forEach(i -> {
                event.getRegistry().register((Item) i);
            });
            BlockHandler.allBlocks.forEach(b -> {
                if(b instanceof IBlockWithBlockItem){
                    event.getRegistry().register(((IBlockWithBlockItem) b).getItemBlock().setRegistryName(Objects.requireNonNull(((Block) b).getRegistryName())));
                }else if(b instanceof IBlockWithVariants) {
                    event.getRegistry().register(new ItemBlock((Block) b){
                        @Override
                        public boolean getHasSubtypes() {
                            return true;
                        }

                        @Override
                        public int getMetadata(int damage) {
                            return damage;
                        }

                        @Override
                        public String getTranslationKey(ItemStack stack) {
                            return super.getTranslationKey(stack) + "_" + ((IBlockWithVariants)block).getModelName(stack.getMetadata());
                        }
                    }.setRegistryName(Objects.requireNonNull(((Block) b).getRegistryName())));
                }else{
                    event.getRegistry().register(new ItemBlock((Block) b).setRegistryName(Objects.requireNonNull(((Block) b).getRegistryName())));
                }
            });
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            BlockHandler.allBlocks.forEach(b -> {
                event.getRegistry().register((Block) b);
                if(b instanceof IBlockWithTileEntity){
                    GameRegistry.registerTileEntity(((IBlockWithTileEntity) b).getTileEntity(), ((IBlockWithTileEntity) b).getTileEntityName());
                }
            });
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void addSounds(RegistryEvent.Register<SoundEvent> event){
            SoundsHandler.init();
        }

        @SubscribeEvent
        public static void addRegistries(RegistryEvent.NewRegistry event){
            RegistryBuilder<SpellRegistry.SpellRegistryEntry> spells = new RegistryBuilder<>();
            spells.setType(SpellRegistry.SpellRegistryEntry.class);
            spells.setName(new ResourceLocation(FallenElves.MOD_ID, "spells"));
            SpellRegistry.SPELLS = spells.create();
        }
    }

    public void serverInit(FMLServerStartingEvent event){
        CommandsHandler.allCommands.forEach(event::registerServerCommand);
    }

    public abstract World getWorld();
}
