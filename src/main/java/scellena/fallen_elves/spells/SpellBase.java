package scellena.fallen_elves.spells;

import net.minecraft.entity.Entity;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;

public abstract class SpellBase {

    Entity owner;

    public abstract boolean onRightClick();
    public abstract double getManaCost();

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
}
