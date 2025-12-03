package scellena.fallen_elves.client;


import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.items.wands.ItemWand;
import scellena.fallen_elves.network.PacketHandler;
import scellena.fallen_elves.network.SPackets.SkillSetPacket;

@SideOnly(Side.CLIENT)
public class KeyHandler {

    public static final KeyBinding GO_NEXT_SKILL = new KeyBinding("key.next_skill", Keyboard.KEY_NONE,"key.categories." + FallenElves.MOD_ID);
    public static final KeyBinding GO_BACK_SKILL = new KeyBinding("key.back_skill", Keyboard.KEY_NONE,"key.categories." + FallenElves.MOD_ID);

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

                ItemStack heldItem = player.getHeldItemMainhand();

                int skillGo = 0;
                if (GO_NEXT_SKILL.isPressed()) skillGo--;
                if (GO_BACK_SKILL.isPressed()) skillGo++;
                if(skillGo != 0 && heldItem.getItem() instanceof ItemWand){
                    skillGo += ((ItemWand) heldItem.getItem()).getSpellId(player, heldItem);
                    PacketHandler.INSTANCE.sendToServer(new SkillSetPacket(skillGo));
                }

            }
        }
    }
}
