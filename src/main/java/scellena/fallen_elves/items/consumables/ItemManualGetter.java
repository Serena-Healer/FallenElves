package scellena.fallen_elves.items.consumables;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.items.ItemBase;
import scellena.fallen_elves.items.ModCreativeTab;

import javax.annotation.Nullable;
import java.util.List;

public class ItemManualGetter extends ItemBase {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Item item = Item.getByNameOrId("patchouli:guide_book");
        if(item != null){
            ItemStack stack = new ItemStack(item, 1);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("patchouli:book", "fallen_elves:manual");
            stack.setTagCompound(nbt);
            playerIn.setHeldItem(handIn, stack);
        }else{
            playerIn.sendMessage(new TextComponentTranslation("item.manual_getter.error"));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(worldIn != null && worldIn.isRemote) {
            tooltip.add(I18n.format("item.manual_getter.desc1"));
            tooltip.add(I18n.format("item.manual_getter.desc2"));
        }
    }

    @Override
    public CreativeTabs getTab() {
        return ModCreativeTab.DUNGEON_BLOCKS;
    }
}
