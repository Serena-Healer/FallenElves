package scellena.fallen_elves.data.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.spells.SpellRegistry;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataHandler {

    public EntityPlayer owner;
    boolean reserveUpdate = true;

    public final int inf = 1000000007;

    public boolean initializedFlag = false;

    public static final int dataVersion = 0;

    public PlayerDataHandler(){

    }

    public void setOwner(EntityPlayer player){
        owner = player;
        update();
    }

    public NBTTagCompound getNBT(){
        NBTTagCompound nbt = new NBTTagCompound();

        return nbt;
    }

    public void readNBT(NBTBase nbtin){
        if(nbtin instanceof NBTTagCompound){
            NBTTagCompound nbt = (NBTTagCompound) nbtin;
            if(nbt.hasKey("version")) {
                int version = nbt.getInteger("version");

                if(version != dataVersion){
                    adjustVersion(version);
                }

                initializedFlag = true;
            }else{
                reset();
            }
        }
    }

    //アップデートする
    public void adjustVersion(int oldVersion){
    }

    public void reset(){
        update();
    }

    public void onTick(){
        if(reserveUpdate){
            if(!owner.isDead && !owner.world.isRemote){
                reserveUpdate = false;
            }
        }
    }

    public void update(){
        reserveUpdate = true;
    }


}
