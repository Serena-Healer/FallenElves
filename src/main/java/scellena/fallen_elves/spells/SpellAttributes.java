package scellena.fallen_elves.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class SpellAttributes {

    public static final IAttribute MAX_MANA = new RangedAttribute((IAttribute)null, "fallen_elves:mana", 0.0D, 0.0D, 2048.0D).setShouldWatch(true);
    public static final IAttribute MAGICAL_MIGHT = new RangedAttribute((IAttribute)null, "fallen_elves:magical_might", 0.0D, 0.0D, 2048.0D);
    public static final IAttribute MAGICAL_MENDING = new RangedAttribute((IAttribute)null, "fallen_elves:magical_mending", 0.0D, 0.0D, 2048.0D);

    public static void init(EntityLivingBase entity){
        if(entity.getAttributeMap().getAttributeInstance(MAX_MANA) == null) {
            entity.getAttributeMap().registerAttribute(MAX_MANA);
            entity.getAttributeMap().registerAttribute(MAGICAL_MIGHT);
            entity.getAttributeMap().registerAttribute(MAGICAL_MENDING);
        }
    }

}
