package scellena.fallen_elves.items;

import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import scellena.fallen_elves.FallenElves;
import net.minecraft.item.ItemArmor;
import scellena.fallen_elves.spells.SpellAttributes;

import java.util.UUID;

public class ItemMageArmor extends ItemArmor implements IItemColor, IItemBase {

    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

    public static final ItemArmor.ArmorMaterial robe1 = EnumHelper.addArmorMaterial("beginner", FallenElves.MOD_ID + ":arcane", 250, new int[]{1, 3, 2, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static final ItemArmor.ArmorMaterial robe2 = EnumHelper.addArmorMaterial("apprentice", FallenElves.MOD_ID + ":arcane", 900, new int[]{2, 6, 5, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static final ItemArmor.ArmorMaterial robeMending = EnumHelper.addArmorMaterial("mending", FallenElves.MOD_ID + ":arcane", 900, new int[]{2, 6, 5, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static final ItemArmor.ArmorMaterial robeMight = EnumHelper.addArmorMaterial("might", FallenElves.MOD_ID + ":arcane", 900, new int[]{2, 6, 5, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    double mana;
    double might;
    double mending;
    String color;

    public ItemMageArmor(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn, double mana, double might, double mending, String color) {
        super(materialIn, 0, equipmentSlotIn);
        this.mana = mana;
        this.might = might;
        this.mending = mending;
        this.color = color;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SpellAttributes.MAGICAL_MENDING.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "mending", (double)this.mending, 0));
            multimap.put(SpellAttributes.MAGICAL_MIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "might", (double)this.might, 0));
            multimap.put(SpellAttributes.MAX_MANA.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "mana", (double)this.mana, 0));
        }

        return multimap;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(tintIndex == 1) return Integer.parseInt("ffffff", 16);
        return Integer.parseInt(color, 16);
    }

    @Override
    public int getColor(ItemStack stack) {
        return colorMultiplier(stack, 0);
    }

    @Override
    public String modelJsonName() {
        return FallenElves.MOD_ID + ":robe_" + this.armorType.getName();
    }

    public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.EQUIPMENTS;
    }

}
