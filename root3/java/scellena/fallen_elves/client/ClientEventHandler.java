package scellena.fallen_elves.client;

import akka.japi.Pair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;
import scellena.fallen_elves.items.IItemBase;
import scellena.fallen_elves.items.ItemUtils;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientEventHandler {

    @SubscribeEvent
    public void onDrawScreen(RenderGameOverlayEvent event){
        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;

        //仮
        renderer.setUnicodeFlag(Minecraft.getMinecraft().gameSettings.forceUnicodeFont);

        PlayerDataHandler data = PlayerCapabilityProvider.getPlayerData(Minecraft.getMinecraft().player);
        //if(event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)event.setCanceled(true);
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH){

        }
    }

    @SubscribeEvent
    public void onDrawTooltip(ItemTooltipEvent event){
        if(!(event.getItemStack().getItem() instanceof IItemBase)){
            ItemUtils.setInformation(event.getItemStack(), event.getToolTip());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event){
        WorldClient world = Minecraft.getMinecraft().world;
        if(world != null && Minecraft.getMinecraft().player.isCreative()) {

        }
    }
}
