package scellena.fallen_elves.potions;

import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class PotionsHandler {

    public static List<Potion> potionList = new ArrayList<>();

    public static void init(){
        potionList.forEach(ForgeRegistries.POTIONS::register);
    }

    public static void register(Potion potion){
        potionList.add(potion);
    }

}
