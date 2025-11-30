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
        return 10000;
    }

    public static boolean checkManaCost(Entity owner, SpellBase spell){
        if(owner == null) return false;
        EntityDataHandler data = EntityCapabilityProvider.getEntityData(owner);
        if(data == null) return false;
        System.out.println("DEBUG: " + owner.getDisplayName().toString() + " Mana: " + data.getMana() + " XP: " + data.getCurrentXP());
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
        return 998244353;
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
