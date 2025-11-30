package scellena.fallen_elves.blocks.spell;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.items.wands.ItemWand;

import java.util.List;

public class TileEntitySpellSet extends TileEntity implements IInventory {

    List<ItemStack> inventory = NonNullList.withSize(24, ItemStack.EMPTY);

    @Override
    public int getSizeInventory() {
        return 24;
    }

    @Override
    public boolean isEmpty() {
        for(int i=0; i<24; i++){
            if(inventory.get(i) != null && inventory.get(i) != ItemStack.EMPTY)return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index == 0){
            return stack.getItem() instanceof ItemWand;
        }else{
            return stack.getItem() instanceof ItemSpellbook;
        }
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return I18n.format("tile.spell_set.name");
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory = NonNullList.withSize(24, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, (NonNullList<ItemStack>) inventory);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, (NonNullList<ItemStack>) inventory);
        return compound;
    }

}
