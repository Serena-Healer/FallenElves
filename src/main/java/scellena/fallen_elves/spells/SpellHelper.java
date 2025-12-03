package scellena.fallen_elves.spells;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;
import scellena.fallen_elves.items.spellbooks.ItemSpellbook;

import java.util.List;
import java.util.Map;

public class SpellHelper {

    /**
     * 引数のレベルに到達するのに必要な経験値差分を返す。
     * @param level 次のレベル
     * @return 経験値差分
     */
    public static double getXPRequired(int level){
        if(level == 2) return 500;
        if(level == 3) return 1500;
        if(level == 4) return 3000;
        if(level == 5) return 5000;
        return Math.pow(1.11, level) * 6000;
    }

    public static boolean checkManaCost(Entity owner, SpellBase spell){
        if(owner == null) return false;
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(owner);
        if(data == null) return false;
        return data.getMana() >= spell.getManaCost();
    }

    public static void onFinishSpell(Entity owner, SpellBase spell){
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(owner);
        if(data != null){
            data.addMana(spell.getManaCost() * -1);
            data.collectXP(spell.getXPGain());
        }
    }

    public static double getMaxMana(Entity owner){
        return Math.pow(1.1, EntityCapabilityProvider.getEntityData(owner).getCurrentLevel() - 1) * 100;
    }

    /**
     * 1s あたりの MP 回復量を返す。
     */
    public static double getManaRegen(Entity owner){
        return getMaxMana(owner) * 0.01;
    }

    public static Map<ResourceLocation, SpellBase> getSpellObjects(Entity entity){
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(entity);
        return data.getSpellObjects();
    }

    public static ResourceLocation getRegistryName(Class<? extends SpellBase> spell){
        for(SpellRegistry.SpellRegistryEntry entry : SpellRegistry.SPELLS){
            if(spell == entry.getSpell()) return entry.getRegistryName();
        }
        return null;
    }

}
