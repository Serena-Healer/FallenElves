package scellena.fallen_elves.network.SPackets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.items.wands.ItemWand;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class SkillSetPacket implements IMessage {
    private int slot;

    public SkillSetPacket() {
        // TODO Auto-generated constructor stub
    }

    public SkillSetPacket(int slot) {
        this.slot = slot;
        // ModMain.proxy.log.info("Creating Packet: {} Args: {}", this, entity);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        slot = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public static class PacketSyncHandler implements IMessageHandler<SkillSetPacket, IMessage> {
        @Override
        public IMessage onMessage(SkillSetPacket message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    World world = FallenElves.proxy.getWorld();
                    if (world == null || ctx.side == Side.CLIENT)
                        return;
                    EntityPlayer player = ctx.getServerHandler().player;
                    ItemStack stack = player.getHeldItemMainhand();
                    if(stack.getItem() instanceof ItemWand){
                        ((ItemWand)stack.getItem()).setSpellId(player, stack, message.slot);
                    }
                }
            });
            return null;
        }
    }

}
