package scellena.fallen_elves.potions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.Confiance5th;

import javax.annotation.Nullable;

public class PotionBase extends Potion {

    protected PotionBase(String name, int color) {
        super(false,color);
        setPotionName(name);
        setRegistryName(new ResourceLocation(Confiance5th.MOD_ID,name));
        PotionsHandler.potionList.add(this);
        this.setBeneficial();
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}
