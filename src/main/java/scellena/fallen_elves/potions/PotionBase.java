package scellena.fallen_elves.potions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.FallenElves;

import javax.annotation.Nullable;

public class PotionBase extends Potion {

    protected PotionBase(String name, int color, boolean badEffectIn) {
        super(badEffectIn, color);
        setPotionName(name);
        setRegistryName(new ResourceLocation(FallenElves.MOD_ID,name));
        PotionsHandler.potionList.add(this);
        if(!badEffectIn) this.setBeneficial();
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}
