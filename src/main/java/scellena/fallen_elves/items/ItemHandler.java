package scellena.fallen_elves.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import scellena.fallen_elves.items.consumables.ItemDebug;
import scellena.fallen_elves.items.consumables.ItemExperience;
import scellena.fallen_elves.items.consumables.ItemManaFood;
import scellena.fallen_elves.items.consumables.ItemManualGetter;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.items.wands.ItemWand;
import scellena.fallen_elves.spells.SpellRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler {

    public static List<IItemBase> allItems = new ArrayList<>();

    public static Item INGOT;

    public static Item WAND_ENEMY;
    public static Item SWORD_ENEMY;
    public static Item WAND_GOD;

    public static void init(){

        register("wand", new ItemWand(2.0, 10.0, 10.0));
        WAND_ENEMY = register("wand_light", new ItemWand(1.5, 30.0, 30.0));
        WAND_GOD = register("wand_knowledge", new ItemWand(3.0, 50.0, 50.0));
        SWORD_ENEMY = register("sword_luminary", new ItemWand(7.0, 5.0, 5.0));

        register("material_elf", new ItemBase());
        register("material_god", new ItemBase());
        register("material_luminary", new ItemBase());
        register("book_material1", new ItemBase());
        register("book_material2", new ItemBase());

        register("manual_getter", new ItemManualGetter());
        register("mana_food1", new ItemManaFood(1, 1, 100));
        register("mana_food2", new ItemManaFood(1, 1, 300));
        register("mana_food3", new ItemManaFood(1, 1, 1000));
        register("mana_food4", new ItemManaFood(1, 1, 3000));

        for(SpellRegistry.SpellRegistryEntry entry : SpellRegistry.SPELLS){
            register("spellbook_" + entry.getRegistryName().getPath(), new ItemSpellbook(entry.getSpell()));
        }

        INGOT = register("magic_ingot", new ItemBase());

        register("debug", new ItemDebug());
        register("debug_xp", new ItemExperience());

    }

    public static Item register(String name, Item item){
        item.setRegistryName(name);
        item.setTranslationKey(name);
        item.setCreativeTab(((IItemBase) item).getTab());
        allItems.add((IItemBase) item);
        return item;
    }

}
