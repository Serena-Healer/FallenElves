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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import scellena.fallen_elves.decay.DecayTicker;
import scellena.fallen_elves.network.CPackets.EntityDataSyncPacket;
import scellena.fallen_elves.network.PacketHandler;
import scellena.fallen_elves.spells.SpellAttributes;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.spells.SpellRegistry;
import scellena.fallen_elves.util.SoundsHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityDataHandler {

    Entity owner;
    boolean reserveUpdate = false;

    public boolean initializedFlag = false;
    public static final int dataVersion = 0;

    public Map<String, ItemStack> equipments = new HashMap<>();

    //Fallen Elves データ ここから
    //魔法経験値
    double currentXP = 0;

    //魔法レベル＝闇堕ちレベル
    int currentLevel = 0;
    Map<ResourceLocation, SpellBase> spellObjects = new HashMap<>();

    double mana = 0;

    //邪神の介入タイマー
    int randomEventTick = 0;

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
            }else{
                reset();
            }
        }

        initializedFlag = true;
    }

    //アップデートする
    public void adjustVersion(int oldVersion){
    }

    public void reset(){
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

    public void setOwner(Entity entity){
        if(owner != entity) {
            owner = entity;
            initSpells();
        }
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

        for(SpellBase spell : getSpellObjects().values()){
            spell.onTick();
        }

        addMana(SpellHelper.getManaRegen(owner) / 20.0);
        randomEventTick--;
        DecayTicker.onTick(owner);
    }

    public void update(){
        reserveUpdate = true;
    }

    public void initSpells(){
        for(SpellRegistry.SpellRegistryEntry entry : SpellRegistry.SPELLS){
            spellObjects.put(entry.getRegistryName(), entry.getInstance());
            spellObjects.get(entry.getRegistryName()).setOwner(owner);
        }

        addMana(998244353);
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
                owner.getEntityWorld().playSound(null, owner.getPosition(), SoundsHandler.LEVEL, SoundCategory.PLAYERS, 1, 1);
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
        if(mana > SpellHelper.getMaxMana(owner)) mana = SpellHelper.getMaxMana(owner);
        update();
    }

    public int getRandomEventTick() {
        return randomEventTick;
    }

    public EntityDataHandler setRandomEventTick(int randomEventTick) {
        this.randomEventTick = randomEventTick;
        return this;
    }

    public Map<ResourceLocation, SpellBase> getSpellObjects() {
        return spellObjects;
    }
}
