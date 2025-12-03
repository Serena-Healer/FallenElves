package scellena.fallen_elves.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.network.CPackets.*;
import scellena.fallen_elves.network.SPackets.SkillSetPacket;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(FallenElves.MOD_ID);
    static int id = 0;

    public static void initPackets() {
        //クライアント側
        INSTANCE.registerMessage(PlayerDataSyncPacket.PacketSyncHandler.class, PlayerDataSyncPacket.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(EntityDataSyncPacket.PacketSyncHandler.class, EntityDataSyncPacket.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(WorldDataSyncPacket.PacketSyncHandler.class, WorldDataSyncPacket.class, id++, Side.CLIENT);

        //サーバー側
        INSTANCE.registerMessage(SkillSetPacket.PacketSyncHandler.class, SkillSetPacket.class, id++, Side.SERVER);
    }

}