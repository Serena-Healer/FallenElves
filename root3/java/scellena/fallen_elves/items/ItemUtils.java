package scellena.fallen_elves.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtils {

    public static void setInformation(ItemStack stack, List<String> tooltip) {
        int i = 0;
        while(true){
            if(I18n.hasKey("item." + stack.getItem().getRegistryName().getPath() + "_" + stack.getItemDamage() + ".desc" + i)){
                tooltip.add(I18n.format("item." + stack.getItem().getRegistryName().getPath() + "_" + stack.getItemDamage() + ".desc" + i));
            }else if(I18n.hasKey("item." + stack.getItem().getRegistryName().getPath() + ".desc" + i)){
                tooltip.add(I18n.format("item." + stack.getItem().getRegistryName().getPath() + ".desc" + i));
            }else{
                break;
            }
            i++;
        }
    }

}
