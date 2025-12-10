package scellena.fallen_elves.items.wands;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.spells.SpellAttributes;
import scellena.fallen_elves.spells.SpellBase;
import scellena.fallen_elves.spells.SpellHelper;
import scellena.fallen_elves.util.SkillUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemWand extends ItemBase {

    public static final String SPELL_NBT_KEY = "spell_type";
    public static final String SPELL_LIST_KEY = "spell_list";

    public static UUID MIGHT_ID = UUID.fromString("12c799be-d16a-4c8c-bc05-64d46438332c");
    public static UUID MENDING_ID = UUID.fromString("4c68009c-199e-487c-8775-9660ae6adee5");

    public double damage;
    public double might;
    public double mending;

    public ItemWand(double damage, double might, double mending) {
        this.damage = damage;
        this.might = might;
        this.mending = mending;
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            multimap.put(SpellAttributes.MAGICAL_MIGHT.getName(), new AttributeModifier(MIGHT_ID, "Weapon modifier", might, 0));
            multimap.put(SpellAttributes.MAGICAL_MENDING.getName(), new AttributeModifier(MENDING_ID, "Weapon modifier", mending, 0));
        }

        return multimap;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        SpellBase spell = getSetSpell(playerIn, stack);
        if(spell != null) {
            if (spell.isAvailable()) {
                if (spell.onRightClick()) {
                    SpellHelper.onFinishSpell(playerIn, spell);
                }
                ;
            }else{
                SkillUtils.playSoundFromServer(worldIn, playerIn.getPositionVector(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1, 1);
            }
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
