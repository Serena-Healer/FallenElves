package scellena.fallen_elves.spells.types;

import net.minecraft.util.text.TextComponentString;
import scellena.fallen_elves.spells.SpellBase;

public class TestSpell extends SpellBase {
    @Override
    public boolean onRightClick() {
        getOwner().sendMessage(new TextComponentString("Spell Used!"));
        return true;
    }

    @Override
    public double getManaCost() {
        return 10;
    }
}
