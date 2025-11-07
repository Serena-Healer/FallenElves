package scellena.fallen_elves.network.CPackets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.data.world.WorldDataHandler;

import java.io.IOException;

public class WorldDataSyncPacket implements IMessage {

    private int entityId;
    private NBTTagCompound nbtTag;

    public WorldDataSyncPacket() {
        // TODO Auto-generated constructor stub
    }

    public WorldDataSyncPacket(WorldDataHandler data) {
        nbtTag = (data == null ? new NBTTagCompound() : data.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        entityId = buffer.readInt();
        try {
            nbtTag = DataSerializers.COMPOUND_TAG.read(new PacketBuffer(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(entityId);
        DataSerializers.COMPOUND_TAG.write(new PacketBuffer(buffer), nbtTag);
    }

    public static class PacketSyncHandler implements IMessageHandler<WorldDataSyncPacket, IMessage> {
        @Override
        public IMessage onMessage(WorldDataSyncPacket message, MessageContext ctx) {
            // System.out.println("Client update");
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    World world = Confiance5th.proxy.getWorld();
                    if (world == null || !world.isRemote)
                        return;
                    WorldDataHandler.get(world).readFromNBT(message.nbtTag);
                }
            });
            return null;
        }
    }
}
