package scellena.fallen_elves.data.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import scellena.fallen_elves.network.CPackets.EntityDataSyncPacket;
import scellena.fallen_elves.network.PacketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityDataHandler {

    Entity owner;
    boolean reserveUpdate = false;

    public Map<String, ItemStack> equipments = new HashMap<>();

    public void readNBT(NBTBase nbtin){
        if(nbtin instanceof NBTTagCompound){
            NBTTagCompound nbt = (NBTTagCompound) nbtin;
        }
    }

    public NBTTagCompound getNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        return nbt;
    }

    public void setOwner(Entity entity){
        owner = entity;
    }

    public void syncCapability(){
        if(owner != null && !owner.world.isRemote){
            EntityDataSyncPacket packet = new EntityDataSyncPacket(owner);
            ((WorldServer)(owner.world)).getEntityTracker().getTrackingPlayers(owner).forEach(p -> {
               if(p instanceof EntityPlayerMP){
                   PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) p);
               }
            });
            if(owner instanceof EntityPlayerMP){
                PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) owner);
            }
        }
    }

    public void onTick(){
        if(reserveUpdate){
            if(!owner.isDead && !owner.world.isRemote){
                reserveUpdate = false;
                syncCapability();
            }
        }
    }

    public void update(){
        reserveUpdate = true;
    }

}
