package scellena.fallen_elves.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Utilities {

    public static String removeSection(String str){
        String ret = "";
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == '§'){
                i++;
            }else{
                ret += str.charAt(i);
            }
        }
        return ret;
    }

    public static String connectList(List<String> list, String interval){
        AtomicReference<String> ret = new AtomicReference<>("");
        list.forEach(s -> {
            ret.set(ret.get() + s + interval);
        });
        return ret.get();
    }

    public static String getItemListString(List<ItemStack> list){
        StringBuilder s = new StringBuilder();
        AtomicInteger i = new AtomicInteger();
        list.forEach(m -> {
            if(i.getAndIncrement() > 0)s.append(", ");
            s.append(m.getDisplayName() + " x" + m.getCount());
        });
        return s.toString();
    }

    public static String getEntityDisplayNameKey(Entity entity){
        if(entity == null)return "???";
        if (entity.hasCustomName())
        {
            return entity.getCustomNameTag();
        }
        else
        {
            String s = EntityList.getEntityString(entity);

            if (s == null)
            {
                s = "generic";
            }

            return "entity." + s + ".name";
        }
    }

    public static int countItem(IInventory inventory, ItemStack stack){
        int ans = 0;
        for(int i=0; i<inventory.getSizeInventory(); i++){
            if(inventory.getStackInSlot(i).isItemEqual(stack)){
                ans += inventory.getStackInSlot(i).getCount();
            }
        }
        return ans;
    }

    public static boolean removeItem(IInventory inventory, ItemStack stack, int count){
        int c = count;
        for(int i=0; i<inventory.getSizeInventory(); i++){
            if(inventory.getStackInSlot(i).isItemEqual(stack)){
                int j = inventory.getStackInSlot(i).getCount();
                if(j > c)j = c;
                c -= j;
                inventory.getStackInSlot(i).shrink(j);
                if(c <= 0)return true;
            }
        }
        return false;
    }

    public static void giveItemByDropping(EntityPlayer player, ItemStack stack){
        EntityItem entityitem = player.dropItem(stack, false);
        if(entityitem != null) {
            entityitem.setNoPickupDelay();
            entityitem.setOwner(player.getName());
        }
    }

    public static int getRandomInteger(int amount){
        return (amount / 1000) + (new Random().nextDouble() * 1000 < (amount % 1000) ? 1 : 0);
    }

    public static BlockPos getTopPos(World world, BlockPos v){
        for(int i=0; i<255; i++){
            BlockPos v2 = v.add(0, i, 0);
            if(!world.getBlockState(v2).getBlock().isCollidable()){
                return v2;
            }
        }
        return v;
    }

}
