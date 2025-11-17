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
import scellena.fallen_elves.spells.SpellHelper;

public class PlayerDataHandler {

    public EntityPlayer owner;
    boolean reserveUpdate = true;

    public final int inf = 1000000007;

    public boolean initializedFlag = false;

    public static final int dataVersion = 0;

    //Fallen Elves データ ここから
    //魔法経験値
    double currentXP = 0;

    //魔法レベル＝闇堕ちレベル
    int currentLevel = 0;

    double mana = 0;

    public PlayerDataHandler(){

    }

    public void setOwner(EntityPlayer player){
        owner = player;
        update();
    }

    public NBTTagCompound getNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("version", dataVersion);
        nbt.setInteger("level", currentLevel);
        nbt.setDouble("exp", currentXP);
        nbt.setDouble("mana", mana);
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

                currentLevel = nbt.getInteger("level");
                currentXP = nbt.getDouble("exp");
                mana = nbt.getDouble("mana");

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

    //君は完璧で究極のゲッター・セッター
    public double getCurrentXP() {
        return currentXP;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void collectXP(double amount){
        currentXP += amount;
        while(true) {
            if (currentXP >= SpellHelper.getXPRequired(getCurrentLevel() + 1)) {
                currentXP -= SpellHelper.getXPRequired(getCurrentLevel() + 1);
                currentLevel++;
            }else{
                break;
            }
        }
    }

    public double getMana() {
        return mana;
    }

    public void addMana(double amount){
        mana += amount;
        if(mana < 0) mana = 0;
    }
}
