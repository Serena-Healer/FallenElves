package scellena.fallen_elves.blocks.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.items.wands.ItemWand;
import scellena.fallen_elves.util.SkillUtils;

import java.util.ArrayList;
import java.util.List;

public class ContainerSpellSet extends Container {

    InventoryPlayer player;
    TileEntitySpellSet tileEntity;

    public ContainerSpellSet(InventoryPlayer p, TileEntitySpellSet t) {
        super();
        player = p;
        tileEntity = t;
        addSlotToContainer(new SlotWand(tileEntity, this, 0,24, 10));
        for(int i=0; i<5; i++){
            addSlotToContainer(new Slot(tileEntity, i + 1, i * 18 + 69, 10));
        }
        for(int i=0; i<2; i++){
            for(int j=0; j<9; j++){
                addSlotToContainer(new Slot(tileEntity, i * 9 + j + 6, 8 + 18 * j, 36 + 18 * i));
            }
        }

        for(int i=0; i<27; i++){
            addSlotToContainer(new Slot(p, i + 9, 8 + (i % 9) * 18, 86 + (i / 9) * 18));
        }
        for(int i=0; i<9; i++){
            addSlotToContainer(new Slot(p, i, 8 + i * 18, 144));
        }
    }

    public InventoryPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileEntity);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(itemstack1, itemstack);
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }

    public void onWandPut(ItemStack stack){
        int i = 0;
        emptySpellSlots();
        for(ItemStack stack1 : ((ItemWand)stack.getItem()).loadStacks(stack)){
            for(int j=0; j<18; j++){
                ItemStack stack2 = tileEntity.getStackInSlot(j + 6);
                if(stack2.isItemEqual(stack1)){
                    tileEntity.setInventorySlotContents(i + 1, stack1);
                    stack2.setCount(stack2.getCount() - 1);
                    break;
                }
            }
            i++;
        }
    }

    public void onWandTake(ItemStack stack){
        if(!stack.isEmpty()){
            List<ItemStack> spellBooks = new ArrayList<>();
            for(int i=1; i<6; i++){
                ItemStack spellStack = tileEntity.getStackInSlot(i);
                if(!spellStack.isEmpty()){
                    spellBooks.add(spellStack);
                }
            }
            if(stack.getItem() instanceof ItemWand){
                ((ItemWand)stack.getItem()).saveStacks(stack, spellBooks);
            }
            emptySpellSlots();
            SkillUtils.playSoundFromServer(player.player.world, player.player.getPositionVector(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 2);
            SkillUtils.playSoundFromServer(player.player.world, player.player.getPositionVector(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1);
        }
    }

    public void emptySpellSlots(){
        for(int i=0; i<5; i++){
            ItemStack stack = tileEntity.getStackInSlot(i + 1);
            if(!stack.isEmpty()){
                for(int j=0; j<18; j++){
                    ItemStack stack1 = tileEntity.getStackInSlot(j + 6);
                    if(stack1.isItemEqual(stack)){
                        stack1.setCount(stack.getCount() + stack1.getCount());
                        break;
                    }
                    if(stack1.isEmpty()){
                        tileEntity.setInventorySlotContents(j + 6, stack);
                        break;
                    }
                    if(j == 17){
                        player.player.dropItem(stack1, true);
                    }
                }
            }
            tileEntity.setInventorySlotContents(i + 1, ItemStack.EMPTY);
        }
    }

    public static class SlotWand extends Slot {

        ContainerSpellSet container;

        public SlotWand(IInventory inventoryIn, ContainerSpellSet container, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
            this.container = container;
        }

        @Override
        public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
            super.onSlotChange(p_75220_1_, p_75220_2_);
            container.onWandTake(p_75220_2_);
        }

        @Override
        public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
            container.onWandTake(stack);
            return super.onTake(thePlayer, stack);
        }

        @Override
        public void putStack(ItemStack stack) {
            super.putStack(stack);
            if(stack.getItem() instanceof ItemWand){
                container.onWandPut(stack);
            }
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return super.isItemValid(stack) && stack.getItem() instanceof ItemWand;
        }
    }

    public static class SlotSpell extends Slot {
        public SlotSpell(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return super.isItemValid(stack) && stack.getItem() instanceof ItemSpellbook;
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

}
