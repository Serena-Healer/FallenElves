package scellena.fallen_elves.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler {

    public static List<IItemBase> allItems = new ArrayList<>();

    public static void init(){

    }

    public static Item register(String name, Item item){
        item.setRegistryName(name);
        item.setTranslationKey(name);
        item.setCreativeTab(((IItemBase) item).getTab());
        allItems.add((IItemBase) item);
        return item;
    }

}
