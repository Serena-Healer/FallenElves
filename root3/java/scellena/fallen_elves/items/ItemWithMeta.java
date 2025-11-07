package scellena.fallen_elves.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemWithMeta extends ItemBase implements IItemWithVariants {

    int count = 0;

    public ItemWithMeta(int count){
        this.setHasSubtypes(true);
        this.count = count;
    }

    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item." + getRegistryName().getPath() + "_" + stack.getMetadata();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);
        if(getTab() == tab) {
            for (int i = 1; i < count; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getModelCount() {
        return count;
    }

    @Override
    public String getModelName(int meta) {
        return Integer.toString(meta);
    }
}
