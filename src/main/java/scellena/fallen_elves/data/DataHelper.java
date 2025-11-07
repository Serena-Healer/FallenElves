package scellena.fallen_elves.data;

import net.minecraft.entity.player.EntityPlayer;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;

import javax.annotation.Nonnull;

public class DataHelper {

    @Nonnull
    public static PlayerDataHandler getPlayerData(EntityPlayer player){
        PlayerDataHandler ans = PlayerCapabilityProvider.getPlayerData(player);
        if(ans == null)throw new RuntimeException("創世神「人間の情報が名簿に見つかりませんでした...。」");
        return ans;
    }

}
