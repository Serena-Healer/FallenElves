package scellena.fallen_elves.client;


import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyHandler {

    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent e) {
        onTick(e);
    }

    private void onTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;

        if (e.side == Side.SERVER)
            return;

        if (e.phase == TickEvent.Phase.START) {
            if (FMLClientHandler.instance().getClient().inGameHasFocus) {

            }
        }
    }
}
