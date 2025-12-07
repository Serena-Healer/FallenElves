package scellena.fallen_elves.items.consumables;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.items.IItemBase;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.ModCreativeTab;

public class ItemManaFood extends ItemFood implements IItemBase {

    double manaRegen;

    public ItemManaFood(int amount, float saturation, double manaRegen) {
        super(amount, saturation, false);
        this.manaRegen = manaRegen;
        setAlwaysEdible();
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(entityLiving);
        data.addMana(manaRegen);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public String modelJsonName() {
        return getRegistryName().toString();
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.FOODS;
    }

}
