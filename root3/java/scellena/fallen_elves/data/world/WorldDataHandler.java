package scellena.fallen_elves.data.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.network.CPackets.WorldDataSyncPacket;
import scellena.fallen_elves.network.PacketHandler;

import java.util.HashMap;
import java.util.Map;

public class WorldDataHandler extends WorldSavedData {

    public int dimensionQualified = -1;
    public Map<Integer, Integer> namedKills = new HashMap<>();
    public static String KEY = Confiance5th.MOD_ID + "_data";
    public static WorldDataHandler instance;

    public Map<String, Integer> kills = new HashMap<>();

    public WorldDataHandler() {
        super(KEY);
    }

    public WorldDataHandler(String str) {
        super(str);
    }

    public static WorldDataHandler get(World world){
        MapStorage storage = world.getMapStorage();
        instance = (WorldDataHandler) storage.getOrLoadData(WorldDataHandler.class, KEY);

        if (instance == null) {
            instance = new WorldDataHandler();
            storage.setData(KEY, instance);
        }
        return instance;
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if(nbt.hasKey("dimension")){
            dimensionQualified = nbt.getInteger("dimension");
        }
        if(nbt.hasKey("named_kill")){
            NBTTagCompound nbtNamed = nbt.getCompoundTag("named_kills");
            nbtNamed.getKeySet().forEach(k -> {
                int s = Integer.parseInt(k);
                namedKills.put(s, nbtNamed.getInteger(k));
            });
        }
        if(nbt.hasKey("kills")){
            NBTTagCompound nbtNamed = nbt.getCompoundTag("kills");
            nbtNamed.getKeySet().forEach(k -> {
                kills.put(k, nbtNamed.getInteger(k));
            });
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("dimension", dimensionQualified);
        System.out.println(dimensionQualified);
        NBTTagCompound nbtNamed = new NBTTagCompound();
        namedKills.forEach((a, b) -> {
            nbtNamed.setInteger(Integer.toString(a), b);
        });
        compound.setTag("named_kill", nbtNamed);
        NBTTagCompound nbtKills = new NBTTagCompound();
        kills.forEach((a, b) -> {
            nbtKills.setInteger(a, b);
        });
        compound.setTag("kills", nbtKills);
        return compound;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        //クライアント側に共有してあげる
        PacketHandler.INSTANCE.sendToAll(new WorldDataSyncPacket(this));
    }
}
