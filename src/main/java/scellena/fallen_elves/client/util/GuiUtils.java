package scellena.fallen_elves.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils {

    public static void drawNumberRight(FontRenderer renderer, int number, int rightX, int y, int color, boolean shadow){
        drawStringRight(renderer, Integer.toString(number), rightX, y, color, shadow);
    }

    public static void drawStringRight(FontRenderer renderer, String str, int rightX, int y, int color, boolean shadow){
        if(shadow) {
            renderer.drawStringWithShadow(str, rightX - renderer.getStringWidth(str), y, color);
        }else{
            renderer.drawString(str, rightX - renderer.getStringWidth(str), y, color);
        }
    }

    public static void drawStringMiddle(FontRenderer renderer, String str, int rightX, int y, int color, boolean shadow){
        if(shadow) {
            renderer.drawStringWithShadow(str, rightX - renderer.getStringWidth(str) / 2, y, color);
        }else{
            renderer.drawString(str, rightX - renderer.getStringWidth(str) / 2, y, color);
        }
    }

    public static void drawHorizontalGradientRect(int left, int top, int right, int bottom, int startColor, int endColor, double z)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)z).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)z).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)z).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)z).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

}
