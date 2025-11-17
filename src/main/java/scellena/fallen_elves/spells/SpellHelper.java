package scellena.fallen_elves.spells;

import net.minecraft.entity.Entity;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;

public class SpellHelper {

    /**
     * 引数のレベルに到達するのに必要な経験値差分を返す。
     * @param level 次のレベル
     * @return 経験値差分
     */
    public static double getXPRequired(int level){
        return 100;
    }

    public static boolean checkManaCost(Entity owner, SpellBase spell){
        if(owner == null) return false;
        PlayerDataHandler data = PlayerCapabilityProvider.getPlayerData(owner);
        if(data == null) return false;
        return data.getMana() >= spell.getManaCost();
    }

    public static void onFinishSpell(Entity owner, SpellBase spell){
        PlayerDataHandler data = PlayerCapabilityProvider.getPlayerData(owner);
        if(data != null){
            data.addMana(spell.getManaCost());
            data.collectXP(spell.getXPGain());
        }
    }

}
