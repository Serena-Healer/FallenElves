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

    public static Potion KILL_DESIRE;
    public static Potion ALLIANCE;
    public static Potion ALLIANCE_2;

    public static void init(){
        potionList.forEach(ForgeRegistries.POTIONS::register);
        KILL_DESIRE = register(new PotionBase("kill_desire", Integer.parseInt("400040", 16), true));
        ALLIANCE = register(new PotionBase("alliance", Integer.parseInt("400040", 16), true));
        ALLIANCE_2 = register(new PotionBase("alliance_2", Integer.parseInt("400040", 16), true));
    }

    public static Potion register(Potion potion){
        potionList.add(potion);
        ForgeRegistries.POTIONS.register(potion);
        return potion;
    }

}
