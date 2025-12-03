package scellena.fallen_elves.items.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemWand extends ItemBase {

    public static final String SPELL_NBT_KEY = "spell_type";
    public static final String SPELL_LIST_KEY = "spell_list";

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        SpellBase spell = getSetSpell(playerIn, stack);
        if(spell != null && spell.isAvailable()){
            if(spell.onRightClick()){
                SpellHelper.onFinishSpell(playerIn, spell);
            };
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public SpellBase getSetSpell(EntityPlayer playerIn, ItemStack stackIn){
        NBTTagCompound nbt = getNBTShareTag(stackIn);
        int id = getSpellId(playerIn, stackIn);
        if(id >= 0) {
            List<ItemStack> books = loadStacks(stackIn);
            if(books.size() >= id){
                ItemStack stack1 = books.get(id);
                if(stack1.getItem() instanceof ItemSpellbook){
                    Class<? extends SpellBase> spell = ((ItemSpellbook)stack1.getItem()).getSpell(stack1);
                    return SpellHelper.getSpellObjects(playerIn).getOrDefault(SpellHelper.getRegistryName(spell), null);
                }
            }
        }
        return null;
    }

    public void setSpellId(EntityPlayer playerIn, ItemStack stackIn, int id){
        NBTTagCompound nbt = getNBTShareTag(stackIn);
        if(nbt == null) {
            nbt = new NBTTagCompound();
        }
        int size = loadStacks(stackIn).size();
        nbt.setInteger(SPELL_NBT_KEY, (id + size) % size);
    }

    public int getSpellId(EntityPlayer playerIn, ItemStack stackIn){
        NBTTagCompound nbt = getNBTShareTag(stackIn);
        if(nbt != null && nbt.hasKey(SPELL_NBT_KEY)) {
            int id = nbt.getInteger(SPELL_NBT_KEY);
            return id;
        }
        return -1;
    }

    public List<ItemStack> loadStacks(ItemStack stackIn){
        NBTTagCompound nbt = getNBTShareTag(stackIn);
        if(nbt == null || !nbt.hasKey(SPELL_LIST_KEY)) return new ArrayList<>();
        NonNullList<ItemStack> ans = NonNullList.withSize(5, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems((NBTTagCompound) nbt.getTag(SPELL_LIST_KEY), ans);
        return ans;
    }

    public ItemStack saveStacks(ItemStack stackIn, List<ItemStack> spellBooksIn){
        NBTTagCompound nbt = getNBTShareTag(stackIn);
        if(nbt == null) nbt = new NBTTagCompound();
        NBTTagCompound nbt2 = new NBTTagCompound();
        NonNullList<ItemStack> spellBooks = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i=0; i<Math.min(spellBooks.size(), spellBooksIn.size()); i++){
            spellBooks.set(i, spellBooksIn.get(i));
        }
        ItemStackHelper.saveAllItems(nbt2, spellBooks);
        nbt.setTag(SPELL_LIST_KEY, nbt2);
        readNBTShareTag(stackIn, nbt);
        return stackIn;
    }

}
