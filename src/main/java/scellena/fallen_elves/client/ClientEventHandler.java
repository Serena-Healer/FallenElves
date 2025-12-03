package scellena.fallen_elves.client;

import akka.japi.Pair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
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
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.client.util.GuiUtils;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;
import scellena.fallen_elves.items.IItemBase;
import scellena.fallen_elves.items.ItemUtils;
import scellena.fallen_elves.items.wands.ItemWand;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientEventHandler {

    static final ResourceLocation MANA_BAR = new ResourceLocation(FallenElves.MOD_ID, "textures/gui/mana_bar.png");

    @SubscribeEvent
    public void onDrawScreen(RenderGameOverlayEvent event){
        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;

        PlayerDataHandler data = PlayerCapabilityProvider.getPlayerData(Minecraft.getMinecraft().player);
        EntityDataHandler data2 = EntityCapabilityProvider.getEntityData(Minecraft.getMinecraft().player);
        //if(event.getType() == RenderGameOverlayEvent.ElementType.ARMOR)event.setCanceled(true);
        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR){
            Minecraft.getMinecraft().getTextureManager().bindTexture(MANA_BAR);
            GlStateManager.enableAlpha();
            GlStateManager.color(1, 1, 1, 1);

            int x = 8; int y = 8;

            //描画
            int level = data2.getCurrentLevel() + 1;

            //MPバー
            double rate = data2.getMana() / SpellHelper.getMaxMana(Minecraft.getMinecraft().player);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, 0, 0, 127, 16);
            if(rate > 0)
                Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x + 1, y + 1, 0, 26, (int) (125 * rate), 14);

            //経験値バー
            rate = data2.getCurrentXP() / SpellHelper.getXPRequired(level);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y + 16, 0, 17, 127, 9);
            if(rate > 0)
                Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x + 1, y + 17, 0, 40, (int) (125 * rate), 7);

            //レベル
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x + 132, y, 127, 0, 23, 23);
            GuiUtils.drawNumberRight(renderer, level, x + 132 + 18, y + 12, 0, false);

            //現在魔法表示
            ItemStack heldItem = Minecraft.getMinecraft().player.getHeldItemMainhand();
            if(heldItem.getItem() instanceof ItemWand){
                SpellBase spell = ((ItemWand)heldItem.getItem()).getSetSpell(Minecraft.getMinecraft().player, heldItem);
                if(spell != null){
                    GuiUtils.drawStringMiddle(renderer, spell.getDisplayName(), width / 2, height / 2 + 24, Integer.parseInt("ff00ff", 16), true);
                }
            }

            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
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
