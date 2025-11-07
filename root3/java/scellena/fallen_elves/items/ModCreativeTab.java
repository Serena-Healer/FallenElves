package scellena.fallen_elves.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import scellena.fallen_elves.Confiance5th;

public abstract class ModCreativeTab extends CreativeTabs {

    public static final ModCreativeTab FOODS = new ModCreativeTab("consumables"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };
    public static final ModCreativeTab EQUIPMENTS = new ModCreativeTab("equipments"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };
    public static final ModCreativeTab MATERIAL = new ModCreativeTab("material"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };
    public static final ModCreativeTab BLOCKS = new ModCreativeTab("blocks"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };
    public static final ModCreativeTab DUNGEON_BLOCKS = new ModCreativeTab("creative"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };
    public static final ModCreativeTab MOD_ITEMS = new ModCreativeTab("items"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SPECKLED_MELON);
        }
    };

    public ModCreativeTab(String name) {
        super(Confiance5th.MOD_ID + ".tabs." + name);
    }
}
