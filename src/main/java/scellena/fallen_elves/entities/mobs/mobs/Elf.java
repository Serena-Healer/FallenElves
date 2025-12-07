package scellena.fallen_elves.entities.mobs.mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.mobs.templetes.FriendlyMob;
import scellena.fallen_elves.items.ItemHandler;

public class Elf extends FriendlyMob {

    public Elf(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
    }

}
