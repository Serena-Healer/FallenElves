package scellena.fallen_elves.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import scellena.fallen_elves.items.consumables.ItemDebug;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;
import scellena.fallen_elves.items.wands.ItemWand;
import scellena.fallen_elves.spells.SpellRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler {

    public static List<IItemBase> allItems = new ArrayList<>();

    public static void init(){

        register("wand", new ItemWand());

        for(SpellRegistry.SpellRegistryEntry entry : SpellRegistry.SPELLS){
            register("spellbook_" + entry.getRegistryName().getPath(), new ItemSpellbook(entry.getSpell()));
        }

        register("debug", new ItemDebug());

    }

    public static Item register(String name, Item item){
        item.setRegistryName(name);
        item.setTranslationKey(name);
        item.setCreativeTab(((IItemBase) item).getTab());
        allItems.add((IItemBase) item);
        return item;
    }

}
