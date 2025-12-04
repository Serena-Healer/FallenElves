package scellena.fallen_elves.entities.mobs.a;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.mobs.templetes.FriendlyMob;

public class Elf extends FriendlyMob {

    public Elf(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20);
        if(this instanceof ICustomEntity){
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
        }
    }

}
