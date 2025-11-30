package scellena.fallen_elves.spells;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;

public abstract class SpellBase {

    Entity owner;
    ResourceLocation registryName;

    public abstract boolean onRightClick();
    public abstract double getManaCost();

    public String getDisplayName(){
        return I18n.translateToLocal("spell." + registryName.toString() + ".name");
    }

    public double getXPGain(){
        return getManaCost() + 100.0;
    }

    public boolean isAvailable(){
        return SpellHelper.checkManaCost(getOwner(), this);
    }

    public Entity getOwner() {
        return owner;
    }

    public SpellBase setOwner(Entity owner) {
        this.owner = owner;
        return this;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public SpellBase setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName;
        return this;
    }

    public void onTick(){}

}
